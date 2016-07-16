package com.github.henriquesmoco.localflix.web;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    public static final String DEFAULT_TENANT_ID = "java:/localflix-demo";
    public static final String TENANT_ID_ATTRIBUTE = "CURRENT_TENANT_ID";

    @Override
    public String resolveCurrentTenantIdentifier() {
        Optional<String> identifier = Optional.empty();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            identifier = Optional.ofNullable(
                    (String) requestAttributes.getAttribute(TENANT_ID_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST));
        }
        return identifier.orElse(DEFAULT_TENANT_ID);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
