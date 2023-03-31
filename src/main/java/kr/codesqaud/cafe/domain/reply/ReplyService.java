package kr.codesqaud.cafe.domain.reply;

import kr.codesqaud.cafe.domain.user.Member;
import kr.codesqaud.cafe.dto.ReplyDto;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateMemberRepository;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

	private final NamedJdbcTemplateReplyRepository replyRepository;
	private final NamedJdbcTemplateMemberRepository memberRepository;

	@Autowired
	public ReplyService(NamedJdbcTemplateReplyRepository replyRepository, NamedJdbcTemplateMemberRepository memberRepository) {
		this.replyRepository = replyRepository;
		this.memberRepository = memberRepository;
	}

	// 댓글 생성
	public void createReply(Member member, String contents, Long articleSequence) {
		Reply reply = new Reply();
		reply.setWriter(member.getUserId());
		reply.setContents(contents);
		reply.setArticleSequence(articleSequence);
		reply.setUserSequence(member.getUserSequence());

		replyRepository.write(reply);
	}

	// 댓글 조회
	public List<ReplyDto> showReplies(Long articleSequence) {
		List<ReplyDto> allReplies = replyRepository.showAllReplies(articleSequence);
		return allReplies;
	}
}
