package com.aison.service.impl;

import com.aison.entity.NzUploadFile;
import com.aison.mapper.NzUploadFileMapper;
import com.aison.service.NzUploadFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NzUploadFileServiceImpl extends ServiceImpl<NzUploadFileMapper, NzUploadFile> implements NzUploadFileService {
    @Override
    public List<NzUploadFile> getCompanyImgList(Long companyId) {
        List<NzUploadFile> companyImgList = baseMapper.getCompanyImgList(companyId);
        for(NzUploadFile file:companyImgList){
            if(file==null){
                return null;
            }
        }
        return baseMapper.getCompanyImgList(companyId);
    }
}
