package com.h2net.h2monitoringweb.common;

import com.h2net.h2monitoringweb.login.LoginService;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CustomFilter implements Filter {
    private static final List<String> EXCLUDED_URLS = Arrays.asList(
            "^/$",
            "/login",
            "/api/login",
            "/css/.*$",
            "/js/.*$",
            "/images/.*$",
            "/icon/.*$",
            "/static/.*$",
            "/api/join/.*$",
            "/public/"
    );

    private LoginService loginService;
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 작업 (필요 시)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // 제외할 경로인지 확인 (로그인 페이지, CSS, JS 등)
        boolean isExcluded = EXCLUDED_URLS.stream().anyMatch(excluded -> requestURI.matches(excluded));

        // 제외할 경로이거나 이미 인증된 경우 필터 통과
        if (isExcluded) {
            chain.doFilter(request, response); // 다음 필터 또는 서블릿으로 요청 전달
            return;
        }


        // 인증되지 않은 사용자는 로그인 페이지로 리다이렉트
        // 제외 경로가 아닌 경우에만 인증 검사 진행
        boolean isAuthenticated = false;
        Cookie[] cookies = httpRequest.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("loginToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        String auth = (String) httpRequest.getSession().getAttribute("auth");


        if (token != null && auth != null) {
            try {
                boolean userInfo = loginService.isValidCookieAuth(token);

                if (userInfo) {
                    isAuthenticated = true;
                    if (requestURI.startsWith("/admin") && "02".equals(auth)) {
                        httpResponse.setContentType("text/html;charset=UTF-8");
                        httpResponse.getWriter().write("<html><head><title>알림</title>");
                        httpResponse.getWriter().write("<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>");
                        httpResponse.getWriter().write("</head><body>");
                        httpResponse.getWriter().write("<script type='text/javascript'>");
                        httpResponse.getWriter().write("Swal.fire({");
                        httpResponse.getWriter().write("icon: 'error',");
                        httpResponse.getWriter().write("title: '권한이 없습니다.'");
                        httpResponse.getWriter().write("}).then(() => {");
                        httpResponse.getWriter().write("window.location.href = '/monitor/water';");
                        httpResponse.getWriter().write("});");
                        httpResponse.getWriter().write("</script>");
                        httpResponse.getWriter().write("</body></html>");
                        return;
                    }
                } else {
                    isAuthenticated = false;
                    Cookie invalidCookie = new Cookie("loginToken", "");
                    invalidCookie.setPath("/");
                    invalidCookie.setHttpOnly(true);
                    invalidCookie.setMaxAge(0); // 즉시 만료
                    httpResponse.addCookie(invalidCookie);
                }
            } catch (Exception e) {
                isAuthenticated = false;
                e.printStackTrace();
                Cookie invalidCookie = new Cookie("loginToken", "");
                invalidCookie.setPath("/");
                invalidCookie.setHttpOnly(true);
                invalidCookie.setMaxAge(0); // 즉시 만료
                httpResponse.addCookie(invalidCookie);
            }
        } else {
            isAuthenticated = false;
        }

        if (!isAuthenticated) {
            httpResponse.setContentType("text/html;charset=UTF-8");
            httpResponse.getWriter().write("<html><head><title>알림</title>");
            httpResponse.getWriter().write("<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>");
            httpResponse.getWriter().write("</head><body>");
            httpResponse.getWriter().write("<script type='text/javascript'>");
            httpResponse.getWriter().write("Swal.fire({");
            httpResponse.getWriter().write("icon: 'error',");
            httpResponse.getWriter().write("title: '세션이 만료되었습니다.'");
            httpResponse.getWriter().write("}).then(() => {");
            httpResponse.getWriter().write("window.location.href = '/';"); // 로그인 페이지로 리다이렉트
            httpResponse.getWriter().write("});");
            httpResponse.getWriter().write("</script>");
            httpResponse.getWriter().write("</body></html>");
            return;
        }

        // 인증된 사용자이고 제외 경로가 아닌 경우, 다음 필터 또는 서블릿으로 요청 전달
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 필터 정리 작업 (필요 시)
    }
}
