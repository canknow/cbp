package com.canknow.cbp.common.domain.file;

import java.io.InputStream;

public interface IAttachmentProvider {
    FileInfo uploadFile(byte[] bytes, String originalName) throws Exception;

    FileInfo uploadFile(InputStream inputStream, String originalName) throws Exception;

    void deleteFile(FileInfo fileName);
}
