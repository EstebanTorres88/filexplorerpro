


/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class Operation {
    private String type;
    private FileSystemNode target;
    private String extraData;

    public Operation(String type, FileSystemNode target, String extraData) {
        this.type = type;
        this.target = target;
        this.extraData = extraData;
    }

    public String getType() {
        return type;
    }

    public FileSystemNode getTarget() {
        return target;
    }

    public String getExtraData() {
        return extraData;
    }

    public void execute(FileSystem fs) {
        if (fs == null) {
            return;
        }

        String operationType = type == null ? "" : type.trim().toLowerCase();
        if (operationType.equals("mkdir")) {
            fs.mkdir(extraData);
            return;
        }

        if (operationType.equals("touch")) {
            String[] parts = extraData == null ? new String[0]
                    : extraData.contains("|") ? extraData.split("\\|", 4) : extraData.split("\\s+", 4);
            if (parts.length >= 4) {
                int sizeKb = 0;
                try {
                    sizeKb = Integer.parseInt(parts[2].trim());
                } catch (NumberFormatException ignored) {
                    sizeKb = 0;
                }
                fs.touch(parts[0].trim(), parts[1].trim(), sizeKb, parts[3]);
            }
        }
    }
}