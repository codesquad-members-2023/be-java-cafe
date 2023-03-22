package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Member;
import org.springframework.stereotype.Service;


@Service
public interface MemberService {
    Member login(String userId, String password);
}
