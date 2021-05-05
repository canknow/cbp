package com.canknow.cbp.webCommon.application.file;

import lombok.Data;

@Data
public class FileUploadResultDto {
    private String fileType;

    private String providerName;

    private String fileName;

    private String fileExtension;

    private String originalName;

    private Integer fileSize;

    private String url;
}
