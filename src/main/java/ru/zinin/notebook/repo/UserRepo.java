package ru.zinin.notebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zinin.notebook.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
}
