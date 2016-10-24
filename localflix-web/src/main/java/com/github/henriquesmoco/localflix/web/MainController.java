package com.github.henriquesmoco.localflix.web;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

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
