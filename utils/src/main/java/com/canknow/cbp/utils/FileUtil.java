package com.canknow.cbp.utils;

import lombok.experimental.UtilityClass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class FileUtil {
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    public static boolean deleteFile(String filePath) {
        boolean flag = false;

        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    public void saveTempFile () {
    }

    public byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 8];
        int index = inputStream.read(buffer);
        while (-1 != index) {
            output.write(buffer, 0, index);
            index = inputStream.read(buffer);
        }
        return output.toByteArray();
    }

    public File[] getFilesOfDirectory(String directoryText) {
        File directory = new File(StringUtil.utf8Decoding(directoryText));
        File[] result = null;
        if (directory.isDirectory()) {
            result = directory.listFiles();
        }

        return result;
    }

    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        }
        else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        }
        else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        }
        else {
            return String.format("%d B", size);
        }
    }
}
