package com.canknow.cbp.web.adapter.inbound.web.utils;

import com.canknow.cbp.base.exceptions.InvalidParameterException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@UtilityClass
@Slf4j
public class UploadUtil {
    public void checkEmpty(MultipartFile multipartFile) {
        if (StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new InvalidParameterException("上传文件不存在！");
        }
    }

    public String upload(String uploadPath, byte[] bytes, String fileName) throws Exception {
        File saveDirectory = new File(uploadPath);

        if (!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }

        File saveFile = new File(saveDirectory, fileName);
        FileUtils.writeByteArrayToFile(saveFile, bytes);
        return saveFile.getParent() + "\\" + fileName;
    }
}
