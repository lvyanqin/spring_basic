package lynn.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数校验
 */
public class ValidateUtil {

    //匹配的正则可以不用^开始 $结束

    /**
     * 日期: yyyy-MM-dd
     */
    private static final String V_DATE = "((19|20)\\d{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])";
    /**
     * 时间: HH:mm:ss
     */
    private static final String V_TIME = "([0-1][0-9]|2[0-4]):([0-5][0-9]|60):([0-5][0-9]|60)";

    private static final String V_PASSWORD = "^([a-zA-z0-9]{8,16})$";

    private static final String V_USERNAME = "^([a-zA-z0-9]{5,16})$";

    private static final String V_MOBILE = "^[1][3,4,5,7,8][0-9]{9}$";

    private static final String V_PHONE_WITH_AREA = "^[0][1-9]{2,3}-[0-9]{5,10}$";

    private static final String V_PHONE_NOT_WITH_AREA = "^[1-9]{1}[0-9]{5,8}$";

    private static final String V_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    private static final String V_URL=  "^(([hH][tT]{2}[pP]|[hH][tT]{2}[pP][sS])://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+([\\w./?%&=\\u4e00-\\u9fa5]+)$";
    //    private static final String V_IDCARD = "([1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])|" +
//            "([1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3})";
    private static final String V_IDCARD = "([1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])";

    /**
     * 验证是不是日期yyyy-MM-dd
     * 如果是符合格式的字符串,返回true,否则为false
     *
     * @param str 要验证的字符串
     * @return
     */
    public static boolean isDate(String str) {
        return match(V_DATE, str);
    }

    /**
     * 验证是不是日期yyyy-MM-dd HH:mm:ss
     * 如果是符合格式的字符串,返回true,否则为false
     *
     * @param str 要验证的字符串
     * @return
     */
    public static boolean isDateTime(String str) {
        String regex = V_DATE + " " + V_TIME;
        return match(regex, str);
    }

    /**
     * 匹配含key的方法，格式是/../key*../.. 或 ../key*../..
     * 如 user/get..; /user/get..; user/get../..; /user/get../.. 的方法
     * 现在这个正则比较简单，可以匹配多节和多个/
     *
     * @param key
     * @param str
     * @return
     */
    public static boolean matchMethod(String key, String str) {
        String regex = "[/\\w]*/*" + key + "[/\\w]*";
        return match(regex, str);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean match(String regex, String str) {
        return Pattern.matches(regex, str);
    }


    /**
     * 校验用户密码格式
     *
     * @param password
     * @return
     */
    public static boolean checkAccountPassword(String password) {
        return Pattern.matches(V_PASSWORD, password);
    }

    /**
     * 手机号验证
     */
    public static boolean isMobile(final String str) {
        Pattern compile = Pattern.compile(V_MOBILE);
        Matcher matcher = compile.matcher(str);
        boolean matches = matcher.matches();
        return matches;
    }

    public static boolean isUrl(final String str) {
        Pattern compile = Pattern.compile(V_URL);
        Matcher matcher = compile.matcher(str);
        boolean matches = matcher.matches();
        return matches;
    }

    /**
     * 电话号码校验
     *
     * @param str
     * @return
     */
    public static boolean isPhone(final String str) {
        boolean matches = false;
        // 验证带区号的
        Pattern compile = Pattern.compile(V_PHONE_WITH_AREA);
        // 验证没有区号的
        Pattern compile1 = Pattern.compile(V_PHONE_NOT_WITH_AREA);
        if (str.length() > 9) {
            Matcher matcher = compile.matcher(str);
            matches = matcher.matches();
        } else {
            Matcher matcher = compile1.matcher(str);
            matches = matcher.matches();
        }
        return matches;
    }

    /**
     * 邮箱校验
     *
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        Pattern p;
        Matcher m;
        p = Pattern.compile(V_EMAIL);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    public static String parseIdCard(String str) {
        Pattern p = Pattern.compile(V_IDCARD);
        Matcher m = p.matcher(str);
        if (m.find())
            return m.group();
        else {
            return null;
        }
    }

    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        
                        return false;
                    }

                } catch (Exception e) {
                   
                    return false;
                }
            }

        }
        return matches;
    }


    public static String parseSexByCardNo(String cardNo) {
        if (StrUtil.isBlank(cardNo) || (cardNo.length() != 15 && cardNo.length() != 18)) {
            return "男";
        } else if (cardNo.length() == 15) {
            if (Long.parseLong(cardNo.substring(14, 15)) % 2 == 1) {
                return "男";
            } else {
                return "女";
            }
        } else {
            if (Long.parseLong(cardNo.substring(16, 17)) % 2 == 1) {
                return "男";
            } else {
                return "女";
            }
        }
    }

    public static boolean checkAccountName(String username) {
        return Pattern.matches(V_USERNAME, username);
    }


}
