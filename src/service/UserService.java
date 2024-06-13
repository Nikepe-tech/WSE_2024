package service;

import converter.UserConverter;
import dto.User;
import storage.UserRepo;
import storage.impl.UserRepoImpl;

import java.util.Optional;

/**
 * Serwis obsługujący operacje związane z użytkownikami.
 */

public class UserService {

    private static UserService instance;

    private final UserRepo userRepo = UserRepoImpl.getInstance();
    private final UserValidator userValidator = UserValidator.getInstance();


    private UserService() {
    }

    /**
     * Metoda do uzyskania instancji serwisu (singleton).
     * @return Instancja serwisu użytkowników.
     */

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    /**
     * Metoda do sprawdzania loginu i hasła użytkownika.
     * @param userNameInput Wprowadzony login użytkownika.
     * @param passwordInput Wprowadzone hasło użytkownika.
     * @return Obiekt użytkownika, jeśli login i hasło są poprawne, w przeciwnym razie null.
     */

    public User checkLogin(final String userNameInput, final String passwordInput) {
        String username = userNameInput.trim();
        String password = passwordInput.trim();
        Optional<User> userOptional = userRepo.findByUserName(username);
        if (userOptional.isPresent()) {
            if (userOptional.get().getPassword().equals(password)) {
                return userOptional.get();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Metoda do walidacji danych użytkownika.
     * @param request Żądanie walidacji użytkownika.
     * @return Wynik walidacji użytkownika.
     */

    public UserValidationResult validate(final UserValidationRequest request) {
        if (userRepo.findByUserName(request.userName()).isPresent()) {
            UserValidationResult nonUniqueUserName = new UserValidationResult();
            nonUniqueUserName.addError("Ten login jest już zajęty");
            return nonUniqueUserName;
        }
        return userValidator.validate(request);
    }

    /**
     * Metoda do tworzenia nowego użytkownika.
     * @param request Żądanie tworzenia użytkownika.
     * @return Utworzony użytkownik.
     */

    public User create(final UserValidationRequest request) {
        User newUser = UserConverter.toObject(request);
        newUser = userRepo.save(newUser);
        return newUser;
    }
}
