package kr.codesqaud.cafe.cafeservice.service;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.domain.Reply;
import kr.codesqaud.cafe.cafeservice.exhandler.exception.MemberNotFoundException;
import kr.codesqaud.cafe.cafeservice.repository.reply.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ReplyService {
    private final ReplyRepository repository;

    @Autowired
    public ReplyService(ReplyRepository repository) {
        this.repository = repository;
    }

    public Reply findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new MemberNotFoundException("댓글 찾을수 없습니다"));
    }

    public List<Reply> findReplyList(Long id) {
        List<Reply> replyList = repository.findAll(id);
        List<Reply> list = Collections.unmodifiableList(replyList);
        return list;
    }

    public Reply save(Long id) {
        return repository.findById(id).orElseThrow(() -> new MemberNotFoundException("댓글 찾을수 없습니다"));
    }
}
