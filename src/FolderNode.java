

/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class FolderNode extends FileSystemNode {
    public ChildNode firstChild;
    private int childCount;

    public FolderNode(String name, String createdAt) {
        super(name, createdAt);
        this.firstChild = null;
        this.childCount = 0;
    }

    @Override
    public boolean isFolder() {
        return true;
    }

    public void addChild(FileSystemNode node) {
        if (node == null) {
            return;
        }
        if (findChild(node.getName()) != null) {
            return;
        }
        ChildNode newNode = new ChildNode(node);
        if (firstChild == null) {
            firstChild = newNode;
        } else {
            ChildNode current = firstChild;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        node.setParent(this);
        childCount += 1;
    }

    public void removeChild(String name) {
        if (firstChild == null) {
            return;
        }
        ChildNode previous = null;
        ChildNode current = firstChild;
        while (current != null) {
            if (current.child.getName().equals(name)) {
                if (previous == null) {
                    firstChild = current.next;
                } else {
                    previous.next = current.next;
                }
                current.child.setParent(null);
                childCount -= 1;
                return;
            }
            previous = current;
            current = current.next;
        }
    }

    public FileSystemNode findChild(String name) {
        ChildNode current = firstChild;
        while (current != null) {
            if (current.child.getName().equals(name)) {
                return current.child;
            }
            current = current.next;
        }
        return null;
    }

    public boolean hasChild(String name) {
        return findChild(name) != null;
    }

    public String listChildren() {
        StringBuilder builder = new StringBuilder();
        ChildNode current = firstChild;
        while (current != null) {
            builder.append(current.child.getName());
            if (current.child.isFolder()) {
                builder.append("/");
            }
            if (current.next != null) {
                builder.append("\n");
            }
            current = current.next;
        }
        return builder.toString();
    }

    public int getChildCount() {
        return childCount;
    }
}