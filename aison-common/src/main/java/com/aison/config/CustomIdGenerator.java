package com.aison.config;

import com.aison.utils.SnowflakeIdWorkerUtils;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomIdGenerator implements IdentifierGenerator {

    @Autowired
    private SnowflakeIdWorkerUtils snowflakeIdWorkerUtils;
    @Override
    public   Long nextId(Object entity) {
        return snowflakeIdWorkerUtils.genId();
    }
}
