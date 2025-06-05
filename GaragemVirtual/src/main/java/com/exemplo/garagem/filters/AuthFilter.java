package com.exemplo.garagem.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        urlPatterns = "/*",
        filterName = "AuthFilter",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)
@Order(2) // Segundo filtro a ser executado
public class AuthFilter implements Filter {

    private static final String[] PUBLIC_PATHS = {
            "/index.html", "/", "/auth/", "/api/auth/", "/assets/"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Verificar se o path é público
        for (String publicPath : PUBLIC_PATHS) {
            if (path.startsWith(publicPath)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // Verificar sessão
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            // Para APIs, retorne 401; para páginas, redirecione
            if (path.startsWith("/api/")) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                res.sendRedirect(req.getContextPath() + "/auth/login.html");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}