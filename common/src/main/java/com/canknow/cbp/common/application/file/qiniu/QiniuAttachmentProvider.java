package com.canknow.cbp.common.application.file.qiniu;

import com.canknow.cbp.common.domain.file.AbstractAttachmentProvider;
import com.canknow.cbp.common.domain.file.FileInfo;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

@Component(value = "qiniuAttachmentProvider")
@ConditionalOnProperty(prefix = "file", name = "attachment-provider", havingValue = "qiniu", matchIfMissing = true)
public class QiniuAttachmentProvider extends AbstractAttachmentProvider {
    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private Auth auth;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private QiniuConfig qiniuConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileInfo uploadFile(byte[] bytes, String originalName) throws IOException {
        FileInfo fileInfo = new FileInfo(bytes, originalName);
        fileInfo.setProviderName("qiniu");
        fileInfo.setUrl("http://" + qiniuConfig.getDomain() + "/" + fileInfo.getFileName());

        String upToken = auth.uploadToken(qiniuConfig.getBucket(), fileInfo.getFileName());
        Response response = uploadManager.put(bytes, fileInfo.getFileName(), upToken);

        fileInfoIRepository.insert(fileInfo);
        return fileInfo;
    }

    @Override
    public void deleteFile(FileInfo fileInfo) {
        fileInfoIRepository.delete(fileInfo.getId());

        try {
            bucketManager.delete(qiniuConfig.getBucket(), fileInfo.getFileName());
        }
        catch (QiniuException e) {
            throw new RuntimeException(e);
        }
    }
}
