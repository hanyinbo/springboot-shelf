package com.aison.service.impl;

import com.aison.entity.WxPosition;
import com.aison.mapper.WxPositionMapper;
import com.aison.service.WxPositionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WxPositionServiceImpl extends ServiceImpl<WxPositionMapper, WxPosition>  implements WxPositionService {

    @Override
    public IPage<WxPosition> getPageOfPosition(Page page, WxPosition wxPosition) {
        return baseMapper.getPositionOfPage(page,wxPosition);
    }
}
