package kr.codesqaud.cafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
    @ExceptionHandler()
    public ModelAndView articleAuthHandler(Exception e) {
        ModelAndView mav = new ModelAndView();
        logger.info(e.getMessage());
        mav.addObject("errorMessage", e.getMessage());
        mav.setViewName("error/error");
        return mav;
    }
}
