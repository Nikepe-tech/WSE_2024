package storage.impl;

import converter.UserConverter;
import dto.User;
import logger.Logger;
import storage.AccountRepo;
import storage.UserRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementacja repozytorium użytkowników.
 */

public class UserRepoImpl implements UserRepo {

    private final String USERS_FILE = "./resources/users.csv";
    private static final AccountRepo accountRepo = AccountRepoImpl.getInstance();
    private static UserRepo instance;

    private AtomicLong userIdCounter;

    /**
     * Prywatny konstruktor inicjalizujący repozytorium użytkowników.
     * Wczytuje dane z pliku użytkowników i ustawia licznik identyfikatorów użytkowników.
     */

    private UserRepoImpl() {
        try (FileReader fileReader = new FileReader(USERS_FILE);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            OptionalLong max = bufferedReader.lines()
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.split(",")[0])
                    .mapToLong(Long::parseLong)
                    .max();
            if (max.isPresent()) {
                userIdCounter = new AtomicLong(max.getAsLong());
                Logger.getLogger().debug(this.getClass(), "Created UserIdCounter with initial value: %d".formatted(userIdCounter.get()));
            } else {
                userIdCounter = new AtomicLong();
                Logger.getLogger().debug(this.getClass(), "Created empty UserIdCounter started with 0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metoda do uzyskania instancji repozytorium użytkowników.
     * @return Instancja repozytorium użytkowników.
     */

    public static UserRepo getInstance() {
        if (instance == null) {
            instance = new UserRepoImpl();
        }
        return instance;
    }

    /**
     * Metoda do wyszukiwania użytkownika po nazwie użytkownika.
     * @param userName Nazwa użytkownika do wyszukania.
     * @return Optional zawierający znalezionego użytkownika, jeśli istnieje.
     */

    @Override
    public Optional<User> findByUserName(String userName) {
        try (FileReader fileReader = new FileReader(USERS_FILE);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            Optional<User> userOptional = bufferedReader.lines()
                    .map(UserConverter::toObject)
                    .filter(user -> user.getUserName().equals(userName))
                    .findFirst();

            userOptional.ifPresent(u -> u.setAccount(accountRepo.findByUserId(u.getId()).orElse(null)));

            return userOptional;
        } catch (Exception e) {
            Logger.getLogger().error(this.getClass(), "Problem reading file");
            return Optional.empty();
        }
    }

    /**
     * Metoda do zapisywania lub aktualizowania użytkownika w repozytorium.
     * Jeśli użytkownik nie ma przypisanego identyfikatora (jest nowy), zostaje dodany do repozytorium.
     * Jeśli użytkownik ma przypisany identyfikator, zostaje zaktualizowany w repozytorium.
     * @param user Użytkownik do zapisania lub zaktualizowania.
     * @return Zapisany lub zaktualizowany użytkownik.
     */

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            return insert(user);
        } else {
            return update(user);
        }
    }

    /**
     * Prywatna metoda do dodawania nowego użytkownika do repozytorium.
     * @param user Użytkownik do dodania.
     * @return Dodany użytkownik.
     */

    private User insert(User user) {
        long newUserId = userIdCounter.incrementAndGet();
        user.setId(newUserId);
        try (FileWriter fileWriter = new FileWriter(USERS_FILE, true)) {
            fileWriter.write(UserConverter.toCsvString(user) + "\n");
            return user;
        } catch (Exception e) {
            Logger.getLogger().error(this.getClass(), "File write error");
            return null;
        }
    }

    /**
     * Prywatna metoda do aktualizowania istniejącego użytkownika w repozytorium.
     * @param user Użytkownik do aktualizacji.
     * @return Zaktualizowany użytkownik.
     */

    private User update(User user) {
        try (FileReader fileReader = new FileReader(USERS_FILE);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            List<User> users = bufferedReader.lines()
                    .map(UserConverter::toObject)
                    .map(storedUser -> storedUser.getId().equals(user.getId()) ? user : storedUser)
                    .toList();
            try (FileWriter fileWriter = new FileWriter(USERS_FILE)) {
                users.forEach(updatedUser -> {
                    try {
                        fileWriter.write(UserConverter.toCsvString(updatedUser) + "\n");
                    } catch (IOException e) {
                        Logger.getLogger().error(this.getClass(), "File write error");
                    }
                });
            } catch (Exception e) {
                Logger.getLogger().error(this.getClass(), "Error opening file for writing");
            }
            return user;
        } catch (Exception e) {
            Logger.getLogger().error(this.getClass(), "Problem reading file");
            return null;
        }
    }
}
