package repository;

import exception.PasswordMismatchException;
import exception.WrongEmailException;
import exception.WrongLoginPasswordException;


public interface UserRepository {
    User addUser(User user, String twoPassword) throws PasswordMismatchException, WrongEmailException;
    void removeUser (User user);
    boolean findEmailUser (String email) throws WrongEmailException;
    boolean checkPassword (String password, String twoPassword) throws PasswordMismatchException;
    User logInUser (String email, String password) throws WrongLoginPasswordException;
    User findUserByEmail(String email);
}
