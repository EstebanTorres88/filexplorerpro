
/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class FolderStack<T extends FolderNode> {
    private FolderStackNode top;
    private int size;

    public FolderStack() {
        this.top = null;
        this.size = 0;
    }

    public void push(T folder) {
        if (folder == null) {
            return;
        }
        FolderStackNode node = new FolderStackNode(folder);
        node.setNext(top);
        top = node;
        size += 1;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        FolderStackNode node = top;
        top = node.getNext();
        size -= 1;
        return (T) node.getFolder();
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        return top == null ? null : (T) top.getFolder();
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
