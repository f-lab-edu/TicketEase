package com.Ticketease.TE.member.signUp;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class SignUpController {


    @GetMapping("/signup")
    public String SignUpDefaultPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpUser(@Valid SignUpRequest request,
                             BindingResult bindingResult,
                             ModelMap map,
                             RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors() || !request.password().equals(request.confirmPassword())){
            redirectAttributes.addFlashAttribute("invalidReq", "비정상적인 요청입니다.");
            return "redirect:signup";
        }

        return "redirect:login";
    }
}