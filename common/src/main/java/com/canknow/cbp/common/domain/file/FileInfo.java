package com.canknow.cbp.common.domain.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.canknow.cbp.base.domain.entities.audit.FullAuditedAggregateRoot;
import com.canknow.cbp.utils.DateUtils;
import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
public class FileInfo extends FullAuditedAggregateRoot {
    private static final long serialVersionUID = 5703932458583505409L;

    public final static String NORM_MONTH_PATTERN = "yyyy/MM/dd/";

    public FileInfo() {

    }

    public FileInfo(byte[] bytes, String originalName) {
        this.fileExtension = FileUtil.extName(originalName);
        this.originalName = originalName;
        this.fileName = DateUtils.format(LocalDateTime.now(), NORM_MONTH_PATTERN) + IdUtil.simpleUUID() + "." + this.fileExtension;
        this.fileSize = bytes.length;
    }

    private String fileType;

    private String providerName;

    private String fileName;

    private String fileExtension;

    private String originalName;

    private Integer fileSize;

    private String url;

    private String filePath;
}
