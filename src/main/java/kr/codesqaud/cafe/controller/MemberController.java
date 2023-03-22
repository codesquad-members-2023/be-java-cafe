package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.user.Member;
import kr.codesqaud.cafe.domain.user.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class MemberController {

    private final MemberRepository memberRepository;
    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/add")
    public String addForm() {
        return "user/form";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute("user") Member user) throws SQLException {
        memberRepository.save(user);
        log.trace("사용자 ID: {}", user.getUserId());
        log.trace("사용자 이름: {}", user.getName());
        log.trace("사용자 email: {}", user.getEmail());
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String users(Model model) {
        List<Member> members = memberRepository.showAllUsers();
        model.addAttribute("users", members);
        log.trace("사용자 수: {}", members.size());
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String userProfile(@PathVariable String userId, Model model) throws SQLException {
        Member findUser = memberRepository.findById(userId);
        model.addAttribute("user", findUser);
        return "user/profile";
    }

    @GetMapping("/update/{userId}")
    public String editUserForm(@PathVariable String userId, Model model) throws SQLException {
        model.addAttribute("user", memberRepository.findById(userId));
        return "user/updateForm";
    }

    @PutMapping("/update/{userId}")
    public String edit(@PathVariable String userId, @ModelAttribute Member user) throws SQLException {
        memberRepository.updateUser(userId, user);
        return "redirect:/users/list";
    }

//    /**
//     * 테스트 용 데이터 추가
//     */
//    @PostConstruct
//    public void init() throws SQLException {
//        memberRepository.save(new Member("GOMUNGNAM", "247597", "고건호", "rhrjsgh97@gmail.com"));
//        memberRepository.save(new Member("Roy", "123456", "이승로", "roy@gmail.com"));
//        memberRepository.save(new Member("Birdie", "123456", "임동현", "birdie@gmail.com"));
//        memberRepository.save(new Member("Hana", "1234", "왕하나", "hana@gmail.com"));
//    }
}
