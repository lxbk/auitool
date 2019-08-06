## 简介

Aiutool是基于freemarker和Spring Jdbc实现的根据数据表生成Java类的工具。

暂时只支持和MySql和Sql Server，后续会再增加其他数据库支持。

## 使用

#### 1、配置数据库

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/aiyou?serverTimezone=UTC&amp
spring.datasource.username=root
spring.datasource.password=passwd
```

#### 2、配置Java类包和生成文件位置

```properties
# Java类包信息
aiu.database.entity-package=xyz.aiyouv.aiutool.entity
# Java文件生成位置
aiu.database.out-file-path=./src/main/java
```

#### 3、配置数据库字段类型和Java变量类型的映射关系

不同的数据对应不同的properties文件，配置信息左侧为数据库字段类型，右侧为Java变量类型。

非Java基本数据类型，需要包含包名和对象名的完成信息。

```properties
int=int
bigint=int
date=java.util.Date
datetime=java.util.Date
nvarchar=String
varchar=String
varchar2=String
char=char
time=java.util.Date
numric=double
tinyint=boolean
```

#### 4、注入生成类

```java
@Configuration
public class AiuConfig {
    private final JdbcTemplate jdbcTemplate;

    public AiuConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean()
    public AiuGenerator aiuGenerator (){
        return new AiuGenerator(jdbcTemplate);
    }
}

```

#### 5、调用生成方法

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class ToolApplicationTests {

    private final static Logger LOGGER = LoggerFactory.getLogger(ToolApplicationTests.class);

    @Autowired
    private AiuGenerator aiuGenerator;
    @Test
    public void contextLoads() throws IOException, TemplateException {
        // sys_user为数据库表名称
        aiuGenerator.generatorEntity("sys_user");
    }

}

```

