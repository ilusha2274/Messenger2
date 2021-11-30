package repository;

import exception.PasswordMismatchException;
import exception.WrongEmailException;
import exception.WrongLoginPasswordException;
import helper.PrintFriend;

import java.sql.SQLException;
import java.util.List;


public interface UserRepository {
    User addUser(User user, String twoPassword) throws PasswordMismatchException, WrongEmailException, SQLException;

    void removeUserByEmail(String email);

    boolean findEmailUser(String email) throws WrongEmailException;

    boolean checkPassword(String password, String twoPassword) throws PasswordMismatchException;

    User logInUser(String email, String password) throws WrongLoginPasswordException;

    User findUserByEmail(String email);

    User findUserById(int id);

    void addNewFriends(User user1, User user2, int chatId);

    List<PrintFriend> findListFriendsByUser(User user);
}
