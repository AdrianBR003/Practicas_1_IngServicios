package com.IngServ.p1;

import jakarta.servlet.http.HttpSession;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String cookiePerm;
    private String nombreUsuario;
    private String nombre;
    private String apellidos;
    private String email;


    public Usuario() {
        this.cookiePerm = "";
        this.nombreUsuario = "";
        this.nombre = "";
        this.apellidos = "";
        this.email = "";
    }

    public Usuario(String cookiePerm, String nombreUsuario, String nombre, String apellidos, String email) {
        this.cookiePerm = cookiePerm;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
    }




    public String getCookiePerm() {
        return cookiePerm;
    }

    public void setCookiePerm(String sessionID) {
        this.cookiePerm = sessionID;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
