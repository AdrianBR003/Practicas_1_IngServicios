package com.IngServ.p1;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

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

        // VAMOS A RECOGER LOS DATOS DEL USUARIO DESDE LA COOKIE CREADA `Cdatos`

        Cookie[] cookies = req.getCookies();
        Usuario user = (Usuario) session.getAttribute("usuario");

        if (cookies != null) {
            System.out.println(Arrays.stream(cookies).toString());
            for (Cookie cookie : cookies) { // En este caso no hace falta, pero si hubiera más sí
                if ("cookiePerm".equals(cookie.getName())) {
                    System.out.println("Cookie recuperada: " + cookie.getName() + " = " + cookie.getValue());
                    mod.addAttribute("cookiePerm", cookie.getValue());
                }

                 // Explicación de porque no se puede aplicar esto más abajo
//                if("Cdatos".equals(cookie.getName())){
//                    System.out.println("Cookie recuperada: " + cookie.getName() + " = " + cookie.getValue());
//                    user = new Usuario(cookie.getAttribute("nombreUsuario"), cookie.getAttribute("nombre"), cookie.getAttribute("apellidos"), cookie.getAttribute("email"));
//                    System.out.println(user);
//                }
            }
        }

        // Creamos un usuario nulo, porque desde un GET, no vamos a tener la información de este guardada si no la pasa por un POST
        mod.addAttribute("usuario", user);
        mod.addAttribute("sessionID", session.getId());
        return "datosUsuario";
    }

    @PostMapping(value = "/datosUsuario")
    public String postDatosUsuario(HttpServletRequest req, HttpServletResponse res, Model mod) {
        String nombreUsuario = req.getParameter("nameUser");
        String nombre = req.getParameter("name");
        String apellidos = req.getParameter("apell");
        String email = req.getParameter("email");

        // COOKIE PERMANENTE

        Cookie c = new Cookie("cookiePerm", nombreUsuario + "&" + apellidos); // Simulación para comprobar como se crean cookies permanentes IND
        c.setMaxAge(3600);
        c.setPath("/datosUsuario");
        res.addCookie(c);

        // COOKIE DE SESION (SESSION)

        HttpSession session = req.getSession(); //
        session.setMaxInactiveInterval(20); // TEST para comprobar si se cierra la sesion en 20 s por inactividad
        Usuario user = new Usuario(nombreUsuario, nombre, apellidos, email);

        System.out.println("Cookie permanente guardada: " + c.getName() + " : " + c.getValue());

        // VAMOS A GUARDAR LOS DATOS DEL USUARIO EN UNA COOKIE PERMANENTE LLAMADA DATOS

        /**
         * NO SE PUEDE HACER ESTO
         *
         * Los métodos setAttribute y getAttribute, solo aplican a los definidos dentro de la Clase Cookie, por lo que
         * no podemos establecer unos valores propios.
         *
         * La única forma de hacerlo sería insertar todo el contenido en una cadena, y ponerle ese valor a la Cookie
         *
         * Sin embargo, en un objeto HTTPSession sí que se puede
         */

//        Cookie c2 = new Cookie("Cdatos", nombreUsuario);
//        c2.setAttribute("nombreUsuario", user.getNombreUsuario());
//        c2.setAttribute("nombre", user.getNombre());
//        c2.setAttribute("apellidos", user.getApellidos());
//        c2.setAttribute("email", user.getEmail());
//        c2.setMaxAge(3600);
//        c2.setPath("/datosUsuario");
//        res.addCookie(c2);

        // VAMOS A GUARDAR LOS DATOS DEL USUARIO EN LA SESION
        session.setAttribute("usuario", user);

        // ENVIAMOS LOS DATOS

        mod.addAttribute("cookiePerm", c.getValue());
        mod.addAttribute("usuario", user);
        mod.addAttribute("session", session.getId());
        return "datosUsuario";
    }
}
