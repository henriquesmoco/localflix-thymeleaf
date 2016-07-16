package com.github.henriquesmoco.localflix.web;

import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.github.henriquesmoco.localflix.web.CurrentTenantIdentifierResolverImpl.TENANT_ID_ATTRIBUTE;

public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        Map<String, Object> pathVars = (Map<String, Object>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVars != null && pathVars.containsKey("tenantid")) {
            req.setAttribute(TENANT_ID_ATTRIBUTE, pathVars.get("tenantid"));
        }
        return true;
    }
}