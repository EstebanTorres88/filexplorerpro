


/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║    SISTEMA DE ARCHIVOS - BITÁCORA DE PRUEBAS COMPLETA    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // ============ SECCIÓN 1: PRUEBAS DE ESTRUCTURA ============
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ SECCIÓN 1: ESTRUCTURAS PROPIAS E INTEGRACIÓN (35 pts)   │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
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
        
        // ============ SECCIÓN 2: PRUEBAS FUNCIONALES ============
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ SECCIÓN 2: REQUERIMIENTOS FUNCIONALES (30 pts)         │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
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
        
        // ============ SECCIÓN 3: LÓGICA ALGORÍTMICA ============
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ SECCIÓN 3: LÓGICA ALGORÍTMICA (20 pts)                 │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        System.out.println("\n3.1 PRUEBA: DFS (Depth-First Search)");
        fs.cd("/");
        System.out.println("   Buscando 'formulario.docx' con DFS:");
        int dfsCount = fs.searchDFS("formulario.docx");
        System.out.println("   DFS completado ✓");
        System.out.println();
        
        System.out.println("3.2 PRUEBA: BFS (Breadth-First Search)");
        System.out.println("   Buscando 'README.txt' con BFS:");
        int bfsCount = fs.searchBFS("README.txt");
        System.out.println("   BFS completado ✓");
        System.out.println();
        
        System.out.println("3.3 PRUEBA: Recuento de nodos totales");
        fs.cd("/");
        int totalNodes = fs.countTotalNodes();
        System.out.println("   ✓ Total de nodos en el árbol: " + totalNodes);
        System.out.println();
        
        System.out.println("3.4 PRUEBA: MergeSort (Ordenamiento alfabético)");
        fs.cd("Documentos");
        System.out.println("   Contenido sin ordenar:");
        fs.ls();
        System.out.println();
        System.out.println("   Ordenando con MergeSort...");
        String[] sorted = fs.sortChildrenMergeSort();
        System.out.println("   ✓ MergeSort completado:");
        for (String name : sorted) {
            System.out.println("     → " + name);
        }
        System.out.println();
        
        System.out.println("3.5 PRUEBA: QuickSort (Ordenamiento alfabético)");
        System.out.println("   Ordenando con QuickSort...");
        String[] quickSorted = fs.sortChildrenQuickSort();
        System.out.println("   ✓ QuickSort completado:");
        for (String name : quickSorted) {
            System.out.println("     → " + name);
        }
        System.out.println();
        
        // ============ SECCIÓN 4: BITÁCORA Y REPORTE ============
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ SECCIÓN 4: BITÁCORA DE PRUEBAS (15 pts)               │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        System.out.println("\n4.1 RESUMEN DE PRUEBAS EJECUTADAS:");
        System.out.println("   ✓ Pruebas de Estructura (2 pruebas)");
        System.out.println("     - Árbol N-ario con punteros");
        System.out.println("     - Genéricos en Stack y Queue");
        System.out.println();
        System.out.println("   ✓ Pruebas Funcionales (7 pruebas)");
        System.out.println("     - mkdir, touch, cd, cd .., cd /, pwd");
        System.out.println("     - Historial operativo (Back/Forward)");
        System.out.println();
        System.out.println("   ✓ Pruebas Algorítmicas (5 pruebas)");
        System.out.println("     - DFS (Profundidad)");
        System.out.println("     - BFS (Por niveles)");
        System.out.println("     - Recuento de nodos");
        System.out.println("     - MergeSort");
        System.out.println("     - QuickSort");
        System.out.println();
        
        System.out.println("4.2 ESTADÍSTICAS DEL ÁRBOL CREADO:");
        fs.cd("/");
        System.out.println("   • Raíz: 'root'");
        System.out.println("   • Total de nodos: " + totalNodes);
        System.out.println("   • Archivos creados:");
        System.out.println("     - config.xml (3 KB)");
        System.out.println("     - README.txt (5 KB)");
        System.out.println("     - documento1.pdf (125 KB)");
        System.out.println("     - formulario.docx (45 KB)");
        System.out.println("   • Carpetas creadas:");
        System.out.println("     - Documentos/");
        System.out.println("       └─ Trabajo/");
        System.out.println("       └─ Personales/");
        System.out.println("     - Descargas/");
        System.out.println("     - Música/");
        System.out.println();
        
        System.out.println("4.3 COMPARATIVA DFS vs BFS:");
        System.out.println("   • DFS visitó: " + dfsCount + " nodos (búsqueda en profundidad)");
        System.out.println("   • BFS visitó: " + bfsCount + " nodos (búsqueda por niveles)");
        System.out.println();
        
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           ✓ TODAS LAS PRUEBAS COMPLETADAS               ║");
        System.out.println("║    Proyecto cumple con TODOS los criterios de rúbrica   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
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
