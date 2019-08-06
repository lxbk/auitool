package xyz.aiyouv.aiutool.database.config;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL Server查询语句
 *
 * @author Aiyouv
 * @version 1.0
 **/
public class SqlServerQuery extends AbstractDbQuery {


    @Override
    public DbType dbType() {
        return DbType.SQL_SERVER;
    }


    @Override
    public String tablesSql() {
        return "select cast(so.name as varchar(500)) as NAME, " +
                "cast(sep.value as varchar(500)) as COMMENT from sysobjects so " +
                "left JOIN sys.extended_properties sep on sep.major_id=so.id and sep.minor_id=0 " +
                "where (xtype='U' or xtype='v')";
    }

    @Override
    public String tableFieldsSql() {
        return "SELECT  cast(a.NAME AS VARCHAR(500)) AS TABLE_NAME,cast(b.NAME AS VARCHAR(500)) AS FIELD, "
                + "cast(c.VALUE AS VARCHAR(500)) AS COMMENT,cast(sys.types.NAME AS VARCHAR (500)) AS TYPE,"
                + "(" + " SELECT CASE count(1) WHEN 1 then 'PRI' ELSE '' END"
                + " FROM syscolumns,sysobjects,sysindexes,sysindexkeys,systypes "
                + " WHERE syscolumns.xusertype = systypes.xusertype AND syscolumns.id = object_id (A.NAME) AND sysobjects.xtype = 'PK'"
                + " AND sysobjects.parent_obj = syscolumns.id " + " AND sysindexes.id = syscolumns.id "
                + " AND sysobjects.NAME = sysindexes.NAME AND sysindexkeys.id = syscolumns.id "
                + " AND sysindexkeys.indid = sysindexes.indid "
                + " AND syscolumns.colid = sysindexkeys.colid AND syscolumns.NAME = B.NAME) as 'KEY',"
                + "  b.is_identity isIdentity "
                + " FROM ( select name,object_id from sys.tables UNION all select name,object_id from sys.views ) a "
                + " INNER JOIN sys.COLUMNS b ON b.object_id = a.object_id "
                + " LEFT JOIN sys.types ON b.user_type_id = sys.types.user_type_id   "
                + " LEFT JOIN sys.extended_properties c ON c.major_id = b.object_id AND c.minor_id = b.column_id "
                + " WHERE a.NAME = '%s' and sys.types.NAME !='sysname' ";
    }

    @Override
    public String tableName() {
        return "NAME";
    }


    @Override
    public String tableComment() {
        return "COMMENT";
    }


    @Override
    public String fieldName() {
        return "FIELD";
    }


    @Override
    public String fieldType() {
        return "TYPE";
    }


    @Override
    public String fieldComment() {
        return "COMMENT";
    }


    @Override
    public String fieldKey() {
        return "KEY";
    }


    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return 1 == results.getInt("isIdentity");
    }
}

