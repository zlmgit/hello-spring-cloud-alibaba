package com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    /**
     * 小写
     */
    public static final StringFormatConstants LOWER_CASE = StringFormatConstants.LOWER_CASE;

    /**
     * 大写
     */
    public static final StringFormatConstants UPPER_CASE = StringFormatConstants.UPPER_CASE;

    private enum StringFormatConstants {

                                        /**
                                         * 小写
                                         */
                                        LOWER_CASE,

                                        /**
                                         * 大写
                                         */
                                        UPPER_CASE;
    }

    public final static String regExpStr2  = "[\\s|\\#|\\%|\\$|\\&|\\'|\\@|\\+|\\*|\\?|\\(|\\)|\\[|\\]|<|>|/|^|!|~|.|,|;]";
    public final static String BASE_CHAR   = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public final static String BASE_NUMBER = "0123456789";
    public final static String regExpStr3 = "[*\"|:<>/?\\\\]";

    /**
     * 全角空格
     */
    private static final char  FULL_SPACE  = 12288;

    /**
     * 半角空格
     */
    private static final char  HALF_SPACE  = 32;

    /**
     * 字符串转换为ASCII码
     * @param str
     * @return
     */
    public static String strToAscii(String str) {//
        char[] chars = str.toCharArray(); // 把字符中转换为字符数组
        String result = "";
        for (int i = 0; i < chars.length; i++) {// 输出结果
            result += chars[i];
        }
        return result;
    }

    /**
     * 把ascii字符串转成对应的字符串
     * @param ascii 以空格分开
     * @return
     */
    public static String asciiToStr(String ascii) {// ASCII转换为字符串
        String[] chars = ascii.split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            sb.append("" + (char) Integer.parseInt(chars[i]));
        }
        return sb.toString();
    }

    /**
     * @param str 原字符串
     * @param fillStr 填充的字符串
     * @param length 需要的长度
     * @return 按需要的长度,把填充的字符串填充到原字符串的左边
     */
    public static String LFillStr(String str, String fillStr, int length) {
        int i = length - str.length();
        String mStr = "";
        for (int j = 0; j < i; j++)
            mStr = mStr + fillStr;
        mStr = mStr + str;
        return mStr;
    }

    /**
     * @param str 原字符串
     * @param fillStr 填充的字符串
     * @param length 需要的长度
     * @return 按需要的长度,把填充的字符串填充到原字符串的右边
     */
    public static String RFillStr(String str, String fillStr, int length) {
        int i = length - str.length();
        String mStr = "";
        for (int j = 0; j < i; j++)
            mStr = mStr + fillStr;
        mStr = str + mStr;
        return mStr;
    }

    /**
     * 在str中查找所有符合正则表达式(regEx)并用str2替换
     * @param str 传入的主字符串
     * @param regEx:可以为空或空字符串,如果为空，则用工具类StringUtil中的regExpStr常量的值
     * @param str2　替的字符串
     * @return
     */
    public static String delSpecialStr(String str, String regEx, String str2) {
        String newstr = "";
        try {
            if (str != null && !str.equals("")) {
                if (regEx == null || regEx.equals(""))
                    regEx = regExpStr2;
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(str);
                newstr = m.replaceAll(str2);
            }
        } catch (Exception e) {
        }
        return newstr;
    }

    /**
     * 对安全字符串进行解码，返回原始字符串
     * 
     * @param stringTmp
     * @return
     */
    public static String decode(String stringTmp) {
        if (stringTmp == null)
            return "";
        String stringname = "";
        for (int i = 0; i < stringTmp.length(); i++) {
            char charint = stringTmp.charAt(i);
            switch (charint) {
                case 126: // '~'
                    String stringtmp = stringTmp.substring(i + 1, i + 3);
                    stringname = stringname + (char) Integer.parseInt(stringtmp, 16);
                    i += 2;
                    break;

                case 94: // '^'
                    String stringtmp2 = stringTmp.substring(i + 1, i + 5);
                    stringname = stringname + (char) Integer.parseInt(stringtmp2, 16);
                    i += 4;
                    break;

                default:
                    stringname = stringname + charint;
                    break;
            }
        }

        return stringname;
    }

    /**
     * 对原始字符串进行编码，返回安全字符串
     * 
     * @param stringname
     * @return
     */
    public static String encode(String stringname) {
        if (stringname == null)
            return "";
        String stringTmp = "";
        for (int i = 0; i < stringname.length(); i++) {
            int charat = stringname.charAt(i);
            if (charat > 255) {
                String tmp = Integer.toString(charat, 16);
                for (int j = tmp.length(); j < 4; j++)
                    tmp = "0" + tmp;

                stringTmp = stringTmp + "^" + tmp;
            } else {
                if (charat < 48 || charat > 57 && charat < 65 || charat > 90 && charat < 97
                    || charat > 122) {
                    String stringat = Integer.toString(charat, 16);
                    for (int j = stringat.length(); j < 2; j++)
                        stringat = "0" + stringat;

                    stringTmp = stringTmp + "~" + stringat;
                } else {
                    stringTmp = stringTmp + stringname.charAt(i);
                }
            }
        }

        return stringTmp;
    }

    /**
     * 对安全字符串进行解码，返回原始字符串
     * 
     * @param stringTmp
     * @return
     */
    public static String decodeBase64(String stringTmp) {
        if (isEmpty(stringTmp))
            return "";
        return new String(Base64.decodeBase64(stringTmp.getBytes()));
    }

    /**
     * 对原始字符串进行编码，返回安全字符串
     * 
     * @param stringTmp
     * @return
     */
    public static String encodeBase64(String stringTmp) {
        if (isEmpty(stringTmp))
            return "";
        return new String(Base64.encodeBase64(stringTmp.getBytes()));
    }

    /**
     * 判断字符串是否数值 
     * @param str
     * @return true:是数值 ；false：不是数值 ;null 返回false
     */
    public static boolean isNumber(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]*)?$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 格式化实数，实现3位一瞥,保留scale位小数,如果位数不够，则添0站位
     * @param oldStr
     * @param scale
     * @return
     */
    public static String getFormat(Object oldvalue, int scale) {
        if (oldvalue == null) {
            return "";
        }
        String newStr = "";
        String formatStr = ",##0";
        if (scale > 0) {
            formatStr += ".";
            for (int i = 0; i < scale; i++) {
                formatStr += "0";
            }
        }
        try {
            NumberFormat nf = new DecimalFormat(formatStr);
            newStr = nf.format(oldvalue);
        } catch (Exception e) {
            newStr = String.valueOf(oldvalue);
        }
        return newStr;
    }

    /**
     * 生成指定长度随机字符
     * @param length
     * @return
     */
    public static String randomString(int length) {
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(BASE_CHAR.charAt(num));
        }
        return buf.toString();
    }

    public static String randomNumber(int length) {
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(10);
            buf.append(BASE_NUMBER.charAt(num));
        }
        return buf.toString();
    }

    /**
     * 取指定范围的随机整数。
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumber(int min, int max) {
        if (min <= 0 || max < min) {
            return 0;
        }

        Random rand = new Random();
        int s = rand.nextInt(max - min) + 1;
        return s;
    }

    /**
     * 输入内容转成Unicode
     * @param s	输入内容
     * @return	Unicode码
     */
    public static String toUnicodeString(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                sb.append(Integer.toHexString(c));
            }
        }
        return sb.toString();
    }

    /**
     * 输入内容转成空或string
     * @param s	输入内容
     * @return
     */
    public static String nullToString(String s) {
        try {

            String str = "" + s;
            if (str.equals("null") || s == null) {
                str = "";
            }
            return str;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将对象转为字符
     * @param object
     * @return
     * @throws Exception
     */
    public static String object2string(Object object) {
        if (object == null) {
            return "";
        }
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            return Base64.encodeBase64String(bos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 字符转为对象
     * @param strdata
     * @return
     * @throws Exception
     */
    public static Object string2object(String strdata) {
        if (strdata == null || strdata.equals("")) {
            return null;
        }
        try {
            byte[] strdatabyte = Base64.decodeBase64(strdata);
            ByteArrayInputStream bis = new ByteArrayInputStream(strdatabyte);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object o = ois.readObject();
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取指定长度随机数,注意：结果可能会以0开头。
     * @return
     */
    public static String getRandomNumber(int len) {
        if (len <= 0) {
            return "";
        }
        Random random = new Random();

        char c[] = new char[10];

        for (int m = 48, n = 0; m < 58; m++, n++) {
            c[n] = (char) m;
        }
        String sRand = "";
        for (int i = 0; i < len; i++) {
            int x = random.nextInt(10);
            String rand = String.valueOf(c[x]);
            sRand += rand;
        }
        return sRand;
    }

    /**
     * 将对象转为字符，比较是否相等
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean compareString(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if ((obj1 == null && obj2 != null) || (obj1 != null && obj2 == null)) {
            return false;
        }
        return obj1.toString().equals(obj2.toString());
    }

    /**
     * 判断字符是否是emoji表情
     * @param codePoint
     * @return
     */
    public static boolean isEmojiCharacter(char codePoint) {
        String rules[] = { "[\uE001-\uE05A]", "[\uE101-\uE15A]", "[\uE201-\uE253]",
                           "[\uE301-\uE34D]", "[\uE401-\uE44C]", "[\uE501-\uE537]" };
        for (String rule : rules) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(String.valueOf(codePoint));
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
        /*
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
        */
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (isEmpty(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }

        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    /**
     * 判断字符串变量是否为空
     * 
     * @param obj
     *            字符串变量
     * @return true时字符串为空
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.equals("null") || str.trim().length() == 0 || "(null)".equals(str))//HADOOP中的空值
            return true;
        else
            return false;
        /* if (null == obj) {
             return true;
         } else {
             return "".equals(obj);
         }*/
    }

    /**
     * 处理字符串变量的首字母
     * 
     * @param obj
     *            字符串变量
     * @param type
     *            处理类型
     * @return 处理后的字符串变量
     */
    public static String firstLetterFormat(String obj, StringFormatConstants type) {

        String firstLetter = null;

        // 如果字符串变量为空，则直接返回
        if (isEmpty(obj)) {
            return obj;
        }

        if (StringFormatConstants.UPPER_CASE.equals(type)) {
            // 获取字符串变量的首字母的大写字母
            firstLetter = obj.substring(0, 1).toUpperCase();
        } else if (StringFormatConstants.LOWER_CASE.equals(type)) {
            // 获取字符串变量的首字母的小写字母
            firstLetter = obj.substring(0, 1).toLowerCase();
        }

        // 如果字符串变量长度大于1，则返回首字母处理后的字符串变量，
        // 否则返回处理后的首字母
        if (1 < obj.length()) {
            return firstLetter + obj.substring(1);
        } else {
            return firstLetter;
        }
    }

    /**
     * 去除尾部空格
     * 
     * @param str
     *            对象字符串
     * @return 去除空格后的字符串
     */
    public static String rtrim(String str) {

        for (int i = str.length() - 1; i >= 0; i--) {
            if (HALF_SPACE != str.charAt(i) && FULL_SPACE != str.charAt(i)) {
                return str.substring(0, i + 1);
            }
        }
        return "";
    }

    /**
     * 判断一个字符串不是空字符串
     * 
     * @param str
     *            字符串
     * @return 空返回false 非空返回true
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断二个字符串是否相等
     * 
     * @param str
     *            字符串
     * @param str1
     *            字符串
     * @return 不相等false 相等true
     */
    public static boolean equals(String str, String str1) {
        if (isNotEmpty(str) && isNotEmpty(str1)) {
            return str.equals(str1);
        }
        return false;
    }

    /**
     * 字符串转INT
     * 
     * @param str
     * @return
     */
    public static Integer stringToInt(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return Integer.parseInt(str);
    }

    /**
     * obj转字符串
     * 
     * @param obj
     * @return
     */
    public static String objectToStr(Object obj) {
        if (null == obj) {
            return "";
        }
        return String.valueOf(obj);
    }

    /**
     * 删除所有的HTML标签
     * @param source 需要进行除HTML的文本
     * @return*/
    public static String deleteAllHTMLTag(String source) {
        if (source == null) {
            return "";
        }
        String s = source;
        /** 删除普通标签 */
        s = s.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
        /** 删除转义字符 */
        s = s.replaceAll("&.{2,6}?;", "");
        return s;
    }

    /**
     * 删除所有的特殊标签
     * @param str
     * @return
     */
    public static String replace(String str) {
        String dest = "";
        if (!StringUtils.isEmpty(str)) {
            Pattern p = Pattern.compile("\\s+|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
        }
        return dest;
    }
    public static String replaceTel(String tel){
        if (isEmpty(tel)){
            return null;
        }
        return tel.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }


    /**
     * 1.去除html格式 去除特殊字符
     * 根据规定文本长度 和占位符 取出列表简介内容
     * @param str 原文本
     * @param size 长度
     * @param placeHolder 占位
     * @return
     */
    public static String takeCut(String str, Integer size, String placeHolder){
        str = replace(deleteAllHTMLTag(str));
        if (StringUtil.isNotEmpty(str) && str.length() > size) {
            return str.substring(0, size) + placeHolder;
        } else {
            return str;
        }
    }
    public static String repFileName(String fileName){
        if(isEmpty(fileName)){
            return fileName;
        }
        return fileName.replaceAll(regExpStr3,"_");
    }
}
