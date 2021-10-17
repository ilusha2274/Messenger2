package repository;

import exception.PasswordMismatchException;
import exception.WrongEmailException;
import exception.WrongLoginPasswordException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CollectionUserRepository implements UserRepository {

    private ArrayList<User> users = new ArrayList<>();
    private Map<String, User> usersByEmail = new HashMap<>();

    @Override
    public User addUser(User user,String twoPassword) throws PasswordMismatchException, WrongEmailException {
        if (!(findEmailUser(user.getEmail())) && checkPassword(user.getPassword(),twoPassword)){
            users.add(user);
            usersByEmail.put(user.getEmail(),user);
            return user;
        }
        return null;
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public boolean findEmailUser(String email) throws WrongEmailException {

        if (usersByEmail.containsKey(email)){
            throw new WrongEmailException("email занят");
        }
        return false;
    }

    @Override
    public boolean checkPassword(String password, String twoPassword) throws PasswordMismatchException {
        if (password.equals(twoPassword)){
            return true;
        }else {
            throw new PasswordMismatchException("Пароли не совпадают");
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return usersByEmail.get(email);
    }

    @Override
    public User logInUser(String email, String password) throws WrongLoginPasswordException {
        User user = findUserByEmail(email);
        if (user != null && password.equals(user.getPassword())){
            return user;
        }else {
            throw new WrongLoginPasswordException("Неверное имя пользователя или пароль");
        }
    }
}
