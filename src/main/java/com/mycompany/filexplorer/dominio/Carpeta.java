
package com.mycompany.filexplorer.dominio;

/**
 *
 * @author Dilan Rojas Vargas
 * @author Esteban Torres Jiménez
 */
import com.mycompany.filexplorer.estructuras.LinkedListGeneric;

public class Carpeta extends NodoFS {
    private LinkedListGeneric<NodoFS> hijos = new LinkedListGeneric<>();

    public Carpeta(String nombre) {
        super(nombre);
    }

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
            if (c != null && c.getNombre().equals(name))
                return c;
        }
        return null;
    }

    public String listChildren() {
        StringBuilder folders = new StringBuilder();
        StringBuilder files = new StringBuilder();
        for (int i = 0; i < hijos.size(); i++) {
            NodoFS c = hijos.get(i);
            if (c instanceof Carpeta) {
                if (folders.length() > 0)
                    folders.append(", ");
                folders.append(c.getNombre());
            } else {
                if (files.length() > 0)
                    files.append(", ");
                files.append(c.toString());
            }
        }

        StringBuilder out = new StringBuilder();

        if (folders.length() > 0) {
            out.append(folders);
        }

        if (files.length() > 0) {
            if (folders.length() > 0)
                out.append("\n");
            out.append(files);
        }

        return out.toString() + "\n";
    }

    public String listChildrenLong() {

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < hijos.size(); i++) {
            NodoFS child = hijos.get(i);
            if (child instanceof Carpeta) {
                output.append(child.getFechaCreacion()).append("  ").append(child.getNombre()).append("/\n");
            } else {
                Archivo file = (Archivo) child;
                output.append(file.getFechaCreacion()).append("  ").append(file.toString()).append("\n");
            }
        }
        return output.toString();
    }

    public void sortChildBySize() {
        hijos.sort((a, b) -> {
            int tamanoA = (a instanceof Archivo archivo) ? archivo.getTamKB() : 0;
            int tamanoB = (b instanceof Archivo archivo) ? archivo.getTamKB() : 0;
            return Integer.compare(tamanoA, tamanoB);
        });
    }

    public void sortChildByName() {
        hijos.sort((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));
    }

    @Override
    public String toString() {
        return nombre + "/";
    }

    public LinkedListGeneric<NodoFS> getHijos() {
        return hijos;
    }
}
