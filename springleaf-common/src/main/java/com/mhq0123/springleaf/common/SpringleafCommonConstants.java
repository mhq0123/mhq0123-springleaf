package com.mhq0123.springleaf.common;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName SpringleafCommonConstants
 * @date 2016-07-06
 * @memo 常量
 */
public class SpringleafCommonConstants {
    /** 操作系统*/
    public final static class OS {
        /** 换行符*/
        public static final String NEW_LINE = System.getProperty("line.separator");
        /** 文件路径分隔符 / \\*/
        public static final String FILE_SEPARATOR = System.getProperty("file.separator");
        /** 路径分隔符  ; */
        public static final String PATH_SEPARATOR = System.getProperty("path.separator");
    }
}
