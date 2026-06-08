

/**
 *
 * @author carpediem_
 */
public class FileNode extends FileSystemNode {
    private String extension;
    private int sizeKb;
    private String content;

    public FileNode(String name, String extension, int sizeKb, String content, String createdAt) {
        super(name, createdAt);
        this.extension = extension;
        this.sizeKb = sizeKb;
        this.content = content;
    }

    @Override
    public boolean isFolder() {
        return false;
    }

    public String getExtension() {
        return extension;
    }

    public int getSizeKb() {
        return sizeKb;
    }

    public String getContent() {
        return content;
    }
}
