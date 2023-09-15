package com.ticketease.te.main;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ticketease.te.performance.board.BoardResponse;
import com.ticketease.te.performance.board.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {

	private final BoardService boardService;

	@GetMapping("/")
	public String index(@RequestParam(required = false) String search,
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
		ModelMap map) {
		Page<BoardResponse> response = boardService.findPerformance(search, pageable).map(BoardResponse::of);
		List<Integer> barNumbers = boardService.getPaginationBarNumbers(pageable.getPageNumber(),
			response.getTotalPages());
		map.addAttribute("board", response);
		map.addAttribute("paginationBarNumbers", barNumbers);
		return "index";
	}

}
