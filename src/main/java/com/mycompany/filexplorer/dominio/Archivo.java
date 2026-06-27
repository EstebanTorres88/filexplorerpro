

package com.mycompany.filexplorer.dominio;
/**
 *
 * @author Dilan Rojas Vargas
 * @author Esteban Torres Jiménez
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
