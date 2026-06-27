

package com.mycompany.filexplorer.dominio;


/**
 *
 * @author Dilan Rojas Vargas
 * @author Esteban Torres Jiménez
 */

public abstract class NodoFS {
    protected String nombre;
    protected long fechaCreacion;
    protected Carpeta parent;

    public NodoFS(String nombre) {
        this.nombre = nombre;
        this.fechaCreacion = System.currentTimeMillis();
        this.parent = null;
    }

    public String getNombre() { return nombre; }
    public void setParent(Carpeta p) { this.parent = p; }
    public Carpeta getParent() { return parent; }
    public long getFechaCreacion() {return fechaCreacion;}

    @Override
    public String toString() { return nombre; }
}
