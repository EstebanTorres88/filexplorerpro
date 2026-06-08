
/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class FileSystem {
    private final FolderNode root;
    private FolderNode current;
    private final FolderStack backStack;
    private final FolderStack forwardStack;
    private final OperationQueue operationQueue;

    public FileSystem() {
        this.root = new FolderNode("root", "2026-06-07 00:00");
        this.current = root;
        this.backStack = new FolderStack();
        this.forwardStack = new FolderStack();
        this.operationQueue = new OperationQueue();
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
}