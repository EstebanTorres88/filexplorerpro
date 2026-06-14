/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.filexplorer;

import com.mycompany.filexplorer.dominio.SistemaArchivos;
import com.mycompany.filexplorer.estructuras.LinkedListGeneric;
import com.mycompany.filexplorer.estructuras.QueueGeneric;
import com.mycompany.filexplorer.estructuras.StackGeneric;
import utils.Command;
import utils.Reader;
import utils.Writer;


/**
 *
 * @author jwd
 */
public class Filexplorer {

    public static void main(String[] args) {
        // --- Primer avance: pruebas de estructuras
        //        System.out.println("-- Probando estructuras lineales --");
        //        LinkedListGeneric<String> list = new LinkedListGeneric<>();
        //        list.add("uno"); list.add("dos"); list.add("tres");
        //        System.out.println("Lista: " + list.toString() + " tamaño=" + list.size());
        //
        //        StackGeneric<Integer> stack = new StackGeneric<>();
        //        stack.push(1); stack.push(2); stack.push(3);
        //        System.out.println("Pila antes: " + stack.toString());
        //        System.out.println("pop: " + stack.pop());
        //        System.out.println("Pila después: " + stack.toString());
        //
        //        QueueGeneric<String> queue = new QueueGeneric<>();
        //        queue.enqueue("a"); queue.enqueue("b"); queue.enqueue("c");
        //        System.out.println("Cola: " + queue.toString());
        //        System.out.println("dequeue: " + queue.dequeue());
        //        System.out.println("Cola después: " + queue.toString());

        //        System.out.println("\n-- Probando árbol N-ario (Carpeta/Archivo) --");
        //        SistemaArchivos fs = new SistemaArchivos();
        //        fs.mkdir("docs"); fs.mkdir("imagenes"); fs.touch("readme", "txt", 5);
        //        System.out.println("Contenido root:\n" + fs.ls());
        //        fs.cd("docs"); fs.touch("doc1","pdf", 120); fs.touch("doc2","pdf", 80);
        //        fs.cd(".."); System.out.println("Contenido root after adding in docs:\n" + fs.ls());
        
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
                    writer.write(fs.ls());
                    break;
                case "mkdir":
                    String mkdirArg = cmd.arg(0);

                    if (cmd.arg(0) == null) {
                        writer.writeln("mkdir: missing operand");
                        break;
                    }

                    fs.mkdir(mkdirArg);
                    break;
                    
                case "cd":
                    String cdArg = cmd.arg(0);
                    
                    if (cdArg == null) {
                        fs.cd("raiz");
                    }
                    
                    if (cdArg == "..") {
                        fs.cd(fs.getCurrent().getParent().toString());
                    }
                    
                    fs.cd(cdArg);
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

                        fs.touch(name, extension, size);

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
    
                case "exit":
                    isRunning = false;
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }
}
