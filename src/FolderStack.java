
/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class FolderStack {
    private FolderStackNode top;
    private int size;

    public FolderStack() {
        this.top = null;
        this.size = 0;
    }

    public void push(FolderNode folder) {
        if (folder == null) {
            return;
        }
        FolderStackNode node = new FolderStackNode(folder);
        node.setNext(top);
        top = node;
        size += 1;
    }

    public FolderNode pop() {
        if (isEmpty()) {
            return null;
        }
        FolderStackNode node = top;
        top = node.getNext();
        size -= 1;
        return node.getFolder();
    }

    public FolderNode peek() {
        return top == null ? null : top.getFolder();
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        top = null;
        size = 0;
    }
}
