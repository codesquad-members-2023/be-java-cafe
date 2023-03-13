package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.repository.UserMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserLogicController {
    UserMemoryRepository userMemoryRepository;

    @Autowired
    public UserLogicController(UserMemoryRepository userMemoryRepository) {
        this.userMemoryRepository = userMemoryRepository;
    }

    @ResponseBody
    @GetMapping("/create")
    public String create(@RequestParam String userId,
                       @RequestParam String password,
                       @RequestParam String name,
                       @RequestParam String email
                       ){
        userMemoryRepository.join(new User(userId, password, name, email));

        return "create";
    }

    @ResponseBody
    @GetMapping
    public String list() {
        List<User> users = userMemoryRepository.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            stringBuilder.append(user.getUserId()).append("\n");
        }
        return stringBuilder.toString();
    }

    @ResponseBody
    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId) {
        User user = userMemoryRepository.findUser(userId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(user.getUserId()).append("\n");
        stringBuilder.append(user.getPassword()).append("\n");
        stringBuilder.append(user.getName()).append("\n");
        stringBuilder.append(user.getEmail()).append("\n");

        return stringBuilder.toString();
    }


}
