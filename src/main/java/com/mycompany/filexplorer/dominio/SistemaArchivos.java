
package com.mycompany.filexplorer.dominio;

import com.mycompany.filexplorer.estructuras.QueueGeneric;
import com.mycompany.filexplorer.estructuras.StackGeneric;

/**
 *
 * @author jwd
 * @author Esteban Torres Jiménez
 */

public class SistemaArchivos {
    private Carpeta root;
    private Carpeta current;

    private StackGeneric<Carpeta> back = new StackGeneric<>();
    private StackGeneric<Carpeta> forward = new StackGeneric<>();

    private QueueGeneric<String> taskQueue = new QueueGeneric<>();

    public SistemaArchivos() {
        root = new Carpeta("raiz");
        current = root;
    }

    public Carpeta getRoot() {
        return root;
    }

    public Carpeta getCurrent() {
        return current;
    }

    public void createFileSystemTree() {

    mkdir("docs");
    mkdir("imgs");
    mkdir("music");
    mkdir("config");
    mkdir("projects");

    cd("docs");
    mkdir("work");
    mkdir("university");
    touch("cv", "pdf", 120);

    cd("work");
    touch("informe", "pdf", 100);
    touch("presentacion", "pptx", 80);
    touch("presupuesto", "xlsx", 60);

    back();

    cd("university");
    touch("apuntes", "txt", 200);
    touch("tesis", "pdf", 300);

    goToRoot();
    cd("imgs");
    mkdir("vacaciones");
    touch("perfil", "jpg", 40);
    touch("logo", "png", 65);

    cd("vacaciones");
    touch("playa", "jpg", 33);
    touch("montana", "jpg", 40);

    goToRoot();
    cd("music");
    mkdir("rap");
    mkdir("raggae");

    cd("rap");
    touch("AllEyezOnMe", "wav", 1000);
    touch("21Questions", "mp3", 300);

    back();

    cd("raggae");
    touch("ThreeLittleBirds", "wav", 2000);
    touch("Jamming", "mp3", 500);

    goToRoot();

    cd("projects");

    mkdir("filexplorer");
    mkdir("stockflow");

    cd("filexplorer");
    touch("Main", "java", 100);
    touch("README", "md", 30);

    back();

    cd("stockflow");

    mkdir("backend");
    mkdir("frontend");

    cd("backend");
    touch("SaleRepository", "java", 120);

    back();

    cd("frontend");
    touch("App", "tsx", 110);

    goToRoot();

    cd("config");
    touch("settings", "json", 5);

    goToRoot();
}

    public boolean mkdir(String name) {
        if (current.findChild(name) != null)
            return false;
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
        if (current.findChild(name) != null)
            return false;
        Archivo file = new Archivo(name, ext, tamKB, "contenido_simulado");
        current.addChild(file);
        return true;
    }

    public boolean cd(String name) {
        if ("..".equals(name)) {
            if (current.getParent() != null) {
                back.push(current);
                forward.clear();
                current = current.getParent();
                return true;
            } else
                return false;
        }
        NodoFS found = current.findChild(name);
        if (found != null && found instanceof Carpeta) {
            back.push(current);
            forward.clear();
            current = (Carpeta) found;
            return true;
        }
        return false;
    }

    public void goToRoot() {
        current = root;
    }

    public String ls(boolean orderBySize) {
        if (orderBySize) {
            current.sortChildBySize();
        }
        return current.listChildren();
    }

    public String pwd() {
        return pwd(current);
    }

    public boolean back() {
        if (back.isEmpty())
            return false;
        forward.push(current);
        current = back.pop();
        return true;
    }

    public boolean forward() {
        if (forward.isEmpty())
            return false;
        back.push(current);
        current = forward.pop();
        return true;
    }

    private String pwd(NodoFS nodo) {
        if (nodo == root)
            return "/raiz";

        StackGeneric<String> stack = new StackGeneric<>();
        NodoFS temp = nodo;

        while (temp != null) {
            if (temp instanceof Archivo archivo) {
                stack.push(archivo.getNombre() + "." + archivo.getExtension());
            } else {
                stack.push(temp.getNombre());
            }
            temp = temp.getParent();
        }

        StringBuilder path = new StringBuilder();
        while (!stack.isEmpty()) {
            path.append("/").append(stack.pop());
        }

        return path.toString();
    }

    public String searchDFS(String nombre) {
        StackGeneric<Carpeta> stack = new StackGeneric<>();
        stack.push(root);

        int visitedNodes = 0;

        while (!stack.isEmpty()) {
            Carpeta current = stack.pop();

            for (int i = 0; i < current.getHijos().size(); i++) {
                NodoFS child = current.getHijos().get(i);
                visitedNodes++;

                if (child instanceof Archivo archivo) {
                    String completeName = archivo.getNombre() + "." + archivo.getExtension();

                    if (completeName.equals(nombre)) {
                        return pwd(child)
                                + "\n[DFS] Nodos visitados: "
                                + visitedNodes;
                    }
                } else if (child.getNombre().equals(nombre)) {
                    return pwd(child)
                            + "\n[DFS] Nodos visitados: "
                            + visitedNodes;
                }

                if (child instanceof Carpeta carpeta) {
                    stack.push(carpeta);
                }
            }
        }

        return "search: " + nombre + " not found"
                + "\n[DFS] Nodos visitados: "
                + visitedNodes;
    }

    public String searchBFS(String nombre) {
        QueueGeneric<Carpeta> queue = new QueueGeneric<>();
        queue.enqueue(root);

        int visitedNodes = 0;

        while (!queue.isEmpty()) {
            Carpeta current = queue.dequeue();

            for (int i = 0; i < current.getHijos().size(); i++) {
                NodoFS child = current.getHijos().get(i);
                visitedNodes++;

                if (child instanceof Archivo archivo) {
                    String completeName = archivo.getNombre() + "." + archivo.getExtension();

                    if (completeName.equals(nombre)) {
                        return pwd(child)
                                + "\n[BFS] Nodos visitados: "
                                + visitedNodes;
                    }
                } else if (child.getNombre().equals(nombre)) {
                    return pwd(child)
                            + "\n[BFS] Nodos visitados: "
                            + visitedNodes;
                }

                if (child instanceof Carpeta carpeta) {
                    queue.enqueue(carpeta);
                }
            }
        }

        return "search: " + nombre + " not found"
                + "\n[BFS] Nodos visitados: "
                + visitedNodes;
    }

    public void queueAdd(String operacion, String ruta) {
        taskQueue.enqueue(operacion + " " + ruta);
    }

    public String queueRun() {
        if (taskQueue.isEmpty())
            return "queue: no tasks to process";

        StringBuilder results = new StringBuilder();
        while (!taskQueue.isEmpty()) {
            String task = taskQueue.dequeue();
            results.append("Processing: ").append(task).append("... done\n");
        }
        return results.toString();
    }
}
