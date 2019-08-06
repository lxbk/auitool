package xyz.aiyouv.aiutool.database.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * //TODO
 *
 * @author Aiyouv
 * @version 1.0
 **/

@Getter
@Setter
public class EntityInfo {
    // 实体所在的包名
    private String javaPackage;
    // 实体类名
    private String className;

    private String tableName;
    // 父类名
    private String superclass;
    // 父类泛型
    private String superGeneric;
    // 属性集合
    List<FieldInfo> properties;
    // 是否有构造函数
    private boolean constructors;

    // 是否存在非基础数据类型
    private Set<String> unBaseTypes;

}
