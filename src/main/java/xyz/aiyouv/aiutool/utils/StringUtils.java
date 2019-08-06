package xyz.aiyouv.aiutool.utils;

/**
 * 字符串处理工具
 * Util
 * @author Aiyouv
 * @version 1.0
 **/
public class StringUtils {

    public static final String UNDERLINE = "_";
    public static final char UNDERLINE_CHAR = '_';

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。<br>
     * 例如：hello_world=》helloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String toCamelCase(CharSequence name){
        if (null == name) {
            return null;
        }

        String name2 = name.toString();
        if (name2.contains(UNDERLINE)) {
            final StringBuilder sb = new StringBuilder(name2.length());
            boolean upperCase = false;
            for (int i = 0; i < name2.length(); i++) {
                char c = name2.charAt(i);
                if (c == UNDERLINE_CHAR) {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
            return sb.toString();
        } else {
            return name2;
        }
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式,首字母大写。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。<br>
     * 例如：hello_world=》HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String toCamelCaseInitialsUpper(CharSequence name){
        if (null == name) {
            return null;
        }

        String name2 = name.toString();
        final StringBuilder sb = new StringBuilder(name2.length());
        boolean upperCase = false;
        for (int i = 0; i < name2.length(); i++) {
            char c = name2.charAt(i);
            if(i == 0){
                sb.append(Character.toUpperCase(c));
                continue;
            }
            if (c == UNDERLINE_CHAR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }


    /**
     * 将下划线方式命名的字符串转换为驼峰式,首字母小写。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。<br>
     * 例如：hello_world=》HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String toCamelCaseInitialsLower(CharSequence name){
        if (null == name) {
            return null;
        }

        String name2 = name.toString();
        if (name2.contains(UNDERLINE)) {
            final StringBuilder sb = new StringBuilder(name2.length());
            boolean upperCase = false;
            for (int i = 0; i < name2.length(); i++) {
                char c = name2.charAt(i);
                if(i == 0){
                    sb.append(Character.toLowerCase(c));
                    continue;
                }
                if (c == UNDERLINE_CHAR) {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
            return sb.toString();
        } else {
            return name2;
        }
    }

    /**
     * 根据最后一个特定字符截取字符串
     * java.util.Date -> Date
     * @author Aiyouv
     * @param str 需要截取的字符串
     * @param regex 指定字符串
     * @return java.lang.String
     **/
    public static String substringLast(String str, String regex){
        String sb = str.substring(str.lastIndexOf(regex)+1, str.length());
        return sb;
    }

    /**
     * 根据最后一个特定字符截取字符串
     * int(11) -> int
     * @author Aiyouv
     * @param str 需要截取的字符串
     * @param regex 指定字符串
     * @return java.lang.String
     **/
    public static String substringFromFirst(String str, String regex){
        if(str == null){
            return null;
        }
        int index = str.indexOf(regex);
        if(index == -1){
            return str;
        }
        return str.substring(0,str.indexOf(regex));
    }
}
