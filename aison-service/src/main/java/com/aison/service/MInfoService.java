package com.aison.service;

import com.aison.dto.MInfoDto;
import com.aison.dto.MInfoUnionQuery;
import com.aison.entity.MInfo;

import java.util.List;


public interface MInfoService {


    public MInfo test();

    public List<MInfo> testquery();


    public Integer testDelete();


    public Integer testPut(Long id);

    public List<MInfo> tesXML();

    public List<MInfoUnionQuery> findUnionQuery();

    public List<MInfoDto> queryUnionAll();

}
