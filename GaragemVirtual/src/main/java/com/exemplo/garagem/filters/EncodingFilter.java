package com.exemplo.garagem.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(
        urlPatterns = "/*",
        filterName = "EncodingFilter",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD},
        asyncSupported = true
)
@Order(1) // Primeiro filtro a ser executado
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Configurar encoding para requisições e respostas
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}