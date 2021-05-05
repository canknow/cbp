package com.canknow.cbp.common.domain.file;

import com.canknow.cbp.base.domain.repositories.IRepository;

public interface IFileInfoRepository extends IRepository<FileInfo> {
    void deleteByFileName(String fileName);
}
