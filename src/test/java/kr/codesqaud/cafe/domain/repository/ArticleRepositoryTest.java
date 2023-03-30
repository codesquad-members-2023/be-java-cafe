package kr.codesqaud.cafe.domain.repository;


import kr.codesqaud.cafe.dto.ArticleWithWriterDto;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ArticleRepositoryTest {

	@Autowired
	NamedJdbcTemplateArticleRepository repository;
	private final Logger logger = LoggerFactory.getLogger(getClass());

//	@Test
//	@DisplayName("게시판 글쓰기 테스트")
//	void write() {
//		logger.info("repository size:{}", repository.showAllArticles().size());
//
//		Article article = new Article("rhrjsgh97", "Hello!", "Nice to meet you!");
//		repository.write(article, session);
//
//		logger.info("repository size:{}", repository.showAllArticles().size());
//
//		assertThat(repository.showAllArticles().size()).isEqualTo(3);
//	}

	@Test
	@DisplayName("게시판 글 조회 테스트")
	void showAllArticles() {
		List<ArticleWithWriterDto> allArticles = repository.findAllArticlesWithWriter();
		for (ArticleWithWriterDto allArticle : allArticles) {
			logger.info("writer:{}, title:{}, contents:{}, writtenTime:{}", allArticle.getWriter(), allArticle.getTitle(), allArticle.getContents(), allArticle.getWrittenTime());
		}
		logger.info("allArticles.size():{}", allArticles.size());

		assertThat(allArticles.size()).isEqualTo(2);
	}

}
