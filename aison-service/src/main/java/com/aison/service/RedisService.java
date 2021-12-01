package com.aison.service;

import javax.websocket.Session;

/**
 * redis服务
 *
 * @author Zhenfeng Li
 * @version 1.0
 * @date 2019-09-21 11:21
 */
public interface RedisService {
    /**
     * 保存 键-值到Redis
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value);

    /**
     * 保存 键-值到Redis
     *
     * @param key     键
     * @param value   值
     * @param timeout 失效时间(秒)
     */
    public void setExpire(String key, Object value, long timeout);

    /**
     * 延长key失效时间
     *
     * @param key     键
     * @param timeout 失效时间(秒)
     */
    public void setExpire(String key, long timeout);

    /**
     * 删除
     *
     * @param key 键
     */
    public void remove(String key);

}
