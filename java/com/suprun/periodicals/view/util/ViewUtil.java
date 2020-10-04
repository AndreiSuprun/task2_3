package com.suprun.periodicals.view.util;

import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.service.entity.SubscriptionBin;
import com.suprun.periodicals.util.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class ViewUtil {

    public static void redirectTo(HttpServletRequest request,
                                  HttpServletResponse response,
                                  String pageToRedirect) throws IOException {
        response.sendRedirect(
                request.getContextPath() + request.getServletPath() + pageToRedirect);
    }

    public static User getAuthorizedUser(HttpSession session) {
        return (User) session.getAttribute(Resource.ATTRIBUTE.getProperty("user"));
    }

    public static SubscriptionBin getSubscriptionBin(HttpSession session) {
        SubscriptionBin subscriptionBin =
                (SubscriptionBin) session.getAttribute(Resource.ATTRIBUTE.getProperty("subscription.bin"));
        if (Objects.isNull(subscriptionBin)) {
            subscriptionBin = new SubscriptionBin();
            session.setAttribute(Resource.ATTRIBUTE.getProperty("subscription.bin"), subscriptionBin);
        }
        return subscriptionBin;
    }

    public static String getReferer(HttpServletRequest request, String defaultPath) {
        String referer = defaultPath;
        String header = request.getHeader("referer");
        if (header != null && !header.isEmpty()) {
            try {
                URI uri = new URI(header);
                String path = uri.getPath();
                String query = uri.getQuery();

                referer = Objects.isNull(query) ? path : path + "?" + query;
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return referer.replaceFirst(request.getContextPath(), "")
                .replaceFirst(request.getServletPath(), "");
    }

    public static String getReferer(HttpServletRequest request) {
        return getReferer(request, Resource.PATH.getProperty("path.home"));
    }

    public static String addParameterToURI(String uri,
                                           String parameterName,
                                           String parameterValue) {
        Objects.requireNonNull(parameterName);
        Objects.requireNonNull(parameterValue);

        try {
            String newParameter = parameterName + "=" + parameterValue;
            URI oldUri = new URI(uri);
            String newQuery = oldUri.getQuery();

            if (Objects.isNull(newQuery)) {
                newQuery = newParameter;
            } else if (newQuery.contains(parameterName)) {
                newQuery = newQuery.replaceFirst(parameterName + "=" + "[^&]+", newParameter);
            } else {
                newQuery = newQuery + "&" + newParameter;
            }

            URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(),
                    oldUri.getPath(), newQuery, oldUri.getFragment());

            return newUri.getPath() + "?" + newUri.getQuery();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String removeParameterFromURI(String uri, String parameterName) {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(parameterName);

        return uri.replaceFirst("\\??&?" + parameterName + "=((.+&)|[^&]+)", "");
    }

    public static void checkErrorParameter(HttpServletRequest request,
                                           String requestAttribute) {
        String error = request.getParameter(requestAttribute);
        if (Objects.nonNull(error) && !error.isEmpty()) {
            request.setAttribute(error, true);
        }
    }


}
