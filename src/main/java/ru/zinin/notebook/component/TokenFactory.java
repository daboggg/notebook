package ru.zinin.notebook.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.zinin.notebook.model.User;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class TokenFactory {
    @Value("${time.validity.token}")
    private long timeValidityToken;

    private static List<TokenHolder> tokenHolders = new ArrayList<>();

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    //генерирует токен
    public static String generateNewToken() {
        byte[] randomBytes = new byte[36];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }


    // добавляет токен в userFromDb, создает TokenHolder,сохранаяет его в tokenHolders
    public User setToken(User userFromDb) {
        userFromDb.setToken(generateNewToken());
        TokenHolder tokenHolder = new TokenHolder();
        tokenHolder.setToken(userFromDb.getToken());
        tokenHolder.setCreationTimeToken(System.currentTimeMillis());
        tokenHolder.setUserId(userFromDb.getId());
        tokenHolders.add(tokenHolder);
        System.out.println(tokenHolders.size());
        return userFromDb;
    }

    @Data
    class TokenHolder {
        private Long userId;
        private String token;
        private long creationTimeToken;
    }
}
