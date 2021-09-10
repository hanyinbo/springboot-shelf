package com.aison.service;

import com.aison.entity.MInfo;
import com.aison.mapper.MInfoMapper;
import com.aison.utils.SnowflakeIdWorkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MInfoService {
    @Autowired
    private MInfoMapper mInfoMapper;

    @Autowired
    private SnowflakeIdWorkerUtils snowflakeIdWorkerUtils;

    public void test(){
        MInfo mInfo = new MInfo();
//        mInfo.setMOid(id.getUniqID());
        mInfo.setMOid(SnowflakeIdWorkerUtils.genId());
        mInfo.setMName("mybatisplus");
        mInfo.setMType("orm");
        mInfo.setVersion(1);
        mInfo.setRemark("rewt");
        mInfoMapper.insert(mInfo);
    }

    public List<MInfo> testquery(){
        return mInfoMapper.selectList(null);
    }

    public Integer testDelete(){
        return mInfoMapper.delete(null);
    }

    public Integer testPut(Long id){
        MInfo mInfo = new MInfo();
        mInfo.setMOid(id);
        mInfo.setRemark("更新成功");
        return mInfoMapper.updateById(mInfo);
    }
}
