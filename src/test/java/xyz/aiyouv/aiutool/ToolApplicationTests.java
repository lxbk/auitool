package xyz.aiyouv.aiutool;

import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.aiyouv.aiutool.database.AiuGenerator;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToolApplicationTests {

    private final static Logger LOGGER = LoggerFactory.getLogger(ToolApplicationTests.class);

    @Autowired
    private AiuGenerator aiuGenerator;
    @Test
    public void contextLoads() throws IOException, TemplateException {
    }

}
