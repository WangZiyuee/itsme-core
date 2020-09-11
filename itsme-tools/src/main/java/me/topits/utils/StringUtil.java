package me.topits.utils;

/**
 * @author Wang Ziyue
 * @since 2020/5/21 14:58
 */
public class StringUtil {

    private static final String FOLDER_SEPARATOR = "/";

    /**
     * 获取文件路径中文件名
     * <p>
     * in:
     * String path1 = "https://topits.me/file";
     * String path2 = "/root/www/index.html";
     * String path3 = "D:/work-core/itsme-core/mybatis-generator";
     * out:
     * ==> file
     * ==> index.html
     * ==> mybatis-generator
     * </p>
     *
     * @param path 文件路径
     * @return 文件名
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }

        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }

}
