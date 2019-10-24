package cn.zh.zhbackend.canseeguan.Utils;

import org.apache.commons.codec.digest.DigestUtils;


public class PptUtils {
    public static String SignActionUrl (String srcUrl)  throws Exception {
        /*String strResult = "";

        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        sha1.update(strResult.getBytes());
        byte[] sha1Bin = sha1.digest();
        System.out.println(sha1Bin);*/

        if (srcUrl==null)
        {return null;}
        else
        {return DigestUtils.sha1Hex(srcUrl);}
    }}

/*MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update(msg.getBytes());
            byte []sha1Bin = sha1.digest();
            printBytes(sha1Bin);
*/

