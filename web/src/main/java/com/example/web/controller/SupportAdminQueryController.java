package com.example.web.controller;

import com.example.domain.service.SupportQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class SupportAdminQueryController {

    private final SupportQueryService supportQueryService;

    @GetMapping("/support/admin")
    public ModelAndView getSupportQueries() {
        return new ModelAndView("support", "queries", supportQueryService.getSupportQueriesForAllUsers());
    }

}
