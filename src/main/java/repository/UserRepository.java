package repository;

import exception.PasswordMismatchException;
import exception.WrongEmailException;
import exception.WrongLoginPasswordException;
import org.springframework.ui.Model;

import java.sql.SQLException;


public interface UserRepository {
    User addUser(User user, String twoPassword) throws PasswordMismatchException, WrongEmailException, SQLException;

    void removeUserByEmail(String email);

    boolean findEmailUser(String email) throws WrongEmailException;

    boolean checkPassword(String password, String twoPassword) throws PasswordMismatchException;

    User logInUser(String email, String password) throws WrongLoginPasswordException;

    User findUserByEmail(String email);

    User findUserById(int id);
}
