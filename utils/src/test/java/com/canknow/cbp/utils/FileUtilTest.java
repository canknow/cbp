package com.canknow.cbp.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

    @Test
    void convertFileSize() {
        String result = FileUtil.convertFileSize(1024 * 1024);
        Assertions.assertEquals("1.0 MB", result);
    }
}