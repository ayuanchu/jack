package com.kancy.mybatisplus.generator.enums;

/**
 * KeyStrategyEnum
 *
 * @author kancy
 * @date 2020/6/14 22:12
 */
public enum KeyStrategyEnum {
    /**
     * 该类型为未设置主键类型(将跟随全局)
     */
    NONE,
    /**
     * 数据库ID自增
     */
    AUTO,
    /**
     * 用户输入ID
     * <p>该类型可以通过自己注册自动填充插件进行填充</p>
     */
    INPUT,

    /* 以下3种类型、只有当插入对象ID 为空，才自动填充。 */
    /**
     * 全局唯一ID (idWorker)
     */
    ID_WORKER,
    /**
     * 全局唯一ID (UUID)
     */
    UUID,
    /**
     * 字符串全局唯一ID (idWorker 的字符串表示)
     */
    ID_WORKER_STR;
}
