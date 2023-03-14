package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private final MemberRepository memberRepository;

    public SignUpServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(User user) {
        memberRepository.save(user);
    }

    public List<User> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public User findById(String userId) {
        return memberRepository.findById(userId);
    }
}
