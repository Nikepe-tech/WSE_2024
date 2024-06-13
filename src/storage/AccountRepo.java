package storage;

import dto.Account;

import java.util.Optional;

/**
 * Interfejs do obsługi repozytorium kont.
 */

public interface AccountRepo {

    /**
     * Metoda do zapisywania konta.
     * @param account Konto do zapisania.
     * @return Zapisane konto.
     */

    Account save(Account account);

    /**
     * Metoda do znajdowania konta po jego identyfikatorze.
     * @param id Identyfikator konta do znalezienia.
     * @return Optional zawierający znalezione konto, jeśli istnieje.
     */

    Optional<Account> findById(final Long id);

    /**
     * Metoda do znajdowania konta po identyfikatorze użytkownika.
     * @param userId Identyfikator użytkownika, którego konto należy znaleźć.
     * @return Optional zawierający znalezione konto użytkownika, jeśli istnieje.
     */

    Optional<Account> findByUserId(final Long userId);
}
