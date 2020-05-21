package me.topits.utils;

/**
 * @author Wang Ziyue
 * @since 2020/5/21 14:58
 */
public class StringUtil {

    private static final String FOLDER_SEPARATOR = "/";

    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }

        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }


}
