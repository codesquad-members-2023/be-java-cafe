package kr.codesqaud.cafe.controller;


import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.Member.JdbcMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {

    private JdbcMemberRepository jdbcMemberRepository;


    Logger logger = LoggerFactory.getLogger(MemberController.class);

    public MemberController(JdbcMemberRepository jdbcMemberRepository) {
        this.jdbcMemberRepository = jdbcMemberRepository;
        System.out.println(this.getClass());
    }

    @PostMapping("/users")
    public String addUser(@ModelAttribute("user") Member member) {
        logger.debug("addUser");
        jdbcMemberRepository.save(member);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        logger.debug("getUserList");
        List<Member> userList = jdbcMemberRepository.findAll();
        model.addAttribute("users", userList);
        return "user/list";
    }

    @RequestMapping("/profile/{userId}")
    public String getUser(@PathVariable String userId, Model model) {
        Optional<Member> user = jdbcMemberRepository.findById(userId);
        model.addAttribute("profile", user.orElseThrow(IllegalArgumentException::new));
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateUser(@PathVariable("userId") String userId, Model model) {
        logger.debug("updateUser : GET");

        Optional<Member> updateUser = jdbcMemberRepository.findById(userId);
        // Model 과 View 연결
        model.addAttribute("user", updateUser.orElseThrow());
        return "user/updateForm";
    }

    @PutMapping("/users/{userId}")
    public String updateUser(@ModelAttribute("user") Member member) {
        logger.debug("updateUser : PUT");
        jdbcMemberRepository.update(member);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user")Member member, HttpSession session, HttpServletRequest request){
        Optional<Member> existUser = jdbcMemberRepository.findById(member.getUserId());
        session = request.getSession();
        boolean isExist = false;

        if(existUser.isPresent() && member.getUserId() != null){
            session.setAttribute("sessionedUser",existUser);
            if(!member.getPassword().equals(existUser.get().getPassword())){
                isExist = true;
                return "user/login";
            }
            return "redirect:/";
        } else {
            isExist = true;
            return "user/login";
        }
    }


}
