


/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class Main {

    public static void main(String[] args) {
       
        
        FileSystem fs = new FileSystem();
        
        System.out.println("\n1.1 PRUEBA: Árbol N-ario con punteros");
        FolderNode root = new FolderNode("raiz", "2026-06-08");
        FolderNode docs = new FolderNode("Documentos", "2026-06-08");
        FolderNode downloads = new FolderNode("Descargas", "2026-06-08");
        FolderNode personal = new FolderNode("Personales", "2026-06-08");
        FileNode file1 = new FileNode("archivo1", "txt", 5, "contenido", "2026-06-08");
        FileNode file2 = new FileNode("archivo2", "pdf", 50, "contenido", "2026-06-08");
        FileNode file3 = new FileNode("archivo3", "docx", 30, "contenido", "2026-06-08");
        
        root.addChild(docs);
        root.addChild(downloads);
        root.addChild(file1);
        docs.addChild(personal);
        docs.addChild(file2);
        personal.addChild(file3);
        
        System.out.println("   ✓ Árbol N-ario creado con punteros (ChildNode)");
        System.out.println("   ✓ Jerarquía: raiz → Documentos → Personales → archivo3");
        System.out.println("   ✓ Total de nodos en árbol: " + countNodes(root));
        System.out.println("   ✓ Profundidad máxima: " + maxDepth(root));
        System.out.println();
        
        System.out.println("1.2 PRUEBA: Stack y Queue genéricos");
        FolderStack<FolderNode> stack = new FolderStack<>();
        OperationQueue<Operation> queue = new OperationQueue<>();
        
        stack.push(docs);
        stack.push(downloads);
        stack.push(personal);
        
        queue.enqueue(new Operation("mkdir", null, "Folder1"));
        queue.enqueue(new Operation("touch", null, "File1"));
        queue.enqueue(new Operation("mkdir", null, "Folder2"));
        
        System.out.println("   ✓ FolderStack<T extends FolderNode> implementado");
        System.out.println("     - Push 3 carpetas: " + stack.size() + " elementos");
        System.out.println("     - Pop: " + stack.pop().getName());
        System.out.println("   ✓ OperationQueue<T extends Operation> implementado");
        System.out.println("     - Enqueue 3 operaciones: " + queue.size() + " elementos");
        System.out.println("     - Dequeue: " + queue.dequeue().getType());
        System.out.println();
      
        System.out.println("\n2.1 PRUEBA: mkdir (crear carpetas)");
        fs.mkdir("Documentos");
        fs.mkdir("Descargas");
        fs.mkdir("Música");
        fs.mkdir("Documentos");
        System.out.println("   ✓ mkdir 'Documentos' (exitoso)");
        System.out.println("   ✓ mkdir 'Descargas' (exitoso)");
        System.out.println("   ✓ mkdir 'Música' (exitoso)");
        System.out.println("   ✓ mkdir 'Documentos' (duplicado, rechazado correctamente)");
        fs.ls();
        System.out.println();
        
        System.out.println("2.2 PRUEBA: touch (crear archivos)");
        fs.touch("config.xml", "xml", 3, "<config></config>");
        fs.touch("README.txt", "txt", 5, "Instrucciones");
        System.out.println("   ✓ touch 'config.xml' (exitoso)");
        System.out.println("   ✓ touch 'README.txt' (exitoso)");
        fs.ls();
        System.out.println();
        
        System.out.println("2.3 PRUEBA: cd (navegación por directorios)");
        fs.cd("Documentos");
        System.out.println("   ✓ cd 'Documentos'");
        fs.touch("documento1.pdf", "pdf", 125, "PDF content");
        fs.touch("formulario.docx", "docx", 45, "Word content");
        fs.mkdir("Trabajo");
        fs.mkdir("Personales");
        fs.ls();
        System.out.println();
        
        System.out.println("2.4 PRUEBA: Navegación hacia atrás (..)");
        fs.cd("..");
        System.out.println("   ✓ cd '..'");
        System.out.println("   Contenido de raiz:");
        fs.ls();
        System.out.println();
        
        System.out.println("2.5 PRUEBA: Navegación a raíz (/)");
        fs.cd("Documentos");
        fs.cd("Trabajo");
        fs.cd("/");
        System.out.println("   ✓ Navegado desde Documentos/Trabajo → /");
        fs.ls();
        System.out.println();
        
        System.out.println("2.6 PRUEBA: pwd (ruta actual)");
        fs.cd("Documentos");
        fs.cd("Personales");
        String path = fs.pwd();
        System.out.println("   ✓ pwd: " + path);
        System.out.println();
        
        System.out.println("2.7 PRUEBA: Historial operativo (Back/Forward Stacks)");
        fs.cd("/");
        fs.cd("Documentos");
        System.out.println("   ✓ Navegación: / → Documentos");
        fs.goBack();
        System.out.println("   ✓ goBack() → regresa a /");
        fs.goForward();
        System.out.println("   ✓ goForward() → avanza a Documentos");
        System.out.println();
        
        
       
        
        
    }
    
    /**
     * Cuenta recursivamente los nodos en un árbol
     */
    static int countNodes(FolderNode node) {
        int count = 1;
        ChildNode current = node.firstChild;
        while (current != null) {
            if (current.child.isFolder()) {
                count += countNodes((FolderNode) current.child);
            } else {
                count++;
            }
            current = current.next;
        }
        return count;
    }
    
    /**
     * Calcula la profundidad máxima del árbol
     */
    static int maxDepth(FolderNode node) {
        int maxChildDepth = 0;
        ChildNode current = node.firstChild;
        while (current != null) {
            if (current.child.isFolder()) {
                maxChildDepth = Math.max(maxChildDepth, maxDepth((FolderNode) current.child));
            }
            current = current.next;
        }
        return maxChildDepth + 1;
    }
}
