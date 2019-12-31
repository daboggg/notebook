package ru.zinin.notebook.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.zinin.notebook.model.User;

import javax.servlet.http.HttpServletRequest;
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

    private final HttpServletRequest request;

    @Autowired
    public TokenFactory(HttpServletRequest request) {
        this.request = request;
    }

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

    // проверяет токен валидоность
    public boolean isValidToken() {
        String tokenFromHeader = request.getHeader("Token");
        TokenHolder holder = null;
        for (TokenHolder tokenHolder : tokenHolders) {
            if (tokenHolder.getToken().equals(tokenFromHeader)) {
                holder = tokenHolder;
                break;
            }
        }
        if (holder == null) {
            return false;
        } else {
            if ((holder.getCreationTimeToken() + timeValidityToken) < System.currentTimeMillis()) {
                return false;
            }
        }
        return true;
    }

    public void updateTimeValidityToken() {
        String tokenFromHeader = request.getHeader("Token");
        for (TokenHolder tokenHolder : tokenHolders) {
            if (tokenHolder.getToken().equals(tokenFromHeader)) {
                tokenHolder.setCreationTimeToken(System.currentTimeMillis());
            }
        }
    }

    @Data
    class TokenHolder {
        private Long userId;
        private String token;
        private long creationTimeToken;
    }
}
