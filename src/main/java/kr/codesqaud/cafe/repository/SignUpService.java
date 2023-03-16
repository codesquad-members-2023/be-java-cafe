package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SignUpService {

    @Autowired
    private final MemberRepository memberRepository;

    public SignUpService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void join(User user) {
        memberRepository.save(user);
    }

    public List<User> findAll() {
        return memberRepository.findAll();
    }

    public Optional<User> findById(String userId) {
        return memberRepository.findById(userId);
    }
}
