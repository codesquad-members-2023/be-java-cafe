package kr.codesqaud.cafe.domain.repository;

import kr.codesqaud.cafe.domain.repository.MemberRepository;
import kr.codesqaud.cafe.domain.repository.SignUpService;
import kr.codesqaud.cafe.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public User findById(String userId) {
        return memberRepository.findById(userId);
    }
}
