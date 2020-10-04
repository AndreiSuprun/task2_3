package com.suprun.periodicals.view.filter;

import com.suprun.periodicals.view.util.ViewUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LocaleFilter implements Filter {
    private final static String LANG = "lang";
    private final static String LOCALE = "locale";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (req.getParameter(LANG) != null) {
            replaceUserLocale(req);
            String referer = ViewUtil.getReferer(req);
            ViewUtil.redirectTo(req, res, referer);
            return;
        }
        if (req.getSession().getAttribute(LOCALE) == null) {
            setUserLocale(req);
        }
        chain.doFilter(request, response);
    }

    private void replaceUserLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String langParameter = request.getParameter(LANG);
        Locale locale = SupportedLocale.getLocaleOrDefault(langParameter);
        session.setAttribute(LOCALE, locale);
    }

    private void setUserLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = SupportedLocale.getDefault();
        session.setAttribute(LOCALE, locale);
    }

    public enum SupportedLocale {
        EN(new Locale("en", "EN")),
        RU(new Locale("ru", "BY"));

        private final static Locale DEFAULT_LOCALE = RU.getLocale();

        private Locale locale;

        SupportedLocale(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }

        public static Locale getDefault() {
            return DEFAULT_LOCALE;
        }

        public static Locale getLocaleOrDefault(String lang) {
            return Arrays.stream(SupportedLocale.values())
                    .map(SupportedLocale::getLocale)
                    .filter(x -> x.getLanguage().equals(lang))
                    .findFirst()
                    .orElse(getDefault());
        }

        public static List<String> getSupportedLanguages() {
            return Arrays.stream(SupportedLocale.values())
                    .map(x -> x.getLocale().getLanguage())
                    .collect(Collectors.toList());
        }
    }
}
