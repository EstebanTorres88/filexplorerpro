

/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class ChildNode {
    public FileSystemNode child;
    public ChildNode next;

    public ChildNode(FileSystemNode child) {
        this.child = child;
        this.next = null;
    }
}
