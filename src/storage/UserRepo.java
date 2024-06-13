package storage;

import dto.User;

import java.util.Optional;

/**
 * Interfejs do obsługi repozytorium użytkowników.
 */

public interface UserRepo {

    /**
     * Metoda do znajdowania użytkownika po nazwie użytkownika.
     * @param userName Nazwa użytkownika do wyszukania.
     * @return Optional zawierający znalezionego użytkownika, jeśli istnieje.
     */

    Optional<User> findByUserName(final String userName);

    /**
     * Metoda do zapisywania użytkownika.
     * @param user Użytkownik do zapisania.
     * @return Zapisany użytkownik.
     */

    User save(User user);
}
