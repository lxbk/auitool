package xyz.aiyouv.aiutool.database;

import cn.hutool.setting.dialect.Props;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import xyz.aiyouv.aiutool.database.config.*;
import xyz.aiyouv.aiutool.database.model.Field;
import xyz.aiyouv.aiutool.global.exception.AiuException;
import xyz.aiyouv.aiutool.database.properties.Table2JavaProperties;
import xyz.aiyouv.aiutool.utils.StringUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Java文件生成服务
 * Service
 * @author Aiyouv
 * @version 1.0
 **/

public class AiuGenerator {

    private final static Logger LOGGER = LoggerFactory.getLogger(AiuGenerator.class);

    @Autowired
    private Configuration cfg;
    @Autowired
    private Table2JavaProperties table2JavaProperties;
    @Nullable
    private JdbcTemplate jdbcTemplate;
    private IDbQuery dbQuery;
    private DbType dbType;
    private String entityPackage;
    // private String servicePackage;
    // private String controllerPackage;
    // private String repositoryPackage;

    // 生成文件所在目录
    private String outFilePath;
    // 数据库数据类型和Java数据类型映射关系
    private Props mapperProps;

    public AiuGenerator(@Nullable JdbcTemplate jdbcTemplate) {
        setJdbcTemplate(jdbcTemplate);
    }

    private void setJdbcTemplate(@Nullable JdbcTemplate jdbcTemplate) {
        try {
            assert jdbcTemplate != null;
            DatabaseMetaData md = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().getMetaData();
            String databaseType = md.getDatabaseProductName();
            dbQuery = getDbQuery(databaseType.toLowerCase());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 生成entity
     * @author Aiyouv
     * @param name 数据库表名称
     **/
    public void generatorEntity(String name) throws IOException, TemplateException {
        String path = this.outFilePath;
        File javaFile = null;
        Template template = cfg.getTemplate("entity.ftl");
        EntityInfo entityInfo = createEntityDataModel(name);
        // 创建.java类文件
        File outDirFile = new File(path);
        if(!outDirFile.exists()){
            outDirFile.mkdir();
        }

        javaFile = creatJavaFilename(outDirFile, entityInfo.getJavaPackage(), entityInfo.getClassName());
        HashMap<String,Object> root = new HashMap<>();
        root.put("entity", entityInfo);
        Writer javaWriter = new FileWriter(javaFile);
        template.process(root, javaWriter);
        javaWriter.flush();
        LOGGER.info("文件生成路径：" + javaFile.getCanonicalPath());

        javaWriter.close();
        // 输出到Console控制台
        Writer out = new OutputStreamWriter(System.out);
        template.process(root, out);
        out.flush();
        out.close();
    }

    /**
     * 创建数据模型
     * @return
     */
    private EntityInfo createEntityDataModel(String name) {
        String packageName = this.entityPackage;
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setJavaPackage(packageName); // 创建包名
        entityInfo.setClassName(StringUtils.toCamelCaseInitialsUpper(name));  // 创建类名
        entityInfo.setTableName(name);
        entityInfo.setConstructors(true); // 是否创建构造函数

        List<FieldInfo> fieldInfos = new ArrayList<FieldInfo>();
        Set<String> unBaseTypes = new HashSet<>();
        AtomicReference<String> javaType = new AtomicReference<String>();

        List<Field> results = jdbcTemplate.query(String.format(dbQuery.tableFieldsSql(),name), new RowMapper<Field>() {
            @Override
            public Field mapRow(ResultSet resultSet, int i) throws SQLException {
                Field field = new Field();
                field.setField(resultSet.getString(dbQuery.fieldName()));
                field.setComment(resultSet.getString(dbQuery.fieldComment()));
                field.setType(resultSet.getString(dbQuery.fieldType()));
                field.setKey(dbQuery.isKeyIdentity(resultSet));
                return field;

            }
        });
        results.forEach(c -> {
            String key = c.getType();
            if(dbType.equals(DbType.MYSQL)){
                key = StringUtils.substringFromFirst(key,"(");
            }
            String prop = mapperProps.getStr(key);
            if(prop == null){
                LOGGER.error("数据类型{}未配置映射",key);
                throw new AiuException(String.format("数据类型%s未配置映射",key));
            }
            if(prop.contains(".")){
                unBaseTypes.add(prop);
                prop = StringUtils.substringLast(prop,".");
            }
            javaType.set(prop);
            FieldInfo attribute = new FieldInfo();
            attribute.setJavaType(javaType.get());
            attribute.setIdentity(c.isKey());
            attribute.setDescription(c.getComment());
            attribute.setPropertyName(StringUtils.toCamelCaseInitialsLower(c.getField()));
            fieldInfos.add(attribute);
        });

        entityInfo.setUnBaseTypes(unBaseTypes);
        // 将属性集合添加到实体对象中
        entityInfo.setProperties(fieldInfos);


        return entityInfo;
    }

    /**
     * 创建.java文件所在路径 和 返回.java文件File对象
     * @param outDirFile 生成文件路径
     * @param javaPackage java包名
     * @param javaClassName java类名
     * @return File
     */
    private static File creatJavaFilename(File outDirFile, String javaPackage, String javaClassName) {
        String packageSubPath = javaPackage.replace('.', '/');
        File packagePath = new File(outDirFile, packageSubPath);
        File file = new File(packagePath, javaClassName + ".java");
        if(!packagePath.exists()){
            packagePath.mkdirs();
        }
        return file;
    }

    @PostConstruct
    private void init() {

        this.entityPackage = table2JavaProperties.getEntityPackage();
        // this.servicePackage = table2JavaProperties.getServicePackage();
        // this.controllerPackage = table2JavaProperties.getControllerPackage();
        // this.repositoryPackage = table2JavaProperties.getRepositoryPackage();
        this.outFilePath = table2JavaProperties.getOutFilePath();
    }


    private IDbQuery getDbQuery(String driverName) {
        if (null == dbQuery) {
            this.dbType = getDbType(driverName);
            switch (dbType) {
                case SQL_SERVER:
                    dbQuery = new SqlServerQuery();
                    mapperProps = new Props("MS2Java.properties");
                    break;
                default:
                    // 默认 MYSQL
                    dbQuery = new MySqlQuery();
                    mapperProps = new Props("MySql2Java.properties");
                    break;
            }
        }
        return dbQuery;
    }

    /**
     * 判断数据库类型
     *
     * @return 类型枚举值
     */
    private DbType getDbType(String driverName) {
        if (null == dbType) {
            if (driverName.contains("mysql")) {
                dbType = DbType.MYSQL;
            } else if (driverName.contains("sql ser")) {
                dbType = DbType.SQL_SERVER;
            } else {
                throw new AiuException("Unknown type of database!");
            }
        }
        return dbType;
    }

}
