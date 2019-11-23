package cn.plugin.core.utils;

import android.text.TextUtils;

import cn.mdruby.pickphotovideoview.App;
import cn.plugin.core.BaseCoreApp;


/**
 * Created by asmin on 2017/3/15.
 */

public class LogUtil {

    /**
     * 日志开关
     */
    private static boolean DEBUG = BaseCoreApp.DEBUG;

    public static void d(String tag, String message) {
        if (DEBUG) {
            android.util.Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (DEBUG) {
            android.util.Log.i(tag, message);
        }
    }


    public static void jsonFormat(String tag, String message, boolean isOutputOriginalContent) {
        JsonFormatOld(tag, message, isOutputOriginalContent);
    }

    public static void jsonFormat(String tag, String message) {
        JsonFormatOld(tag, message, true);
    }

    /**
     * Json格式化输出
     *
     * @param tag
     * @param message                 内容
     * @param isOutputOriginalContent 是否输入原内容
     */
    private static void JsonFormatOld(String tag, String message, boolean isOutputOriginalContent) {
        if (DEBUG && !TextUtils.isEmpty(message)) {
            if (isOutputOriginalContent)
                android.util.Log.e(tag,  message);
            android.util.Log.e(tag, format(convertUnicode(message)));
        }
    }


    public static void w(String tag, String message) {
        if (DEBUG) {
            android.util.Log.w(tag, message);
        }

    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            android.util.Log.e(tag, message);
        }
    }

    /**
     * json格式化
     *
     * @param jsonStr
     * @return
     */
    private static String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    /**
     * Unicode解码
     *
     * @param ori
     * @return
     */
    private static String convertUnicode(String ori) {
        char aChar;
        int len = ori.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);

        }
        return outBuffer.toString();
    }
}
