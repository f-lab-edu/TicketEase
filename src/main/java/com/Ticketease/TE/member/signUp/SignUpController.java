package com.Ticketease.TE.member.signUp;

import com.Ticketease.TE.exception.SignUpExceptionHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class SignUpController {

    private final SignUpService signUpService;

    @GetMapping("/signup")
    public String SignUpDefaultPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpUser(@Valid SignUpRequest request,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        try{
            signUpService.signUpUserValidation(SignUpRequest.toDto(request),bindingResult);
        }catch (SignUpExceptionHandler handler){
            redirectAttributes.addFlashAttribute(handler.getCode().toString(), handler.getMessage());
            return "redirect:signup";
        }

        try {
            signUpService.signUpUser(SignUpRequest.toDto(request));
        }catch (SignUpExceptionHandler handler){
            redirectAttributes.addFlashAttribute(handler.getCode().toString(), handler.getMessage());
            return "redirect:signup";
        }

        redirectAttributes.addFlashAttribute("success", "회원가입이 완료됐습니다.");
        return "redirect:login";
    }
}