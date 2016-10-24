package com.github.henriquesmoco.localflix.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/{tenantid}")
    public String home() {
        return "home";
    }
    @RequestMapping("/")
    public String homeDefault() {
        return String.format("redirect:/%s", CurrentTenantIdentifierResolverImpl.DEFAULT_TENANT_ID);
    }
}
