package xyz.aiyouv.aiutool.database.config;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据查询语句
 *
 * @author Aiyouv
 * @version 1.0
 **/
public abstract class AbstractDbQuery implements IDbQuery {


    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return false;
    }


    @Override
    public String[] fieldCustom() {
        return null;
    }
}

