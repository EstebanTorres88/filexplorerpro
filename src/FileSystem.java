
/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class FileSystem {
    private final FolderNode root;
    private FolderNode current;
    private final FolderStack<FolderNode> backStack;
    private final FolderStack<FolderNode> forwardStack;
    private final OperationQueue<Operation> operationQueue;

    public FileSystem() {
        this.root = new FolderNode("root", "2026-06-07 00:00");
        this.current = root;
        this.backStack = new FolderStack<>();
        this.forwardStack = new FolderStack<>();
        this.operationQueue = new OperationQueue<>();
    }

    public void mkdir(String name) {
        if (name == null || name.isEmpty()) {
            return;
        }
        if (current.hasChild(name)) {
            return;
        }
        FolderNode folder = new FolderNode(name, "2026-06-07 00:00");
        current.addChild(folder);
    }

    public void touch(String name, String extension, int sizeKb, String content) {
        if (name == null || name.isEmpty()) {
            return;
        }
        if (current.hasChild(name)) {
            return;
        }
        FileNode file = new FileNode(name, extension, sizeKb, content, "2026-06-07 00:00");
        current.addChild(file);
    }

    public void ls() {
        String list = current.listChildren();
        if (list.isEmpty()) {
            System.out.println("(vacío)");
            return;
        }
        System.out.println(list);
    }

    public void cd(String path) {
        if (path == null || path.isEmpty()) {
            return;
        }

        if (path.equals("..")) {
            if (current.getParent() != null) {
                backStack.push(current);
                current = current.getParent();
                forwardStack.clear();
            }
            return;
        }

        if (path.equals("/")) {
            if (current != root) {
                backStack.push(current);
                forwardStack.clear();
            }
            current = root;
            return;
        }

        if (path.startsWith("/")) {
            FolderNode target = navigateFromRoot(path.substring(1));
            if (target != null) {
                moveTo(target);
            }
            return;
        }

        FileSystemNode node = current.findChild(path);
        if (node != null && node.isFolder()) {
            moveTo((FolderNode) node);
        }
    }

    private void moveTo(FolderNode target) {
        if (target == null || target == current) {
            return;
        }

        backStack.push(current);
        forwardStack.clear();
        current = target;
    }

    private FolderNode navigateFromRoot(String route) {
        if (route == null || route.isEmpty()) {
            return root;
        }
        String[] parts = route.split("/");
        FolderNode pointer = root;
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.isEmpty()) {
                continue;
            }
            FileSystemNode child = pointer.findChild(part);
            if (child == null || !child.isFolder()) {
                return null;
            }
            pointer = (FolderNode) child;
        }
        return pointer;
    }

    public String pwd() {
        if (current == root) {
            return "/";
        }
        return current.getPath();
    }

    public void goBack() {
        if (backStack.isEmpty()) {
            return;
        }
        FolderNode destination = backStack.pop();
        if (destination != null) {
            forwardStack.push(current);
            current = destination;
        }
    }

    public void goForward() {
        if (forwardStack.isEmpty()) {
            return;
        }
        FolderNode destination = forwardStack.pop();
        if (destination != null) {
            backStack.push(current);
            current = destination;
        }
    }

    public void enqueueOperation(Operation operation) {
        operationQueue.enqueue(operation);
    }

    public void executeNextOperation() {
        Operation operation = operationQueue.dequeue();
        if (operation != null) {
            operation.execute(this);
        }
    }

    /**
     * DFS (Depth-First Search) - Búsqueda en profundidad desde la raíz
     * Retorna cantidad de nodos visitados
     */
    public int searchDFS(String targetName) {
        System.out.println("   [DFS] Iniciando búsqueda: " + targetName);
        int[] visitedCount = {0};
        boolean found = dfsHelper(root, targetName, visitedCount);
        System.out.println("   [DFS] Nodos visitados: " + visitedCount[0] + " | Encontrado: " + found);
        return visitedCount[0];
    }

    private boolean dfsHelper(FolderNode node, String target, int[] visited) {
        visited[0]++;
        if (node.getName().equals(target)) {
            System.out.println("   → Encontrado: " + node.getName());
            return true;
        }

        ChildNode current = node.firstChild;
        while (current != null) {
            if (current.child.isFolder()) {
                if (dfsHelper((FolderNode) current.child, target, visited)) {
                    return true;
                }
            } else {
                visited[0]++;
                if (current.child.getName().equals(target)) {
                    System.out.println("   → Encontrado: " + current.child.getName());
                    return true;
                }
            }
            current = current.next;
        }
        return false;
    }

    /**
     * BFS (Breadth-First Search) - Búsqueda por niveles desde la raíz
     * Retorna cantidad de nodos visitados
     */
    public int searchBFS(String targetName) {
        System.out.println("   [BFS] Iniciando búsqueda: " + targetName);
        int visitedCount = 0;
        SimpleQueue<String> queue = new SimpleQueue<>();
        
        if (root.getName().equals(targetName)) {
            System.out.println("   → Encontrado: " + root.getName());
            return 1;
        }
        
        queue.enqueue(root.getName());
        bfsAddChildren(root, queue, targetName);
        
        while (!queue.isEmpty()) {
            String nodeName = queue.dequeue();
            visitedCount++;
            if (nodeName.equals(targetName)) {
                System.out.println("   → Encontrado: " + nodeName);
                System.out.println("   [BFS] Nodos visitados: " + visitedCount);
                return visitedCount;
            }
        }
        
        visitedCount++;
        System.out.println("   [BFS] Nodos visitados: " + visitedCount + " | Encontrado: false");
        return visitedCount;
    }

    private void bfsAddChildren(FolderNode node, SimpleQueue<String> queue, String target) {
        ChildNode current = node.firstChild;
        while (current != null) {
            queue.enqueue(current.child.getName());
            if (current.child.isFolder()) {
                bfsAddChildren((FolderNode) current.child, queue, target);
            }
            current = current.next;
        }
    }

    /**
     * MergeSort - Ordena los nombres de las carpetas alfabéticamente
     * Retorna array ordenado de nombres
     */
    public String[] sortChildrenMergeSort() {
        String[] children = getChildrenNames();
        if (children.length <= 1) {
            return children;
        }
        mergeSortHelper(children, 0, children.length - 1);
        return children;
    }

    private void mergeSortHelper(String[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(arr, left, mid);
            mergeSortHelper(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(String[] arr, int left, int mid, int right) {
        String[] temp = new String[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i].compareTo(arr[j]) <= 0) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    /**
     * QuickSort - Ordena los nombres de las carpetas alfabéticamente
     * Retorna array ordenado de nombres
     */
    public String[] sortChildrenQuickSort() {
        String[] children = getChildrenNames();
        if (children.length <= 1) {
            return children;
        }
        quickSortHelper(children, 0, children.length - 1);
        return children;
    }

    private void quickSortHelper(String[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortHelper(arr, low, pi - 1);
            quickSortHelper(arr, pi + 1, high);
        }
    }

    private int partition(String[] arr, int low, int high) {
        String pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) < 0) {
                i++;
                String temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        String temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    /**
     * Obtiene los nombres de todos los hijos del directorio actual
     */
    private String[] getChildrenNames() {
        String[] children = new String[current.getChildCount()];
        ChildNode curr = current.firstChild;
        int index = 0;
        while (curr != null) {
            children[index++] = curr.child.getName();
            curr = curr.next;
        }
        return children;
    }

    /**
     * Cuenta la cantidad total de nodos en el árbol
     */
    public int countTotalNodes() {
        return countNodesHelper(root);
    }

    private int countNodesHelper(FolderNode node) {
        int count = 1;
        ChildNode current = node.firstChild;
        while (current != null) {
            if (current.child.isFolder()) {
                count += countNodesHelper((FolderNode) current.child);
            } else {
                count++;
            }
            current = current.next;
        }
        return count;
    }
}