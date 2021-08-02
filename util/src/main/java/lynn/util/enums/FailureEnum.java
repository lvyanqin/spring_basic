package lynn.util.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常编码枚举
 */
public enum FailureEnum {
    /**
     * 4xx
     */
    PARAMETER_FAILURE(40001, "参数错误"),
    TOKEN_WITHOUT_FAILURE(40002, "缺少Token"),
    TOKEN_EXPIRED_FAILURE(40003, "Token过期"),
    TOKEN_PARSE_FAILURE(40004, "token解析异常"),

    NO_PERMISSION_FAILURE(40014, "当前操作没有权限"),
    INVALID_ACCOUNT(40015, "账号已被禁用"),
    LOGIN_OTHER_PLACE(40016, "账号已在其它地方登录"),
    INVALID_LOGIN_NAME(40017, "该账号不存在，请联系管理员分配账号"),

    FAIL_LOGIN(40020, "登录失败"),
    OPERATION_FAILURE(40023, "操作失败, 请重试"),



    VOLUNTEER_REFUSE_AGAIN_APPLY(41112, "请勿重复提交申请"),
    FALLBACK_ERROR(50002, "网络异常"),

    EXCEPTION_FAILURE(50001, "服务器异常");


    private int code;
    private String msg;

    FailureEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 通过code 获得msg
     *
     * @param code
     * @return
     */
    public static String getMsgByCode(int code) {
        for (FailureEnum exceptionCodeEnum : FailureEnum.values()) {
            if (exceptionCodeEnum.getCode() == code) {
                return exceptionCodeEnum.getMsg();
            }
        }
        throw new IllegalArgumentException("No element matches " + code);
    }

    /**
     * 获得枚举的列表, 格式是{1, "异常1"}
     *
     * @return
     */
    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (FailureEnum exceptionCodeEnum : FailureEnum.values()) {
            map.put(exceptionCodeEnum.getCode(), exceptionCodeEnum.getMsg());
        }
        return map;
    }

    /**
     * 获得枚举的列表, 格式是[{"code": 1, "msg": "异常1"}]
     *
     * @return
     */
    public static List<Map<String, Object>> toList() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (FailureEnum exceptionCodeEnum : FailureEnum.values()) {
            map = new HashMap<String, Object>();
            map.put("code", exceptionCodeEnum.getCode());
            map.put("msg", exceptionCodeEnum.getMsg());
        }
        return list;
    }

}
