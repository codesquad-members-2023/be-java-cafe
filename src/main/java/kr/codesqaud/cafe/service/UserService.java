package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.repository.UserDao;
import kr.codesqaud.cafe.repository.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final List<User> userList;
    private final UserDao userDao;

    public UserService(List<User> userList, UserDao userDao) {
        this.userList = userList;
        this.userDao = userDao;
    }

    public void createUser(User user) {
        userDao.addUser(user);
    }

    public List<UserDto> findUserAll() {
        return userDao.findUserAll();
    }

    public UserDto findUserByUserId(String userId) {
//        return userList.stream()
//                .filter(s -> s.getUserId().equals(userId))
//                .collect(Collectors.toList());
        final int FIRST_USER = 0;
        return userDao.findUserByUserId(userId).get(FIRST_USER);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
//        List<User> temp = userList.stream()
//                .filter(u -> u.getUserId().equals(user.getUserId())
//                        && u.getPassword().equals(user.getPassword()))
//                .collect(Collectors.toList());
//        if (temp.size() > 0) {
//            userList.set(userList.indexOf(temp.get(FIRST_INDEX)), user);
//        }

    }
}
