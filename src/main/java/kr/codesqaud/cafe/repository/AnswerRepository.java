package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Answer;
import kr.codesqaud.cafe.dto.answer.AnswerResponseDto;

import java.util.List;

public interface AnswerRepository {
    void save(Answer answer);
    List<AnswerResponseDto> findAllByArticleId(long articleId);
    AnswerResponseDto findById(long id);

    void update(long exAnswerId, String newContents);

    void delete(long id);

    void deleteAll(Long articleId);
}
