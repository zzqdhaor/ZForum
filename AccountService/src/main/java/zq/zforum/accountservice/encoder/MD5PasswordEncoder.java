package zq.zforum.accountservice.encoder;

import org.springframework.stereotype.Component;
import zq.zforum.MD5Util;

import java.security.NoSuchAlgorithmException;
@Component
public class MD5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String raw) {
        try {
            return MD5Util.getEncryptedPwd(raw);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean match(String raw, String encoded) {
        try {
            return MD5Util.validPassword(raw, encoded);
        } catch (Exception e) {
            return false;
        }
    }
}
