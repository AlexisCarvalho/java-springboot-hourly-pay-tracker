package dev.alexis.hourlypaytracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller para rotas de páginas estáticas.
 */
@Controller
public class PageController {

    @GetMapping({"/", "/account"})
    public String account() {
        return "forward:/website/account.html";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "forward:/website/dashboard.html";
    }
}
