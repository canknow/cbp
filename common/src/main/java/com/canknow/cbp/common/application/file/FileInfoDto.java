package com.canknow.cbp.common.application.file;

import com.canknow.cbp.base.application.dto.FullAuditedDto;
import com.canknow.cbp.base.autoMapper.AutoMap;
import lombok.Data;

@Data
public class FileInfoDto extends FullAuditedDto {
    private String fileType;

    private String providerName;

    private String fileName;

    private String fileExtension;

    private String originalName;

    private Integer fileSize;

    private String url;

    private String filePath;

    @AutoMap(type = UserDto.class)
    private UserDto creatorUser;
}
