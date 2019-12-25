package ru.zinin.notebook.component;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import ru.zinin.myexpenses.dto.Token;
//
//import java.security.SecureRandom;
//import java.util.Base64;
//
//@Component
//public class TokenGenerator {
//
//    @Value("${time.validity.token}")
//    private long timeValidityToken;
//
//    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
//    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
//
//    public static String generateNewToken() {
//        byte[] randomBytes = new byte[36];
//        secureRandom.nextBytes(randomBytes);
//        return base64Encoder.encodeToString(randomBytes);
//    }
//
//    public Token getToken() {
//        String newToken = generateNewToken();
//        return new Token(newToken, timeValidityToken);
//    }
//}
