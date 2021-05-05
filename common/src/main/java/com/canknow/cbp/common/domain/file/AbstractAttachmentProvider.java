package com.canknow.cbp.common.domain.file;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.InputStream;

public abstract class AbstractAttachmentProvider implements IAttachmentProvider {
    @Autowired
    protected IFileInfoRepository fileInfoIRepository;

    @Override
    public FileInfo uploadFile(InputStream inputStream, String originalName) throws Exception {
        byte[] bytes = com.canknow.cbp.utils.FileUtil.toByteArray(inputStream);
        return uploadFile(bytes, originalName);
    }
}
