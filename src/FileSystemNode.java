

/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public abstract class FileSystemNode {
    protected String name;
    protected String createdAt;
    protected FolderNode parent;

    public FileSystemNode(String name, String createdAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.parent = null;
    }

    public String getName() {
        return name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public FolderNode getParent() {
        return parent;
    }

    public void setParent(FolderNode parent) {
        this.parent = parent;
    }

    public abstract boolean isFolder();

    public String getPath() {
        if (parent == null) {
            return "/" + name;
        }
        if (parent.getParent() == null) {
            return "/" + parent.getName() + "/" + name;
        }
        return parent.getPath() + "/" + name;
    }
}
