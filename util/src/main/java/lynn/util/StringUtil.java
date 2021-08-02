package lynn.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 的工具类
 */
public class StringUtil {

    /**
     * 获得文件后缀,null表示无后缀,如返回doc 或 xls等
     *
     * @param str
     * @return
     */
    public static String getSuffix(String str) {
        int index = str.lastIndexOf(".");
        if (index != -1) {
            String suffix = str.substring(index + 1);
            return suffix;
        } else {
            return null;
        }
    }

    /**
     * 判断是否为null或空串（去空格了），是返回 true
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        //当str = null时为true，后面的不执行了，所以str = null时不会执行trim()，所以就没问题
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断是否不为null或非空串（去空格了），是返回 true
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {

        return !isEmpty(str);
    }

    /**
     * 转换空串，如str是空串或null 则转成num，不为空就是本身
     *
     * @param str
     * @param num
     * @return
     */
    public static String parseEmpty(String str, String num) {
        if (isEmpty(str)) {
            return num;
        }

        return str;
    }

    /**
     * 对数字补零，
     * <p>数字1，补零到3位，则返回001</p>
     * <p>数字-1，补零到3位，则返回-01</p>
     *
     * @param num
     * @param length
     * @return
     */
    public static String fillLeftZero(int num, int length) {
        return String.format("%0" + length + "d", num);
    }

    /**
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     *
     * <p>No delimiter is added before or after the list.
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtil.join(null, *)               = null
     * StringUtil.join([], *)                 = ""
     * StringUtil.join([null], *)             = ""
     * StringUtil.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtil.join(["a", "b", "c"], null) = "abc"
     * StringUtil.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(final Object[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    /**
     * <p>
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     * </p>
     *
     * <pre>
     * StringUtil.join(null, *)            = null
     * StringUtil.join([], *)              = ""
     * StringUtil.join([null], *)          = ""
     * StringUtil.join([1, 2, 3], ';')  	= "1;2;3"
     * </pre>
     *
     * @param array      数组
     * @param separator  分隔符
     * @param startIndex the first index to start joining from
     * @param endIndex   the index to stop joining from
     * @return the joined String, {@code null} if null array input
     */
    public static String join(final Object[] array, final char separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return "";
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            Object obj = array[i];
            if (obj != null) {
                buf.append(obj);
            }

        }
        return buf.toString();
    }

    /**
     * ids转换成(1,2,3)
     *
     * @param ids
     * @param splitStr
     * @return
     */
    public static String convertIdsToStr(String ids, String splitStr) {
        StringBuffer sb = new StringBuffer();
        if (ids != null && !"".equals(ids)) {
            String[] idsArr = ids.split(splitStr);
            if (idsArr != null && idsArr.length > 0) {
                sb.append("(");
                for (int i = 0; i < idsArr.length; i++) {
                    if (i == idsArr.length - 1) {
                        sb.append(idsArr[i]);
                    } else {
                        sb.append(idsArr[i] + ",");
                    }
                }
                sb.append(")");
            }
        }
        return sb.toString();
    }

    /**
     * 校验手机号 符合格式返回true
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNum(String phone) {
//		String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$";
        String regExp = "^(1[3-9])\\d{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 校验登录名
     *
     * @param loginName
     * @return
     */
    public static boolean isValidLoginName(String loginName) {
        String regExp = "^[a-z]\\w{0,19}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(loginName);
        return m.matches();
    }

    /**
     * 身份证号码校验
     *
     * @param idCradNo
     * @return
     */
    public static boolean isIDCardNo(String idCradNo) {
        String regExp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(idCradNo);
        return m.matches();
    }

    /**
     * 统一社会信用代码校验
     *
     * @param creditCode
     * @return
     */
    public static boolean isCreditCode(String creditCode) {
        String regExp = "^[0-9a-zA-Z]\\w{0,29}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(creditCode);
        return m.matches();
    }

    public static boolean isFixedPhone(String phone) {
        String regExp = "^(0\\d{2,3}-?)?\\d{7,8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {
        String regExp = "[0-9]+";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static void main(String[] args) {
//		String loginName = "a1234567890123456789";
//		String password = "12345@6";
//		String phone = "13111111111";
//		String idCradNo = "420115198906058714";
//		String creditCode = "abc123ABC0abc123ABC0abc123ABC0";
        String phone = "";
        System.out.println(isDigit(phone));
        System.out.println(replaceSpace("sf    dfsd    df\n\n\n\r\rsfsf   sf  "));
    }

    /**
     * 替换空格，换行符
     * @param string
     * @return
     */
    public static String replaceSpace(String string) {

        if (StringUtil.isNotEmpty(string)) {
            string = string.replaceAll("&", "&amp;")
                            .replaceAll(">", "&gt;")
                            .replaceAll("<", "&lt;")
                            .replaceAll("\"", "&quot;")
                            .replaceAll("\\r", "<br/>")
                            .replaceAll("\\n", "<br/>")
                            .replaceAll("\\s", "&nbsp;");
        }

        return string;
    }


    /**
     * 根据身份证号判断性别
     * @param idNumber
     * @return
     * @throws IllegalArgumentException
     */
    public static String judgeGender(String idNumber) throws IllegalArgumentException{
        System.out.println(idNumber.length());
        if(idNumber.length() != 18 && idNumber.length() != 15){
            throw new IllegalArgumentException("身份证号长度错误");
        }
        int gender = 0;
        if(idNumber.length() == 18){
            //如果身份证号18位，取身份证号倒数第二位
            char c = idNumber.charAt(idNumber.length() - 2);
            gender = Integer.parseInt(String.valueOf(c));
        }else{
            //如果身份证号15位，取身份证号最后一位
            char c = idNumber.charAt(idNumber.length() - 1);
            gender = Integer.parseInt(String.valueOf(c));
        }
        System.out.println("gender = " + gender);
        if(gender % 2 == 1){
            return "男";
        }else{
            return "女";
        }
    }

}
