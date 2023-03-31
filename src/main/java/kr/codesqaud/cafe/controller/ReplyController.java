package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;

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
