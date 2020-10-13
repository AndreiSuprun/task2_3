package com.suprun.periodicals.view.filter;

import com.suprun.periodicals.util.Resource;
import com.suprun.periodicals.view.util.ViewUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationFilter implements Filter {
    private static final Set<String> unauthententicatedPaths = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        unauthententicatedPaths.add(Resource.PATH.getProperty("path.home"));
        unauthententicatedPaths.add(Resource.PATH.getProperty("path.signin"));
        unauthententicatedPaths.add(Resource.PATH.getProperty("path.register"));
        unauthententicatedPaths.add(Resource.PATH.getProperty("path.periodical"));
        unauthententicatedPaths.add(Resource.PATH.getProperty("path.catalog"));
        unauthententicatedPaths.add(Resource.PATH.getProperty("path.error"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String requestPath = req.getPathInfo();

        boolean isLoggedIn = session != null &&
                session.getAttribute(Resource.ATTRIBUTE.getProperty("user")) != null;
        boolean isRegisterRequest = Resource.PATH.getProperty("path.register").equals(requestPath);
        boolean isSignInRequest = Resource.PATH.getProperty("path.signin").equals(requestPath);

        if (isLoggedIn) {
            if (isRegisterRequest || isSignInRequest) {
                ViewUtil.redirectTo(req, resp, Resource.PATH.getProperty("path.home"));
            } else {
                chain.doFilter(req, resp);
            }
        } else {
            if (unauthententicatedPaths.contains(requestPath)) {
                chain.doFilter(req, resp);
            } else {
                ViewUtil.redirectTo(req, resp, Resource.PATH.getProperty("path.signin"));
            }
        }
    }

    @Override
    public void destroy() {

    }
}
