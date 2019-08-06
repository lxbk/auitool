package xyz.aiyouv.aiutool.database.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据库表
 * Model
 * @author Aiyouv
 * @version 1.0
 **/

@Getter
@Setter
public class Table implements Serializable {
    private String name;
    private String comment;
}
