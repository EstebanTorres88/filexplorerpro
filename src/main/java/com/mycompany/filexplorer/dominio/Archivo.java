/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.filexplorer.dominio;

/**
 *
 * @author jwd
 */

public class Archivo extends NodoFS {
    private final String extension;
    private final int tamKB;
    private final String contenido;

    public Archivo(String nombre, String extension, int tamKB, String contenido) {
        super(nombre);
        this.extension = extension;
        this.tamKB = tamKB;
        this.contenido = contenido;
    }

    public int getTamKB() { return tamKB; }
    public String getExtension() { return extension; }

    public String getContenido() {
        return contenido;
    }

    @Override
    public String toString() {
        return nombre + "." + extension + " (" + tamKB + "KB)";
    }
}
