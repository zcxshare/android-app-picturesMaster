package com.scanmaster.commonlibrary.utils;

import android.text.TextUtils;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具
 */

public class RegexUtils {
    /**
     * 自定义正则(获取匹配格式的第一个)
     */
    public static String getFirstMatcher(String regex, String source) {
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            result = matcher.group(0);
        }
        return result;
    }

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        if (!TextUtils.isEmpty(email)) {
            String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
            return Pattern.matches(regex, email);
        } else {
            return false;
        }
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        if (idCard != null) {
            String regex = "([1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])" +
                    "|([1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2})";// TODO: 2018/1/11 0011 我修改过,原来: "[1-9]\\d{13,16}[a-zA-Z0-9]{1}"
            return Pattern.matches(regex, idCard);
        } else {
            return false;
        }
    }

    /**
     * 验证银行卡号
     *
     * @param bankNumber 有公账
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBankNumber(String bankNumber) {
        if (bankNumber != null) {
            String regex = "\\d{10,19}";
            return Pattern.matches(regex, bankNumber);
        } else {
            return false;
        }
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *               <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *               <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *               <p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        if (mobile != null) {
            String regex = "(\\+\\d+)?1[34578]\\d{9}$";
            return Pattern.matches(regex, mobile);
        } else {
            return false;
        }
    }

    public static String hiddenMobile(String mobile) {
        StringBuilder mobileBuilder = new StringBuilder(mobile);
        String mobileFront = mobileBuilder.substring(0, 3);
        String mobileLatter = mobileBuilder.substring(mobile.length() - 4);
        return mobileFront + "****" + mobileLatter;
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *              数字之后是空格分隔的国家（地区）代码。</p>
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        if (phone != null) {
            String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
            return Pattern.matches(regex, phone);
        } else {
            return false;
        }
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {
        if (digit != null) {
            String regex = "\\-?[1-9]\\d+";
            return Pattern.matches(regex, digit);
        } else {
            return false;
        }

    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {
        if (!TextUtils.isEmpty(decimals)) {
            String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
            return Pattern.matches(regex, decimals);
        } else {
            return false;
        }
    }

    /**
     * 验证空白字符
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBlankSpace(String blankSpace) {
        if (!TextUtils.isEmpty(blankSpace)) {
            String regex = "\\s+";
            return Pattern.matches(regex, blankSpace);
        } else {
            return false;
        }
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) {
        if (!TextUtils.isEmpty(chinese)) {
            String regex = "^[\u4E00-\u9FA5]+$";
            return Pattern.matches(regex, chinese);
        } else {
            return false;
        }
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) {
        if (!TextUtils.isEmpty(birthday)) {
            String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
            return Pattern.matches(regex, birthday);
        } else {
            return false;
        }
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * <pre>
     * 获取网址 URL 的一级域
     * </pre>
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        // 获取完整的域名
        // Pattern p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        matcher.find();
        return matcher.group();
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }

    /**
     * 得到中文的文本
     *
     * @param text 要改变的文本内容
     * @return 返回去除非中文后的文本
     */
    public static String getChinese(String text) {
        return text.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
    }

    /**
     * 得到非数字的文本
     *
     * @param text 要改变的文本内容
     * @return 返回去除数字后的文本
     */
    public static String getUnNumber(String text) {
        return text.replaceAll("\\d", "");
    }

/*
    //英文
    string enRegS =
    @ "[a-zA-Z]+";
    Regex enRegR = new Regex(enRegS);
    Match enMatch = enRegR.Match(str);
    while(enMatch.Success)

    {
        Enresult.Add(enMatch.ToString());
        enMatch = enMatch.NextMatch();
    }

    //数字
    string numRegS =
    @ "\d+";
    Regex numRegR = new Regex(numRegS);
    Match numMatch = numRegR.Match(str);
    while(numMatch.Success)

    {
        Numresult.Add(numMatch.ToString());
        numMatch = numMatch.NextMatch();
    }*/
}
