package com.aison.service;

import com.aison.entity.NzUploadFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface NzUploadFileService extends IService<NzUploadFile> {

    List<NzUploadFile> getCompanyImgList(Long companyId);
}
