package kr.codesqaud.cafe.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.user.User;


public class JoinServiceImpl implements JoinService{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    public JoinServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void join(User user) {
        //회원 저장
        userRepository.addUser(user);
        logger.info("userRepository = {}", userRepository.toString());

    }

    @Override
    public User lookupUser(String userId) {

        return null;
    }
}
