package com.github.henriquesmoco.localflix.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("{tenantid}/catalog")
public class CatalogController {

    private final CatalogRepository catalogRepo;

    @Autowired
    public CatalogController(CatalogRepository catalogRepo) {
        this.catalogRepo = catalogRepo;
    }

    @ModelAttribute
    public void setPage(Model model) {
        model.addAttribute("sitesection", "catalog");
    }

    @RequestMapping("list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("catalog/list");
        mv.addObject("mediaList", catalogRepo.findAll());
        return mv;
    }
}
