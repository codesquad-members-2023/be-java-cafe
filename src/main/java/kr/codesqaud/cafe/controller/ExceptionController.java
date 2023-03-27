package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionController {

    @GetMapping("/api/error")
    public String globalErrorPage(HttpServletRequest request, Model model) {
        String errMessage = (String)request.getAttribute("error");
        model.addAttribute("error",errMessage);

        return "exception/error";
    }

}
