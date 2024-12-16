package homework.soft.activity.constant.enums;

/**
 * 上传模块
 *
 */
public enum UploadModule {
    COMMENT,
    USER_AVATAR, IMAGE, FILE;

    @Override
    public String toString() {
        return super.name().toLowerCase();
    }
}
