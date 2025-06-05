package com.exemplo.garagem.controllers;

import com.exemplo.garagem.dao.UsuarioDAO;
import com.exemplo.garagem.models.Usuario;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "AuthController",
        urlPatterns = {"/api/auth/login", "/api/auth/logout", "/api/auth/register"},
        loadOnStartup = 1
)
public class AuthController extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();

        if ("/login".equals(action)) {
            processarLogin(req, resp);
        } else if ("/register".equals(action)) {
            processarRegistro(req, resp);
        } else if ("/logout".equals(action)) {
            processarLogout(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // No AuthController
    private void processarLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Ler JSON do corpo
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            buffer.append(line);
        }

        // Converter para objeto
        AuthRequest authRequest = gson.fromJson(buffer.toString(), AuthRequest.class);

        try {
            // Autenticar usando DAO
            Usuario usuario = usuarioDAO.autenticar(authRequest.email, authRequest.senha);

            if (usuario != null) {
                HttpSession session = req.getSession();
                session.setAttribute("usuarioId", usuario.getId());

                // Retornar usuário autenticado (sem senha)
                resp.setContentType("application/json");
                resp.getWriter().write(gson.toJson(usuario));
            } else {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // Classe auxiliar para desserialização
    private static class AuthRequest {
        public String email;
        public String senha;
    }

    private void processarRegistro(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Ler JSON do corpo
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            buffer.append(line);
        }

        // Converter para objeto Usuario
        Usuario novoUsuario = gson.fromJson(buffer.toString(), Usuario.class);

        try {
            // Verificar se email já existe
            if (usuarioDAO.emailExiste(novoUsuario.getEmail())) {
                resp.sendError(HttpServletResponse.SC_CONFLICT, "Email já cadastrado");
                return;
            }

            // Criar novo usuário
            usuarioDAO.criarUsuario(novoUsuario);
            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void processarLogout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    // Classe interna para representar credenciais
    private static class Credenciais {
        public String email;
        public String senha;
    }
}