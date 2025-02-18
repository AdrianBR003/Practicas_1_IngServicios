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

    @GetMapping(value="/home")
    public String getHome(){

        return "home";
    }

    @GetMapping(value="/datosUsuario")
    public String getDatosUsuario(HttpServletRequest req){
        HttpSession session = req.getSession();
        Usuario user = (Usuario) session.getAttribute("usuario");
        if(user==null){
            return "erorr";
        }
        System.out.println("USUARIOO!!!" + user);
        System.out.println("USUARIOO!!!" + user);
        session.setAttribute("usuario", user);
        return "datosUsuario";
    }


    @PostMapping(value="/datosUsuario")
    public String postDatosUsuario(HttpServletRequest req, HttpServletResponse res , Model mod){
        String nombreUsuario = req.getParameter("nameUser");
        String nombre = req.getParameter("name");
        String apellidos = req.getParameter("apell");
        String email = req.getParameter("email");

//        mod.addAttribute("nameUser",nombreUsuario);
//        mod.addAttribute("nombre", nombre);
//        mod.addAttribute("apellidos",apellidos);
//        mod.addAttribute("email",email);

        Usuario user = new Usuario(nombreUsuario,nombre,apellidos,email);

        Cookie c = new Cookie("userIdCookie", nombreUsuario);
        c.setMaxAge(60);
        c.setPath("/home");
        res.addCookie(c);
        HttpSession session = req.getSession();
        mod.addAttribute("usuario", user);
        session.setAttribute("usuario", user);
        return "datosUsuario";
    }

}
