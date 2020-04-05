package com.example.web.controller;

import com.example.domain.dto.CreateSupportQueryDto;
import com.example.domain.dto.PostDto;
import com.example.domain.service.SupportCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SupportCommandController {

	private final SupportCommandService supportService;
	
	@PostMapping("/support")
	public String createNewQuery(@ModelAttribute CreateSupportQueryDto createSupportQueryDto, @AuthenticationPrincipal User user) {
		supportService.createQuery(createSupportQueryDto, user.getUsername());
		return "redirect:/support";
	}

	@PostMapping("/support/query/{id}")
	public String postToQuery(@ModelAttribute PostDto postDto, @PathVariable String id,@AuthenticationPrincipal User user) {
		postDto.setQueryId(id);
		supportService.postToQuery(postDto, user.getUsername());
		if(postDto.isResolve()) {
			this.supportService.resolveQuery(id);
		}
		return "redirect:/support/query/"+postDto.getQueryId();
	}
	
}
