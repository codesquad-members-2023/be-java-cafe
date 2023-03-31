package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.reply.Reply;
import kr.codesqaud.cafe.domain.reply.ReplyService;
import kr.codesqaud.cafe.domain.user.Member;
import kr.codesqaud.cafe.dto.ReplyDto;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateMemberRepository;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateReplyRepository;
import kr.codesqaud.cafe.utility.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static kr.codesqaud.cafe.utility.Constants.*;

@Controller
@RequestMapping("/articles")
public class ReplyController {

	private final NamedJdbcTemplateReplyRepository replyRepository;
	private final NamedJdbcTemplateMemberRepository memberRepository;
	private final ReplyService replyService;

	private final Logger log = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	public ReplyController(NamedJdbcTemplateReplyRepository replyRepository, NamedJdbcTemplateMemberRepository memberRepository, ReplyService replyService) {
		this.replyRepository = replyRepository;
		this.memberRepository = memberRepository;
		this.replyService = replyService;
	}

}
