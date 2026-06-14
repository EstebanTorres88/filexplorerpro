/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.filexplorer.dominio;

/**
 *
 * @author jwd
 */
import com.mycompany.filexplorer.estructuras.LinkedListGeneric;

public class Carpeta extends NodoFS {
    private LinkedListGeneric<NodoFS> hijos = new LinkedListGeneric<>();

    public Carpeta(String nombre) { super(nombre); }

    public void addChild(NodoFS n) {
        n.setParent(this);
        hijos.add(n);
    }

    public boolean removeChild(String name) {
        for (int i = 0; i < hijos.size(); i++) {
            NodoFS c = hijos.get(i);
            if (c != null && c.getNombre().equals(name)) {
                hijos.remove(c);
                return true;
            }
        }
        return false;
    }

    public NodoFS findChild(String name) {
        for (int i = 0; i < hijos.size(); i++) {
            NodoFS c = hijos.get(i);
            if (c != null && c.getNombre().equals(name)) return c;
        }
        return null;
    }

    public String listChildren() {
        StringBuilder folders = new StringBuilder();
        StringBuilder files = new StringBuilder();
        for (int i = 0; i < hijos.size(); i++) {
            NodoFS c = hijos.get(i);
            if (c instanceof Carpeta) {
                if (folders.length() > 0) folders.append(", ");
                folders.append(c.getNombre());
            } else {
                if (files.length() > 0) files.append(", ");
                files.append(c.toString());
            }
        }
        
        StringBuilder out = new StringBuilder();

        if (folders.length() > 0) {
            out.append(folders);
        }

        if (files.length() > 0) {
            if (folders.length() > 0) out.append("\n");
            out.append(files);
        }

        return out.toString() + "\n";
    }

    @Override
    public String toString() { return nombre + "/"; }
}
