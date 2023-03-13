package kr.codesqaud.cafe.service;

import java.util.List;
import kr.codesqaud.cafe.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final List<User> userList;

    public UserService(List<User> userList) {
        this.userList = userList;
    }

    public void createUser(User user) {
        userList.add(user);
    }

    public void searchUserAll() {
        System.out.println();
        System.out.println("전체목록");
        userList.forEach(u -> System.out.println(u.toString()));
    }

    public List<User> returnUserList() {
        return userList;
    }
}
