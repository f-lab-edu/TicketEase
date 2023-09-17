package com.ticketease.te.performance.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService boardService;

	@GetMapping("/detail/{id}")
	public String PerformanceDetailPage(
		@PathVariable Long id,
		ModelMap map
	) {
		try {
			BoardResponse response = BoardResponse.from(boardService.findSinglePerformanceById(id));
			map.addAttribute("Board", response);
		} catch (EntityNotFoundException ex) {
			return "error";
		}
		return "detail";
	}
}
