package com.ticketease.te.member.signUp;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.ExceptionHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SignUpController {

	private final SignUpService signUpService;

	@GetMapping("/signup")
	public String SignUpDefaultPage() {
		return "signup";
	}

	@PostMapping("/signup")
	public String signUpUser(@Valid SignUpRequest request,
		BindingResult bindingResult,
		RedirectAttributes redirectAttributes) {
		try {
			signUpUserValidation(SignUpRequest.toDto(request), bindingResult);
			signUpService.signUpUser(SignUpRequest.toDto(request));
		} catch (ExceptionHandler handler) {
			redirectAttributes.addFlashAttribute(handler.getCode().toString(), handler.getMessage());
			return "redirect:signup";
		}

		redirectAttributes.addFlashAttribute("success", "회원가입이 완료됐습니다.");
		return "redirect:login";
	}

	private void signUpUserValidation(SignUpDto dto, BindingResult result) {
		if (result.hasErrors() || !dto.password().equals(dto.confirmPassword())) {
			throw new ExceptionHandler(ExceptionCode.INVALID_SIGNUP_REQUEST,
				ExceptionCode.INVALID_SIGNUP_REQUEST.getDescription());
		}
	}
}
