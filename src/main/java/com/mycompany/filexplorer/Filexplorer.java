package com.mycompany.filexplorer;

import com.mycompany.filexplorer.dominio.SistemaArchivos;
import com.mycompany.filexplorer.estructuras.LinkedListGeneric;
import com.mycompany.filexplorer.estructuras.QueueGeneric;
import com.mycompany.filexplorer.estructuras.StackGeneric;
import com.mycompany.filexplorer.utils.Command;
import com.mycompany.filexplorer.utils.Reader;
import com.mycompany.filexplorer.utils.Writer;

/**
 *
 * @author jwd
 * @author Esteban Torres Jiménez
 */
public class Filexplorer {

    public static void initFileExplorer() {
        // --- Primer avance: pruebas de estructuras
        // System.out.println("-- Probando estructuras lineales --");
        // LinkedListGeneric<String> list = new LinkedListGeneric<>();
        // list.add("uno"); list.add("dos"); list.add("tres");
        // System.out.println("Lista: " + list.toString() + " tamaño=" + list.size());
        //
        // StackGeneric<Integer> stack = new StackGeneric<>();
        // stack.push(1); stack.push(2); stack.push(3);
        // System.out.println("Pila antes: " + stack.toString());
        // System.out.println("pop: " + stack.pop());
        // System.out.println("Pila después: " + stack.toString());
        //
        // QueueGeneric<String> queue = new QueueGeneric<>();
        // queue.enqueue("a"); queue.enqueue("b"); queue.enqueue("c");
        // System.out.println("Cola: " + queue.toString());
        // System.out.println("dequeue: " + queue.dequeue());
        // System.out.println("Cola después: " + queue.toString());

        // System.out.println("\n-- Probando árbol N-ario (Carpeta/Archivo) --");
        // SistemaArchivos fs = new SistemaArchivos();
        // fs.mkdir("docs"); fs.mkdir("imagenes"); fs.touch("readme", "txt", 5);
        // System.out.println("Contenido root:\n" + fs.ls());
        // fs.cd("docs"); fs.touch("doc1","pdf", 120); fs.touch("doc2","pdf", 80);
        // fs.cd(".."); System.out.println("Contenido root after adding in docs:\n" +
        // fs.ls());
        // --- Segundo Avance: Implementación de menú interactivo
        Writer writer = new Writer();
        Reader reader = new Reader();
        SistemaArchivos fs = new SistemaArchivos();
        boolean isRunning = true;

        while (isRunning) {
            String prompt = "[user@arch " + fs.getCurrent().toString() + "]$ ";
            writer.write(prompt);

            Command cmd = new Command(reader.read(s -> s));

            switch (cmd.getName()) {
                case "ls":
                    String[] lsArgs = cmd.getArgs();
                    boolean orderBySize = lsArgs.length > 0 && lsArgs[0].equals("-s");
                    writer.writeln(fs.ls(orderBySize));
                    break;
                case "mkdir":
                    String mkdirArg = cmd.arg(0);

                    if (cmd.arg(0) == null) {
                        writer.writeln("mkdir: missing operand");
                        break;
                    }

                    if (!fs.mkdir(mkdirArg)) {
                        writer.writeln("mkdir: directory already exists");
                    }
                    break;

                case "cd":
                    String cdArg = cmd.arg(0);

                    if (cdArg == null) {
                        fs.goToRoot();

                    } else if (!fs.cd(cdArg)) {
                        writer.writeln("cd: no such directory: " + cdArg);
                    }
                    break;

                case "touch":
                    String[] touchArgs = cmd.getArgs();

                    if (touchArgs.length != 2) {
                        writer.writeln("Usage: touch <name>.<extension> <size>");
                        break;
                    }

                    try {
                        String[] fileParts = touchArgs[0].split("\\.");

                        if (fileParts.length != 2) {
                            writer.writeln("touch: invalid filename");
                            break;
                        }

                        String name = fileParts[0];
                        String extension = fileParts[1];
                        int size = Integer.parseInt(touchArgs[1]);

                        if (!fs.touch(name, extension, size)) {
                            writer.writeln("touch: file already exists: " + name + "." + extension);
                        }

                    } catch (NumberFormatException e) {
                        writer.writeln("touch: size must be a number");
                    }

                    break;

                case "rm":
                    String[] rmArgs = cmd.getArgs();

                    boolean recursive = false;
                    String target;

                    if (rmArgs.length == 1) {
                        target = rmArgs[0];
                    } else if (rmArgs.length == 2 && "-r".equals(rmArgs[0])) {
                        recursive = true;
                        target = rmArgs[1];
                    } else {
                        writer.writeln("Usage: rm [-r] <file|folder>");
                        break;
                    }

                    if (!fs.rm(target, recursive)) {
                        writer.writeln("rm: failed to remove '" + target + "'");
                    }

                    break;

                case "rmdir":
                    String[] rmdirArgs = cmd.getArgs();

                    if (rmdirArgs.length != 1) {
                        writer.writeln("Usage: rmdir <directory>");
                        break;
                    }

                    if (!fs.rmdir(rmdirArgs[0])) {
                        writer.writeln("rmdir: failed to remove '" + rmdirArgs[0] + "'");
                    }

                    break;

                case "search":
                    String[] searchArgs = cmd.getArgs();
                    if (searchArgs.length == 0) {
                        writer.writeln("Usage: search [-dfs]|[-bfs] [nombre]");
                        break;
                    } else if (searchArgs[0].equals("-dfs")) {
                        writer.writeln(fs.searchDFS(searchArgs[1]));

                    } else if (searchArgs[0].equals("-bfs")) {
                        writer.writeln(fs.searchBFS(searchArgs[1]));

                    } else {
                        writer.writeln("search: invalid option " + searchArgs[0]);
                    }

                    break;

                case "queue":

                    String[] queueArgs = cmd.getArgs();
                    if (queueArgs.length == 0) {
                        writer.writeln("Usage: queue -add [operation] [path] | queue -run");
                        break;
                    }
                    if ("-add".equals(queueArgs[0])) {
                        if (queueArgs.length != 3) {
                            writer.writeln("Usage: queue -add [operation] [path]");
                            break;
                        }
                        fs.queueAdd(queueArgs[1], queueArgs[2]);
                        writer.writeln("Task added: " + queueArgs[1] + " " + queueArgs[2]);
                    } else if ("-run".equals(queueArgs[0])) {
                        writer.writeln(fs.queueRun());
                    } else {
                        writer.writeln("queue: invalid option " + queueArgs[0]);
                    }

                    break;

                case "pwd":
                    writer.writeln(fs.pwd());
                    break;

                case "back":
                    if (!fs.back()) {
                        writer.writeln("back: already at the beginning of history");
                    }
                    break;

                case "forward":
                    if (!fs.forward()) {
                        writer.writeln("forward: already at the end of history");
                    }
                    break;

                case "exit":
                    isRunning = false;
                    break;

                default:
                    writer.writeln("Command not found: " + cmd.getName());
                    break;
            }
        }
    }
}
