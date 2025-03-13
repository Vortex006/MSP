package com.vortex.msp.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MethodOverridingRequestWrapper extends HttpServletRequestWrapper {

    private final String method;

    public MethodOverridingRequestWrapper(HttpServletRequest request, String method) {
        super(request);
        this.method = method;
    }

    @Override
    public String getMethod() {
        return method;
    }
}
