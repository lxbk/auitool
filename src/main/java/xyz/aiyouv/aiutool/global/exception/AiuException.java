package xyz.aiyouv.aiutool.global.exception;

/**
 * 自定义异常
 *
 * @author Aiyouv
 * @version 1.0
 **/
public class AiuException extends RuntimeException {

    public AiuException() {
    }

    public AiuException(String message) {
        super(message);
    }
}
