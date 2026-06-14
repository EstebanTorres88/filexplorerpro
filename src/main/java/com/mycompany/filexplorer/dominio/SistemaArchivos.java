/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.filexplorer.dominio;

/**
 *
 * @author jwd
 */

public class SistemaArchivos {
    private Carpeta root;
    private Carpeta current;

    public SistemaArchivos() {
        root = new Carpeta("raiz");
        current = root;
    }

    public Carpeta getRoot() { return root; }
    public Carpeta getCurrent() { return current; }

    public boolean mkdir(String name) {
        if (current.findChild(name) != null) return false;
        Carpeta c = new Carpeta(name);
        current.addChild(c);
        return true;
    }
    
    public boolean rmdir(String name) {
        NodoFS child = current.findChild(name);

        if (!(child instanceof Carpeta folder)) {
            return false;
        }

        if (folder.listChildren().length() > 0) {
            return false;
        }

        current.removeChild(name);
        return true;
    }
    
    public boolean rm(String name, boolean recursive) {
        NodoFS child = current.findChild(name);

        if (child == null) {
            return false;
        }

        if (child instanceof Carpeta) {
            if (!recursive) {
                return false;
            }
        }

        current.removeChild(name);
        return true;
    }

    public boolean touch(String name, String ext, int tamKB) {
        if (current.findChild(name) != null) return false;
        Archivo a = new Archivo(name, ext, tamKB, "contenido_simulado");
        current.addChild(a);
        return true;
    }

    public boolean cd(String name) {
        if ("..".equals(name)) {
            if (current.getParent() != null) { current = current.getParent(); return true; }
            else return false;
        }
        NodoFS found = current.findChild(name);
        if (found != null && found instanceof Carpeta) { current = (Carpeta) found; return true; }
        return false;
    }
    
    public String ls() { return current.listChildren(); }
}
