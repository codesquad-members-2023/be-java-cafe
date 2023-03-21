package kr.codesqaud.cafe.domain.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {

    private static final List<Member> userRepository = new ArrayList<>();
    private static long sequence = 0L;

    public Member save(Member user) {
        user.setUserSequence(++sequence);
        userRepository.add(user);
        return user;
    }

    public List<Member> showAllUsers() {
        List<Member> allMembers = new ArrayList<>();
        for (int i = 0; i < userRepository.size(); i++) {
            allMembers.add(userRepository.get(i));
        }
        return allMembers;
    }

    public Member findByUserId(String userId) {
        return userRepository.stream().filter(user -> user.getUserId().equals(userId)).findFirst().orElseThrow();
    }

    public void updateUser(String userId, Member updateParam) {
        Member user = findByUserId(userId);
        String originalPassword = user.getPassword();
        if (!originalPassword.equals(updateParam.getPassword())) {
            return;
        }
        user.setPassword(updateParam.getPassword());
        user.setName(updateParam.getName());
        user.setEmail(updateParam.getEmail());
    }
}
