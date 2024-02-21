package zq.zforum.accountservice.encoder;

import org.springframework.stereotype.Component;

public interface PasswordEncoder {

    String encode(String raw);

    boolean match(String raw, String encoded);

}