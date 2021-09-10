package com.aison.service;

import com.aison.entity.MInfo;
import com.aison.mapper.MInfoMapper;
import com.aison.utils.UniqId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

@Service
public class MInfoService {
    @Autowired
    private MInfoMapper mInfoMapper;
    @Autowired
    private UniqId id;

    public void test(){
        MInfo mInfo = new MInfo();
        mInfo.setMOid(id.getUniqID());
        mInfo.setMName("mybatisplus");
        mInfo.setMType("orm");
        mInfo.setVersion(1);
        mInfo.setRemark("备注");
        mInfoMapper.insert(mInfo);
    }
}
