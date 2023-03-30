package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Answer;

import java.util.List;

public interface AnswerRepository {
    void save(Answer answer);
    List<Answer> findAll(long articleId);
    Answer findById(long id);
    void update(Answer exAnswer, Answer newAnswer);
    void delete(long id);

    void update(Answer answer, long answerId);
}
