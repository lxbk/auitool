package xyz.aiyouv.aiutool.database.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据库字段
 * Model
 * @author Aiyouv
 * @version 1.0
 **/

@Getter
@Setter
public class Field implements Serializable {
    private String field;
    private String comment;
    private String type;
    private boolean key;
}
