package com.github.henriquesmoco.localflix.staff;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/{tenantid}/staff")
public class StaffController {

    private final StaffRepository repo;

    @Autowired
    public StaffController(StaffRepository repo) {
        this.repo = repo;
    }

    @ModelAttribute
    public void setPage(Model model) {
        model.addAttribute("sitesection", "staff");
    }


    @GetMapping
    public String list() {
        return "staff/list";
    }

    @GetMapping(params = "json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> listJson(Model model, Pageable pageable,
                                 @RequestParam("draw") Integer draw,
                                 @RequestParam(value = "search[value]", defaultValue = "") String search) {
        Page<Staff> page;
        if (StringUtils.isNotBlank(search)) {
            search = StringUtils.join("%", search, "%");
            page = repo.findByAllFieldsContaining(search, pageable);
        } else {
            page = repo.findAll(pageable);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("data", page.getContent());
        data.put("draw", draw);
        data.put("recordsTotal", page.getTotalElements());
        data.put("recordsFiltered", page.getTotalElements());
        return data;
    }
}
