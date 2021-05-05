package com.canknow.cbp.webCommon.application.file.local;

import com.canknow.cbp.common.domain.file.AbstractAttachmentProvider;
import com.canknow.cbp.common.domain.file.FileInfo;
import com.canknow.cbp.web.adapter.inbound.web.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.canknow.cbp.web.adapter.inbound.web.config.ApplicationConfig;

@Component(value = "localLocalAttachmentProvider")
@ConditionalOnProperty(prefix = "file", name = "attachment-provider", havingValue = "local", matchIfMissing = true)
public class LocalAttachmentProvider extends AbstractAttachmentProvider {
    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public FileInfo uploadFile(byte[] bytes, String originalName) throws Exception {
        FileInfo fileInfo = new FileInfo(bytes, originalName);
        fileInfo.setProviderName("local");
        fileInfo.setUrl(applicationConfig.getResourceAccessUrl() + fileInfo.getFileName());

        String filePath = UploadUtil.upload(applicationConfig.getUploadPath(), bytes, fileInfo.getFileName());
        fileInfo.setFilePath(filePath);
        fileInfoIRepository.insert(fileInfo);
        return fileInfo;
    }

    @Override
    public void deleteFile(FileInfo fileInfo) {

    }
}
