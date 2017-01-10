package org.mhq0123.springleaf.common.utils;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.Enumeration;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName ZipUtils
 * @date 2016-07-06
 * @memo zip格式解压缩工具
 */
public class ZipUtils {
    /**
     * 功能：压缩文件或文件夹s
     *
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void compress(File srcFile, File destFile) throws IOException {
        ZipArchiveOutputStream out = null;
        try {
            out = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), 1024));
            compressOneFile(srcFile, out, "");
        } finally {
            out.close();
        }
    }

    /**
     * 功能：压缩单个文件,非文件夹。私有，不对外开放
     *
     * @param srcFile
     * @param out
     * @param dir
     * @throws IOException
     */
    private static void compressOneFile(File srcFile, ZipArchiveOutputStream out, String dir) throws IOException {
        if (srcFile.isDirectory()) {
            // 对文件夹进行处理。
            ZipArchiveEntry entry = new ZipArchiveEntry(dir + srcFile.getName() + "/");
            out.putArchiveEntry(entry);
            out.closeArchiveEntry();
            // 循环文件夹中的所有文件进行压缩处理。
            String[] subFiles = srcFile.list();
            for (String subFile : subFiles) {
                compressOneFile(new File(srcFile.getPath() + "/" + subFile), out, (dir + srcFile.getName() + "/"));
            }
        } else {
            // 普通文件。
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(srcFile));
                // 创建一个压缩包。
                ZipArchiveEntry entry = new ZipArchiveEntry(srcFile, dir + srcFile.getName());
                out.putArchiveEntry(entry);
                IOUtils.copy(is, out);
                out.closeArchiveEntry();
            } finally {
                if (is != null)
                    is.close();
            }
        }
    }

    /**
     * 功能：解压缩zip压缩包下的所有文件
     *
     * @param zipFile
     * @param dir
     * @throws IOException
     */
    public static void decompressZip(File zipFile, String dir) throws IOException {
        ZipFile zf = new ZipFile(zipFile);
        try {
            for (Enumeration<ZipArchiveEntry> entries = zf.getEntries(); entries.hasMoreElements();) {
                ZipArchiveEntry ze = entries.nextElement();
                // 不存在则创建目标文件夹。
                File targetFile = new File(dir, ze.getName());
                // 遇到根目录时跳过。
                if (ze.getName().lastIndexOf("/") == (ze.getName().length() - 1)) {
                    continue;
                }
                // 如果文件夹不存在，创建文件夹。
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }

                InputStream i = zf.getInputStream(ze);
                OutputStream o = null;
                try {
                    o = new FileOutputStream(targetFile);
                    IOUtils.copy(i, o);
                } finally {
                    if (i != null) {
                        i.close();
                    }
                    if (o != null) {
                        o.close();
                    }
                }
            }
        } finally {
            zf.close();
        }
    }

    /**
     * 功能：解压缩zip压缩包下的某个文件信息。
     *
     * @date 2014年06月25日
     * @param zipFile
     *            zip压缩文件
     * @param fileName
     *            某个文件名,例如abc.zip下面的a.jpg，需要传入/abc/a.jpg。
     * @param dir
     *            解压缩到这个路径下
     * @throws IOException
     *             文件流异常
     */
    public static void decompressZip(File zipFile, String fileName, String dir) throws IOException {
        // 不存在则创建目标文件夹。
        File targetFile = new File(dir, fileName);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }

        ZipFile zf = new ZipFile(zipFile);
        Enumeration<ZipArchiveEntry> zips = zf.getEntries();
        ZipArchiveEntry zip = null;
        while (zips.hasMoreElements()) {
            zip = zips.nextElement();
            if (fileName.equals(zip.getName())) {
                OutputStream o = null;
                InputStream i = zf.getInputStream(zip);
                try {
                    o = new FileOutputStream(targetFile);
                    IOUtils.copy(i, o);
                } finally {
                    if (i != null) {
                        i.close();
                    }
                    if (o != null) {
                        o.close();
                    }
                }
            }
        }
    }

    /**
     * 功能：得到zip压缩包下的某个文件信息,只能在根目录下查找。
     *
     * @date 2014年06月25日
     * @param zipFile
     *            zip压缩文件
     * @param fileName
     *            某个文件名,例如abc.zip下面的a.jpg，需要传入/abc/a.jpg。
     * @return ZipArchiveEntry 压缩文件中的这个文件,没有找到返回null。
     * @throws IOException
     *             文件流异常
     */
    public static ZipArchiveEntry readZip(File zipFile, String fileName) throws IOException {
        ZipFile zf = new ZipFile(zipFile);
        Enumeration<ZipArchiveEntry> zips = zf.getEntries();
        ZipArchiveEntry zip = null;
        while (zips.hasMoreElements()) {
            zip = zips.nextElement();
            if (fileName.equals(zip.getName())) {
                return zip;
            }
        }
        return null;
    }

    /**
     * 功能：得到zip压缩包下的所有文件信息。
     *
     * @date 2014年06月25日
     * @param zipFile
     *            zip压缩文件
     * @return Enumeration<ZipArchiveEntry> 压缩文件中的文件枚举。
     * @throws IOException
     *             文件流异常
     */
    public static Enumeration<ZipArchiveEntry> readZip(File zipFile) throws IOException {
        ZipFile zf = new ZipFile(zipFile);
        Enumeration<ZipArchiveEntry> zips = zf.getEntries();
        return zips;
    }
}
