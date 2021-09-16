package com.aison.service.impl;

import com.aison.dto.MInfoDto;
import com.aison.dto.MInfoUnionQuery;
import com.aison.entity.MInfo;
import com.aison.mapper.MInfoMapper;
import com.aison.service.MInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class MInfoServiceImpl  extends ServiceImpl<MInfoMapper,MInfo> implements MInfoService {

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

    public List<MInfo> tesXML() {
        return mInfoMapper.findAllUser();
    }

    public List<MInfoUnionQuery> findUnionQuery() {
        return mInfoMapper.findUnionQuery();
    }

    public List<MInfoDto> queryUnionAll() {
        return mInfoMapper.findUnionAll();
    }
}
