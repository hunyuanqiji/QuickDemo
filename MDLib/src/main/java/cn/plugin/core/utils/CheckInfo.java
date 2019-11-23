package cn.plugin.core.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.matches;

/**
 * Created by 冯超 on 2017/1/14.
 */
public class CheckInfo {

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPassword(String strPass , Context context){
        if (strPass.length() > 10 || strPass.length() < 6) {
            Toast.makeText(context, "密码长度应当在6-10个字符之间！您所设置的密码长度为" + strPass.length() + "。", Toast.LENGTH_LONG).show();
            return false;
        }
        Pattern pLetter = Pattern.compile("[a-zA-Z]+");
        Pattern pCase = Pattern.compile("[A-Z]+");
        Pattern pNumber = Pattern.compile("[0-9]+");
        Matcher mLetter = pLetter.matcher(strPass);
        Matcher mCase = pCase.matcher(strPass);
        Matcher mNumber = pNumber.matcher(strPass);
        if ( !mLetter.find(0) || !mNumber.find(0)) {
            Toast.makeText(context, "密码必须包含数字和字母。", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean isTelephoneNO(String mobiles) {
        String noRegex  ="^\\d{5,11}$";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(noRegex);
    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

      return m.matches();
        //    return true;
    }
    public static boolean isMobilePhoneNO(String phone) {
//        String str = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";
//        String str = "^((13[0-9])|(15[^4,\\D])|17[0-9]|(18[0,0-9]))\\d{8}$";
//        String str = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\\\d{8}$";
        String str = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(phone);

        boolean b = m.matches() && !TextUtils.isEmpty(phone) && phone.length() == 11;

        return b;
//        return true;
    }
    /*** 验证电话号码（座机电话）
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsTelephone(String str) {
        String regex = "^0(10|2[0-5789]-|\\d{3})-?\\d{7,8}$";
        return matches(regex, str);
    }

   /* //检查电话号码
    public static boolean isPhoneNumberValid(String phoneNumber)
    {
        boolean isValid = false;
        *//** 可接受的电话格式有:
         * ^//(? : 可以使用 "(" 作为开头
         * (//d{3}): 紧接着三个数字
         * //)? : 可以使用")"接续
         * [- ]? : 在上述格式后可以使用具选择性的 "-".
         * (//d{3}) : 再紧接着三个数字
         * [- ]? : 可以使用具选择性的 "-" 接续.
         * (//d{5})$: 以五个数字结束.
         * 可以比较下列数字格式:
         * (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890
         *//*
        String expression = "^//(?(//d{3})//)?[- ]?(//d{3})[- ]?(//d{5})$";

        *//** 可接受的电话格式有:
         * ^//(? : 可以使用 "(" 作为开头
         * (//d{3}): 紧接着三个数字
         * //)? : 可以使用")"接续
         * [- ]? : 在上述格式后可以使用具选择性的 "-".
         * (//d{4}) : 再紧接着四个数字
         * [- ]? : 可以使用具选择性的 "-" 接续.
         * (//d{4})$: 以四个数字结束.
         * 可以比较下列数字格式:
         * (02)3456-7890, 02-3456-7890, 0234567890, (02)-3456-7890
         *//*
        String expression2 = "^//(?(//d{3})//)?[- ]?(//d{4})[- ]?(//d{4})$";

        CharSequence inputStr = phoneNumber;
        *//*创建Pattern*//*
        Pattern pattern = Pattern.compile(expression);
        *//*将Pattern 以参数传入Matcher作Regular expression*//*
        Matcher matcher = pattern.matcher(inputStr);
        *//*创建Pattern2*//*
        Pattern pattern2 = Pattern.compile(expression2);
        *//*将Pattern2 以参数传入Matcher2作Regular expression*//*
        Matcher matcher2 = pattern2.matcher(inputStr);
        if (matcher.matches())//|| matcher2.matches())
        {
            isValid = true;
        }
        return isValid;
    }*/

    /** * 纯数字判断
     * @param str
     * @return */
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;}

    /**
     * 校验字符串是否是数值(包含小数与负数)
     * 示例：
     * false : . 1. 1sr -  12. -12.
     * true: -12 -12.0 -12.056 12 12.0 12.056
     *
     * @param str 需要校验的字符串
     * @return false :不是数值 true：是数值
     */
    public static Boolean checkNumber(String str) {
        String regex = "-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?";
        return str != null && str.matches(regex);
    }

    /**
     * 身份证格式判断
     * @param id
     * @return
     */
    public static boolean isIdentifier(String id) {
      /*  String str = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        */

        String str = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(id);
        return m.matches();
//        return true;
    }

    public static boolean isName(String name){
        String str = "[a-zA-Z\\u4e00-\\u9fa5][a-zA-Z0-9\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(name);
//        return m.matches();
        return true;
    }

    public static boolean isChinese(String password){
        String str = "[\\u4E00-\\u9FA5\\uF900-\\uFA2D]";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

}
