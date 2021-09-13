package com.aison.service;

import com.aison.entity.MInfo;
import com.aison.mapper.MInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MInfoService {
    @Autowired
    private MInfoMapper mInfoMapper;

    public MInfo test() {
        MInfo mInfo = new MInfo();
        mInfo.setMName("mybatisplus");
        mInfo.setMType("orm");
        mInfo.setVersion(1);
        mInfo.setRemark("rewt");
        mInfoMapper.insert(mInfo);
        return mInfo;
    }

    public List<MInfo> testquery() {
        return mInfoMapper.selectList(null);
    }

    public Integer testDelete() {
        return mInfoMapper.delete(null);
    }

    public Integer testPut(Long id) {
        MInfo mInfo = new MInfo();
        mInfo.setMOid(id);
        mInfo.setRemark("更新成功");
        return mInfoMapper.updateById(mInfo);
    }


    public List<MInfo> tesXML(){
        return mInfoMapper.findAllUser();
    }
}
