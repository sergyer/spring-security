package com.example.web.controller;

import com.example.domain.dto.CreateSupportQueryDto;
import com.example.domain.dto.PostDto;
import com.example.domain.dto.SupportQueryDto;
import com.example.domain.service.SupportQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class SupportQueryController {

    private final SupportQueryService supportService;

    @GetMapping("/support")
    public ModelAndView getQueries(@AuthenticationPrincipal User user) {
        return new ModelAndView("support", "queries", supportService.getSupportQueriesForUser(user.getUsername()));
    }

    @GetMapping("/support/query/{id}")
    public ModelAndView getQuery(@PathVariable String id) {
        SupportQueryDto query = supportService.getSupportQueryById(id);
        ModelAndView model = new ModelAndView("query", "query", query);
        PostDto newPost = new PostDto();
        newPost.setResolve(query.isResolved());
        model.addObject("newPost", new PostDto());
        return model;
    }

    @GetMapping("/support/compose")
    public ModelAndView createNewSupportQuery() {
        ModelAndView model = new ModelAndView();
        model.addObject("newQuery", new CreateSupportQueryDto());
        model.setViewName("compose");
        return model;
    }

}
