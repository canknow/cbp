package com.canknow.cbp.common.adapter.outbound.persistent.file;

import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import com.canknow.cbp.common.domain.file.FileInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileInfoJpaRepository extends IJpaRepositoryBase<FileInfo> {
    void deleteByFileName(String fileName);
}
