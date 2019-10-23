package cn.zh.zhbackend.canseeguan.Utils;/**
 * ClassName: StringUtils <br/>
 * Description: <br/>
 * date: 2019/10/12 下午4:13<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

/**
 * @program: readexcel
 *
 * @description:
 *
 * @author: zxb
 *
 * @create: 2019-10-12 16:13
 **/
public class StringUtils {
    /**
     * 首字母转大写
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
    /**
     * 将数据库中的long型数据转成String类型
     * @param num
     * @return
     */
    public static String longToString(Object num){
        System.out.println("StringUtils-o:"+num);
        String s = num.toString();
        System.out.println("StringUtils-s:"+s);
        return  s;

    }
}
