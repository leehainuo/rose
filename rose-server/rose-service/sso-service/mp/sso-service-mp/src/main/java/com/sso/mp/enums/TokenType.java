package com.sso.mp.enums;

public enum TokenType {
    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token");

    private final String redisKey;

    /**
     * 枚举构造函数
     * @param redisKey 该类型令牌在Redis中存储的键名
     */
    TokenType(String redisKey) {
        this.redisKey = redisKey;
    }

    /**
     * 获取Redis存储键名
     * @return 对应Redis的hash结构key
     */
    public String getRedisKey() {
        return redisKey;
    }

}
