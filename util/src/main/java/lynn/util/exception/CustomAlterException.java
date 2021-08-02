package lynn.util.exception;

import lynn.util.enums.FailureEnum;
import lynn.util.result.Result;

public class CustomAlterException extends RuntimeException {
    private int code;
    private String msg;

    public CustomAlterException() {
        super();
    }

    public CustomAlterException(String message) {
        super(message);
        this.msg = message;
    }

    public CustomAlterException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public CustomAlterException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CustomAlterException(FailureEnum fail) {
        super(fail.getMsg());
        this.code = fail.getCode();
        this.msg = fail.getMsg();
    }

    public CustomAlterException(Result result) {
        super(result.getMessage());
        this.code = result.getCode();
        this.msg = result.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
