package com.github.henriquesmoco.localflix.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.github.henriquesmoco.localflix.web.CurrentTenantIdentifierResolverImpl.DEFAULT_TENANT_ID;
import static com.github.henriquesmoco.localflix.web.CurrentTenantIdentifierResolverImpl.TENANT_ID_ATTRIBUTE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CurrentTenantIdentifierResolverImplTest {

    private CurrentTenantIdentifierResolverImpl tenantIdentifier;

    @Before
    public void setUp() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void resolveTenantIdentifier_OfRequestWithoutAttributes_ReturnDefaultTenantId() throws Exception {
        tenantIdentifier = new CurrentTenantIdentifierResolverImpl();

        String tenantID = tenantIdentifier.resolveCurrentTenantIdentifier();

        assertThat(tenantID, is(equalTo(DEFAULT_TENANT_ID)));
    }

    @Test
    public void resolveTenantIdentifier_OfRequestWithTenantId_ReturnCorrectTenantId() throws Exception {
        tenantIdentifier = new CurrentTenantIdentifierResolverImpl();
        setRequestAttribute(TENANT_ID_ATTRIBUTE, "tenant1");

        String tenantID = tenantIdentifier.resolveCurrentTenantIdentifier();

        assertThat(tenantID, is(equalTo("tenant1")));
    }

    private void setRequestAttribute(String name, Object value) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        attributes.setAttribute(name, value, RequestAttributes.SCOPE_REQUEST);
    }
}
