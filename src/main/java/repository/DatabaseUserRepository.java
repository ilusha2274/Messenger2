package repository;

import exception.PasswordMismatchException;
import exception.WrongEmailException;
import exception.WrongLoginPasswordException;
import helper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

public class DatabaseUserRepository implements UserRepository, UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> index() {
        return jdbcTemplate.query("SELECT * FROM users", new UserMapper());
    }

    @Override
    public User addUser(User user, String twoPassword) throws PasswordMismatchException, WrongEmailException {

        if (!(findEmailUser(user.getEmail())) && checkPassword(user.getPassword(), twoPassword)) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode(user.getPassword());

            int id = jdbcTemplate.queryForObject("INSERT INTO users (user_name,user_email,user_password,enabled) VALUES(?,?,?,?) RETURNING user_id",
                    Integer.class,
                    user.getName(), user.getEmail(), password,user.isEnabled());

            jdbcTemplate.update("INSERT INTO authorities (authority,user_id) VALUES(?,?)", "USER", id);

            user.setId(id);

            return user;
        }
        return null;
    }

    @Override
    public void removeUserByEmail(String email) {
        jdbcTemplate.update("DELETE FROM users WHERE user_email=?", email);
    }

    @Override
    public boolean findEmailUser(String email) throws WrongEmailException {

        if (findUserByEmail(email) != null) {
            throw new WrongEmailException("email занят");
        }
        return false;

    }

    @Override
    public boolean checkPassword(String password, String twoPassword) throws PasswordMismatchException {
        if (password.equals(twoPassword)) {
            return true;
        } else {
            throw new PasswordMismatchException("Пароли не совпадают");
        }
    }

    @Override
    public User logInUser(String email, String password) throws WrongLoginPasswordException {
        User user = findUserByEmail(email);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        } else {
            throw new WrongLoginPasswordException("Неверное имя пользователя или пароль");
        }
    }

    @Override
    public User findUserByEmail(String email) {

        return jdbcTemplate.query("SELECT * FROM users WHERE user_email=?", new Object[]{email},
                new UserMapper()).stream().findAny().orElse(null);
    }

    @Override
    public User findUserById (int id){
        return jdbcTemplate.query("SELECT * FROM users WHERE user_id=?", new Object[]{id},
                new UserMapper()).stream().findAny().orElse(null);
    }

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        return findUserByEmail(s);
    }
}
