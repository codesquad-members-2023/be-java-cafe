package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.reply.ReplyService;
import kr.codesqaud.cafe.domain.user.Member;
import kr.codesqaud.cafe.dto.ReplyEditDto;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateMemberRepository;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateReplyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

	@PostMapping("/{articleSequence}/reply/write")
	public String writeReply(@PathVariable Long articleSequence, @RequestParam String contents, HttpSession session) {
		Member member = memberRepository.findByNumber(getAttribute(session)).get();
		replyService.createReply(member, contents, articleSequence);
		return "redirect:/articles/" + articleSequence;
	}

	@GetMapping("/{articleSequence}/reply/{replySequence}/edit")
	public String replyEditForm(Model model, @PathVariable Long articleSequence, @PathVariable Long replySequence, HttpSession session) {
		ReplyEditDto foundReply = replyService.findReplyEditDto(replySequence);
		if (foundReply.getUserSequence() != session.getAttribute(SESSIONED_USER)) {
			log.info("다른 사용자의 댓글 !!");
			return "redirect:/articles/" + articleSequence;
		}
		model.addAttribute("foundReply", foundReply);
		log.info("foundReply 내용: {}", foundReply.getContents());
		log.info("foundReply 작성자 번호: {}", foundReply.getUserSequence());
		log.info("foundReply 댓글 번호: {}", foundReply.getReplySequence());
		return "reply/replyEditForm";
	}

	@PutMapping("/{articleSequence}/reply/{replySequence}/edit")
	public String editReply(@ModelAttribute ReplyEditDto replyEditDto, @PathVariable Long articleSequence) {
		replyService.editReply(replyEditDto);
		return "redirect:/articles/" + articleSequence;
	}

	@DeleteMapping("/{articleSequence}/reply/{replySequence}/delete")
	public String deleteReply(HttpSession session, @PathVariable Long replySequence, @PathVariable Long articleSequence) {
		log.info("삭제하려는 댓글 번호: {}", replySequence);
		ReplyEditDto replyEditDto = replyService.findReplyEditDto(replySequence);
		if (replyEditDto.getUserSequence() != session.getAttribute(SESSIONED_USER)) {
			log.info("다른 사용자의 댓글 !!");
			return "redirect:/articles/" + articleSequence;
		}
		replyService.deleteReply(replyEditDto);
		return "redirect:/articles/" + articleSequence;
	}

	private Long getAttribute(HttpSession session) {
		return (Long) session.getAttribute(SESSIONED_USER);
	}
}
