import zq.zforum.MD5Util;

import java.security.NoSuchAlgorithmException;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(MD5Util.getEncryptedPwd("123"));
    }
}
