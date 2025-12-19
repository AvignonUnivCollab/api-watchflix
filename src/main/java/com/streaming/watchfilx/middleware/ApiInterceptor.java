package com.streaming.watchfilx.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("➡️ Interceptor : Requête vers " + request.getRequestURI());

        // Exemple : Vérifier un header personnalisé
        String apiKey = request.getHeader("X-API-KEY");

        if (apiKey == null || !apiKey.equals("123456")) {
            System.out.println("⛔ API KEY manquante ou invalide");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized - API KEY invalid");
            return false; // Stop la requête
        }

        return true; // Continue l'exécution de l’API
    }
}
