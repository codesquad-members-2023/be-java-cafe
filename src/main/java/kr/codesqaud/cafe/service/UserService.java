package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.repository.UserDao;
import kr.codesqaud.cafe.repository.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(User user) {
        userDao.addUser(user);
    }

    public List<UserDto> findUserAll() {
        return userDao.findUserAll();
    }

    public UserDto findUserByUserId(String userId) {
        final int FIRST_USER = 0;
        return userDao.findUserByUserId(userId).get(FIRST_USER);
    }

    public void updateUser(UserDto userDto) {
        userDao.updateUser(userDto);
    }

    public boolean findUserByPassword(UserDto userDto) {
        String inputPassword = userDto.getPassword();
        final int FIRST_USER = 0;
        return !userDao.findUserByUserPassword(inputPassword).isEmpty();
    }
}
