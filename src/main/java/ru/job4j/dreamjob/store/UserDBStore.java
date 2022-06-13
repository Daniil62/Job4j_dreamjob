package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class UserDBStore {

    private final BasicDataSource pool;
    private static Logger log = Logger.getLogger(PostDBStore.class.getName());

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO users(email, password) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            if (ps.executeUpdate() > 0) {
                try (ResultSet id = ps.getGeneratedKeys()) {
                    if (id.next()) {
                        user.setId(id.getInt(1));
                    }
                }
                result = Optional.of(user);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "error: ", e);
        }
        return result;
    }

    public User findByEmail(String email) {
        User result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE email = ?")
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = new User(
                            it.getInt("id"),
                            it.getString("email"),
                            it.getString("password"));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "error: ", e);
        }
        return result;
    }

    public boolean deleteUser(String email, String password) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "DELETE FROM users WHERE email = ? AND password = ?")
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try {
                result = ps.executeUpdate() > 0;
            } finally {
                ps.close();
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "error: ", e);
        }
        return result;
    }

    public void deleteAll() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("DELETE FROM users")
        ) {
            try {
                ps.execute();
            } finally {
                ps.close();
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "error: ", e);
        }
    }
}
