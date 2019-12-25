package ru.zinin.notebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.zinin.notebook.component.TokenFactory;
import ru.zinin.notebook.exception.EmailAlreadyExist;
import ru.zinin.notebook.exception.UserNotFound;
import ru.zinin.notebook.exception.WrongPassword;
import ru.zinin.notebook.model.User;
import ru.zinin.notebook.repo.UserRepo;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final TokenFactory tokenFactory;

    @Autowired
    public UserService(UserRepo userRepo, TokenFactory tokenFactory) {
        this.userRepo = userRepo;
        this.tokenFactory = tokenFactory;
    }

    public ResponseEntity<User> register(User user) throws EmailAlreadyExist {

        User userFromDb = userRepo.getUserByEmail(user.getEmail());

        if (userFromDb != null) {
            throw new EmailAlreadyExist(user.getEmail());
        } else {
            //устанавливаем время регистрации
            user.setCreationTime(System.currentTimeMillis());
            //сохраняем в базу данных пользователя
            userFromDb = userRepo.save(user);
            //добавляем токен
            userFromDb = tokenFactory.setToken(userFromDb);
        }

        return ResponseEntity.ok(userFromDb);
    }

    public ResponseEntity<User> login(User user) throws UserNotFound, WrongPassword {
        User userFromDb = userRepo.getUserByEmail(user.getEmail());
        if (userFromDb == null) {
            throw new UserNotFound(user.getEmail());
        } else {
            if (!userFromDb.getPassword().equals(user.getPassword())) {
                throw new WrongPassword(user.getPassword());
            } else {
                userFromDb = tokenFactory.setToken(userFromDb);
                return ResponseEntity.ok(userFromDb);
            }
        }
    }
}
