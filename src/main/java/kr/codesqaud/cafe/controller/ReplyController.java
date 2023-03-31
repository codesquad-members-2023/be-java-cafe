package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/articles")
public class ReplyController {


	@Autowired
	public ReplyController(NamedJdbcTemplateReplyRepository replyRepository, NamedJdbcTemplateMemberRepository memberRepository, ReplyService replyService) {
		this.replyRepository = replyRepository;
		this.memberRepository = memberRepository;
		this.replyService = replyService;
	}

}
