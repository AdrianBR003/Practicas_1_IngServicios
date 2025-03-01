package com.IngServ.p1;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/home")
    public String getHome() {
        return "home";
    }

    @GetMapping(value = "/datosUsuario")
    public String getDatosUsuario(HttpServletRequest req, Model mod) {
        HttpSession session = req.getSession(false); // Si no existe la sesion, no tiene que crear una nueva en este path
        if(session == null){
            return "error";
        }
        Usuario user = (Usuario) session.getAttribute("usuario");
        if (user == null ) {
            System.out.println("Usuario = null");
            return "error";
        }
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cookiePerm".equals(cookie.getName())) {
                    System.out.println("Cookie recuperada: " + cookie.getName() + " = " + cookie.getValue());
                    user.setCookiePerm(cookie.getValue());
                    break;
                }
            }
        }

        mod.addAttribute("usuario", user);
        return "datosUsuario";
    }

    @PostMapping(value = "/datosUsuario")
    public String postDatosUsuario(HttpServletRequest req, HttpServletResponse res, Model mod) {
        String nombreUsuario = req.getParameter("nameUser");
        String nombre = req.getParameter("name");
        String apellidos = req.getParameter("apell");
        String email = req.getParameter("email");
        Cookie c = new Cookie("cookiePerm" + nombre, nombreUsuario + "&" + apellidos); // Simulación para comprobar como se crean cookies permanentes IND
        c.setMaxAge(3600);
        c.setPath("/datosUsuario");
        res.addCookie(c);

        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(20); // TEST para comprobar si se cierra la sesion en 20 s
        Usuario user = new Usuario(c.getValue(), nombreUsuario, nombre, apellidos, email);

        System.out.println("Cookie permanente guardada: " + c.getName() + " : " + c.getValue());

        mod.addAttribute("usuario", user);
        session.setAttribute("usuario", user);
        return "datosUsuario";
    }
}
