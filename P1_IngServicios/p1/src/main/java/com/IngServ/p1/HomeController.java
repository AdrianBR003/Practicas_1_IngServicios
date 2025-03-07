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

/*
    DEVELOPER'S NOTE:

        1. The comments for this session are intended to record the creation process, therefore, the code has not been properly cleaned up and sorted.
           In the following longer sessions, the best practices will be followed.
 */

@Controller
public class HomeController {

    @GetMapping(value = "/home")
    public String getHome() {
        return "home";
    }

    @GetMapping(value = "/userData")
    public String getUserData(HttpServletRequest req, Model mod) {
        HttpSession session = req.getSession(false); // If the session does not exist, you do not have to create a new one in this path.
        if(session == null){
            return "error";
        }

        // LET'S COLLECT USER DATA FROM THE CREATED COOKIE `Cdata`.

        Cookie[] cookies = req.getCookies();
        User user = (User) session.getAttribute("user");

        if (cookies != null) {
            System.out.println(Arrays.stream(cookies));
            for (Cookie cookie : cookies) { // In this case it is not necessary, but if there were more, yes.
                if ("cookiePerm".equals(cookie.getName())) {
                    System.out.println("Cookie: " + cookie.getName() + " = " + cookie.getValue());
                    mod.addAttribute("cookiePerm", cookie.getValue());
                }

//                if("Cdatos".equals(cookie.getName())){
//                    System.out.println("Cookie recuperada: " + cookie.getName() + " = " + cookie.getValue());
//                    user = new User(cookie.getAttribute("nameUser"), cookie.getAttribute("name"), cookie.getAttribute("lastname"), cookie.getAttribute("email"));
//                    System.out.println(user);
//                }
            }
        }
        mod.addAttribute("usuario", user);
        mod.addAttribute("sessionID", session.getId());
        return "userData";
    }

    @PostMapping(value = "/userData")
    public String postUserData(HttpServletRequest req, HttpServletResponse res, Model mod) {
        String nameUser = req.getParameter("nameUser");
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");

        // PERMANENT COOKIE

        Cookie c = new Cookie("cookiePerm", nameUser + "&" + lastname);
        c.setMaxAge(3600);
        c.setPath("/userData");
        res.addCookie(c);

        // SESSION COOKIE

        HttpSession session = req.getSession(); //
        session.setMaxInactiveInterval(20); // TEST para comprobar si se cierra la sesion en 20 s por inactividad
        User user = new User(nameUser, name, lastname, email);

        System.out.println("Cookie permanente guardada: " + c.getName() + " : " + c.getValue());

        // WE WILL STORE THE USER'S DATA IN A PERMANENT COOKIE CALLED DATA

        /*
         * YOU CAN'T DO THIS
         *
         * The setAttribute and getAttribute methods only apply to those defined within the Cookie Class, so we can't set our own values.
         * we cannot set our own values.
         *
         * The only way to do it would be to insert all the content in a string, and set that value to the Cookie.
         *
         * However, in a HTTPSession object you can
         */


//        Cookie c2 = new Cookie("Cdatos", nameUser);
//        c2.setAttribute("nameUser", user.getNameUser());
//        c2.setAttribute("name", user.getName());
//        c2.setAttribute("lastname", user.getLastName());
//        c2.setAttribute("email", user.getEmail());
//        c2.setMaxAge(3600);
//        c2.setPath("/userData");
//        res.addCookie(c2);

        // LET'S SAVE THE USER'S DATA IN THE SESSION
        session.setAttribute("usuario", user);

        // ENVIAMOS LOS DATOS

        mod.addAttribute("cookiePerm", c.getValue());
        mod.addAttribute("user", user);
        mod.addAttribute("session", session.getId());
        return "userData";
    }
}
