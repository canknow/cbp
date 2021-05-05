package com.canknow.cbp.common.adapter.outbound.persistent.file;

import com.canknow.cbp.jpa.repositories.FullAuditedJpaRepositoryBase;
import com.canknow.cbp.common.domain.file.FileInfo;
import com.canknow.cbp.common.domain.file.IFileInfoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class FileInfoRepository extends FullAuditedJpaRepositoryBase<FileInfo, IFileInfoJpaRepository> implements IFileInfoRepository {

    public FileInfoRepository(IFileInfoJpaRepository jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public void deleteByFileName(String fileName) {
        this.jpaRepository.deleteByFileName(fileName);
    }
}
