

/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class FolderStackNode {
    private FolderNode folder;
    private FolderStackNode next;

    public FolderStackNode(FolderNode folder) {
        this.folder = folder;
        this.next = null;
    }

    public FolderNode getFolder() {
        return folder;
    }

    public FolderStackNode getNext() {
        return next;
    }

    public void setNext(FolderStackNode next) {
        this.next = next;
    }
}
