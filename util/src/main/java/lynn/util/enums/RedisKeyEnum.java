package lynn.util.enums;

public enum RedisKeyEnum {

    //token缓存
	TEST("WX_CACHE::TEST:%s", "测试");



    private String prefix;
    private String desc;

    RedisKeyEnum(String prefix, String desc) {
        this.prefix = prefix;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrefix() {
        return prefix;
    }
}
