package fr.benco11.asynconf.exercices.ex3.services;

import fr.benco11.asynconf.exercices.ex3.entity.User;
import fr.benco11.asynconf.exercices.ex3.permissions.PermissionsGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service qui gère les utilisateurs
 */
public class AccountService {
    public static final String ADMIN_NAME = "Administrateur";
    private final Map<String, User> users;

    public AccountService() {
        users = new HashMap<>();
        saveAccount(new User(ADMIN_NAME, PermissionsGroup.GlobalPermissionsGroup.ADMIN));
    }

    public void saveAccount(User user) {
        users.put(user.name(), user);
    }

    public Optional<User> accountByName(String name) {
        return (users.containsKey(name)) ? Optional.of(users.get(name)) : Optional.empty();
    }

    public boolean isRegistered(User user) {
        return isRegistered(user.name());
    }

    public boolean isRegistered(String name) {
        return accountByName(name).isPresent();
    }

    public void deleteAccount(String name) {
        users.remove(name);
    }
}
