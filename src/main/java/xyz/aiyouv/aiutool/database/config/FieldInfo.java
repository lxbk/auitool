package xyz.aiyouv.aiutool.database.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 字段属性
 *
 * @author Aiyouv
 * @version 1.0
 **/

@Setter
@Getter
public class FieldInfo {
    private String javaType;
    // 属性名称
    private String propertyName;
    // 是否是主键
    private boolean identity;
    // 备注
    private String description;

}
