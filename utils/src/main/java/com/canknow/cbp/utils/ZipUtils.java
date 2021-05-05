package com.canknow.cbp.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    public static final String EXT = ".zip";
    private static final String BASE_DIR = "";

    /**
     * 符号"/"用来作为目录标识判断符
     */
    private static final String PATH = "/";
    private static final int BUFFER = 1024;

    public static void compress(File srcFile) throws Exception {
        String name = srcFile.getName();
        String basePath = srcFile.getParent();
        String destPath = basePath + name + EXT;
        compress(srcFile, destPath);
    }

    public static void compress(File sourceFile, File destFile) throws Exception {
        // 对输出文件做CRC32校验
        CheckedOutputStream checkedOutputStream = new CheckedOutputStream(new FileOutputStream(destFile), new CRC32());

        ZipOutputStream zipOutputStream = new ZipOutputStream(checkedOutputStream);

        compress(sourceFile, zipOutputStream, BASE_DIR);

        zipOutputStream.flush();
        zipOutputStream.close();
    }

    public static void compress(File srcFile, String destPath) throws Exception {
        compress(srcFile, new File(destPath));
    }

    private static void compress(File sourceFile, ZipOutputStream zipOutputStream, String basePath) throws Exception {
        if (sourceFile.isDirectory()) {
            compressDirectory(sourceFile, zipOutputStream, basePath);
        }
        else {
            compressFile(sourceFile, zipOutputStream, basePath);
        }
    }

    public static void compress(String sourcePath) throws Exception {
        File sourceFile = new File(sourcePath);
        compress(sourceFile);
    }

    public static void compress(String srcPath, String destPath) throws Exception {
        File srcFile = new File(srcPath);

        compress(srcFile, destPath);
    }

    private static void compressDirectory(File directory, ZipOutputStream zipOutputStream, String basePath) throws Exception {
        File[] files = directory.listFiles();

        // 构建空目录
        assert files != null;

        if (files.length < 1) {
            ZipEntry entry = new ZipEntry(basePath + directory.getName() + PATH);

            zipOutputStream.putNextEntry(entry);
            zipOutputStream.closeEntry();
        }

        for (File file : files) {
            compress(file, zipOutputStream, basePath + directory.getName() + PATH);
        }
    }

    private static void compressFile(File file, ZipOutputStream zos, String dir) throws Exception {

        /**
         * 压缩包内文件名定义
         *
         * <pre>
         * 如果有多级目录，那么这里就需要给出包含目录的文件名
         * 如果用WinRAR打开压缩包，中文名将显示为乱码
         * </pre>
         */
        ZipEntry entry = new ZipEntry(dir + file.getName());
        zos.putNextEntry(entry);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

        int count;
        byte[] data = new byte[BUFFER];

        while ((count = bufferedInputStream.read(data, 0, BUFFER)) != -1) {
            zos.write(data, 0, count);
        }
        bufferedInputStream.close();
        zos.closeEntry();
    }
}
