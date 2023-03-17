package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Qna;
import kr.codesqaud.cafe.repository.QnaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class QnaController {

    private Logger log = LoggerFactory.getLogger(getClass());
    private QnaRepository qnaRepository;

    @Autowired
    public QnaController(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    @PostMapping
    public String addUser(@ModelAttribute Qna qna) {
        qnaRepository.save(qna);
        log.debug("debug log={}", qna.getContents());
        return "redirect:/";
    }



}
