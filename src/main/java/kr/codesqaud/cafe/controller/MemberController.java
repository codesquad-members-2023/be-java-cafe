package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.repository.NamedJdbcTemplateMemberRepository;
import kr.codesqaud.cafe.domain.user.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kr.codesqaud.cafe.Constants.SESSIONED_USER;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class MemberController {

	private final NamedJdbcTemplateMemberRepository namedJdbcTemplateMemberRepository;

	private final Logger log = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	public MemberController(NamedJdbcTemplateMemberRepository namedJdbcTemplateMemberRepository) {
		this.namedJdbcTemplateMemberRepository = namedJdbcTemplateMemberRepository;
	}

	@GetMapping("/add")
	public String addForm() {
		return "user/form";
	}

	@PostMapping("/add")
	public String saveUser(@ModelAttribute("user") Member user) throws SQLException {
		namedJdbcTemplateMemberRepository.save(user);
		log.trace("사용자 ID: {}", user.getUserId());
		log.trace("사용자 이름: {}", user.getName());
		log.trace("사용자 email: {}", user.getEmail());
		return "redirect:/login";
	}

	@GetMapping("/list")
	public String users(HttpSession session, Model model) throws SQLException {
		if (session.getAttribute(SESSIONED_USER) == null) {
			return "user/login";
		}
		List<Member> members = namedJdbcTemplateMemberRepository.showAllUsers();
		model.addAttribute("users", members);
		log.trace("사용자 수: {}", members.size());
		return "user/list";
	}

	@GetMapping("/profile/{userId}")
	public String userProfile(HttpSession session, @PathVariable String userId, Model model) throws SQLException {
		if (session.getAttribute(SESSIONED_USER) == null) {
			return "user/login";
		}
		Optional<Member> findUser = namedJdbcTemplateMemberRepository.findById(userId);
		model.addAttribute("user", findUser.orElseThrow());
		return "user/profile";
	}

	@GetMapping("/update/{userId}")
	public String editUserForm(HttpSession session, @PathVariable String userId, Model model) throws SQLException {

		String loginUserId = (String) session.getAttribute(SESSIONED_USER);

		if (loginUserId == null) {
			return "redirect:/login";
		}

		if(!userId.equals(loginUserId)) {
			return "redirect:/";
		}

		Member member = namedJdbcTemplateMemberRepository.findById(loginUserId).get();

		model.addAttribute(member);
		return "user/updateForm";
	}

	@PutMapping("/update/{userId}")
	public String edit(@ModelAttribute Member user) throws SQLException {
		namedJdbcTemplateMemberRepository.update(user);
		return "redirect:/users/list";
	}

}
