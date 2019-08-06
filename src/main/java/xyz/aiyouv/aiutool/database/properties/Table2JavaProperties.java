package xyz.aiyouv.aiutool.database.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据库转Java配置信息
 *
 * @author Aiyouv
 */

@Component
@ConfigurationProperties(prefix = "aiu.database")

public class Table2JavaProperties {

    /**
     * 数据库驱动
     **/
    private String dirive = "MySql";
    private String entityPackage = "xyz.aiyouv.aiu.entity";
    private String servicePackage = "xyz.aiyouv.aiu.service";
    private String controllerPackage = "xyz.aiyouv.aiu.controller";
    private String repositoryPackage = "xyz.aiyouv.aiu.repository";
    private String outFilePath = "./";

    public String getDirive() {
        return dirive;
    }

    public void setDirive(String dirive) {
        this.dirive = dirive;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getRepositoryPackage() {
        return repositoryPackage;
    }

    public void setRepositoryPackage(String repositoryPackage) {
        this.repositoryPackage = repositoryPackage;
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }
}