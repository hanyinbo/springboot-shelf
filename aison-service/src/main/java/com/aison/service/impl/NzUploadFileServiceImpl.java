package com.aison.service.impl;

import com.aison.entity.NzUploadFile;
import com.aison.mapper.NzUploadFileMapper;
import com.aison.service.NzUploadFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NzUploadFileServiceImpl extends ServiceImpl<NzUploadFileMapper, NzUploadFile> implements NzUploadFileService {
    @Override
    public List<NzUploadFile> getCompanyImgList(Long companyId) {
        return baseMapper.getCompanyImgList(companyId);
    }
}
