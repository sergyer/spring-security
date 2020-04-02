package com.example.web.controller;

import com.example.domain.dto.AddTransactionToPortfolioDto;
import com.example.domain.dto.DeleteTransactionsDto;
import com.example.domain.service.PortfolioCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class PortfolioCommandController {

    private final PortfolioCommandService commandService;


    @PostMapping("/portfolio/transactions")
    public ModelAndView addTransactionToPortfolio(@ModelAttribute("transaction") AddTransactionToPortfolioDto request, @AuthenticationPrincipal User user) {
        commandService.addTransactionToPortfolio(request, user.getUsername());
        return new ModelAndView("redirect:/portfolio");
    }

    @PostMapping("/portfolio/transactions/remove")
    public ModelAndView deleteTransactionFromPortfolio(@ModelAttribute("selected") DeleteTransactionsDto request,@AuthenticationPrincipal User user) {
        for (String id : request.getId()) {
            commandService.removeTransactionFromPortfolio(id,user.getUsername());
        }
        return new ModelAndView("redirect:/portfolio");
    }

}
