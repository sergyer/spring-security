package com.example.web.controller;

import com.example.domain.dto.AddTransactionToPortfolioDto;
import com.example.domain.dto.DeleteTransactionsDto;
import com.example.domain.dto.ListTransactionsDto;
import com.example.domain.dto.TransactionDetailsDto;
import com.example.domain.service.PortfolioQueryService;
import com.example.domain.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PortfolioQueryController {

    private final PortfolioQueryService portfolioService;

    private final PricingService pricingService;

    @GetMapping("/")
    public String index() {
        return "redirect:/portfolio";
    }

    @GetMapping("/portfolio")
    public ModelAndView positions(@AuthenticationPrincipal User user) {
        ModelAndView model = new ModelAndView();
        model.addObject("positionsResponse", portfolioService.getPortfolioPositions(user.getUsername()));
        model.addObject("transaction", new AddTransactionToPortfolioDto());
        return model;
    }

    @GetMapping("/price")
    public ResponseEntity<BigDecimal> price() {
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(2, TimeUnit.MINUTES).cachePublic().noTransform())
                             .body(pricingService.getCurrentPriceForCrypto("BTC"));
    }

    @GetMapping(value = {"/portfolio/transactions", "/portfolio/transactions/{symbol}"})
    public ModelAndView listTransactionsForPortfolio(@AuthenticationPrincipal User user, @PathVariable(required = false) String symbol) {
        final ListTransactionsDto transactions = portfolioService.getPortfolioTransactions(user.getUsername());
        ModelAndView model = new ModelAndView();
        if (symbol != null) {
            List<TransactionDetailsDto> symbolTrans = transactions.getTransactions()
                                                                  .stream()
                                                                  .filter(trans -> trans.getSymbol().equals(symbol))
                                                                  .collect(Collectors.toList());
            model.addObject("transactions", symbolTrans);
        } else {
            model.addObject("transactions", transactions.getTransactions());
        }
        model.addObject("selected", new DeleteTransactionsDto());
        model.setViewName("transactions");
        return model;
    }

}
