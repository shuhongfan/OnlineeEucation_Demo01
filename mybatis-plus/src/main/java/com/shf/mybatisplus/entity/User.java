package com.shf.mybatisplus.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}

/**
 * AUTO(0), 数据库ID自增
 * NONE(1),该类型为未设置主键类型.
 * INPUT(2),用户输入ID,该类型可以通过自己注册自动填充插件进行填充
 * ID_WORKER(3),全局唯一ID (idWorker)
 *  UUID(4), 全局唯一ID (UUID)
 *  ID_WORKER_STR(5);字符串全局唯一ID (idWorker 的字符串表示)
 */
