package cn.plugin.core.ui.attachment.bean;

import java.io.Serializable;

/**
 * Created by yy on 2018/12/29.
 **/
public class AttachmentEntity implements Serializable {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_ADD = 1;

    private String FilePath;
    private String FileExtension;
    private int Type = TYPE_NORMAL;

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public void setFileExtension(String fileExtension) {
        FileExtension = fileExtension;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
