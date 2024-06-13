package storage.impl;

import converter.AccountConverter;
import dto.Account;
import logger.Logger;
import storage.AccountRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementacja interfejsu repozytorium kont.
 */

public class AccountRepoImpl implements AccountRepo {

    private final String ACCOUNTS_FILE = "./resources/accounts.csv";
    private static AccountRepo instance;
    private AtomicLong accountIdCounter;

    /**
     * Prywatny konstruktor, który inicjalizuje repozytorium.
     * Wczytuje dane z pliku kont i ustawia licznik identyfikatorów kont.
     */

    private AccountRepoImpl() {
        try (FileReader fileReader = new FileReader(ACCOUNTS_FILE);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            OptionalLong max = bufferedReader.lines()
                    .map(line -> line.split(",")[0])
                    .mapToLong(Long::parseLong)
                    .max();
            if (max.isPresent()) {
                accountIdCounter = new AtomicLong(max.getAsLong());
                Logger.getLogger().debug(this.getClass(), "Created UserIdCounter with initial value: %d".formatted(accountIdCounter.get()));
            } else {
                accountIdCounter = new AtomicLong();
                Logger.getLogger().debug(this.getClass(), "Created empty UserIdCounter started with 0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda do uzyskania instancji repozytorium.
     * @return Instancja repozytorium kont.
     */

    public static AccountRepo getInstance() {
        if (instance == null) {
            instance = new AccountRepoImpl();
        }
        return instance;
    }

    /**
     * Metoda do zapisywania lub aktualizowania konta w repozytorium.
     * Jeśli konto nie ma przypisanego identyfikatora (jest nowe), zostaje dodane do repozytorium.
     * Jeśli konto ma przypisany identyfikator, zostaje zaktualizowane w repozytorium.
     * @param account Konto do zapisania lub zaktualizowania.
     * @return Zapisane lub zaktualizowane konto.
     */

    @Override
    public Account save(Account account) {
        if (account.getId() == null) {
            return insert(account);
        } else {
            return update(account);
        }
    }

    /**
     * Metoda do znajdowania konta po jego identyfikatorze.
     * @param id Identyfikator konta do znalezienia.
     * @return Optional zawierający znalezione konto, jeśli istnieje.
     */

    @Override
    public Optional<Account> findById(Long id) {
        try (FileReader fileReader = new FileReader(ACCOUNTS_FILE);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            return bufferedReader.lines()
                    .filter(line -> line.split(",")[0].equals(id.toString()))
                    .map(AccountConverter::toObject)
                    .findFirst();
        } catch (Exception e) {
            Logger.getLogger().error(this.getClass(), "Problem reading file");
            return Optional.empty();
        }
    }

    /**
     * Metoda do znajdowania konta po identyfikatorze użytkownika.
     * @param userId Identyfikator użytkownika, dla którego znaleziono konto.
     * @return Optional zawierający znalezione konto użytkownika, jeśli istnieje.
     */

    @Override
    public Optional<Account> findByUserId(Long userId) {
        try (FileReader fileReader = new FileReader(ACCOUNTS_FILE);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            return bufferedReader.lines()
                    .filter(line -> line.split(",")[2].equals(userId.toString()))
                    .map(AccountConverter::toObject)
                    .findFirst();
        } catch (Exception e) {
            Logger.getLogger().error(this.getClass(), "Problem reading file");
            return Optional.empty();
        }
    }

    /**
     * Prywatna metoda do dodawania nowego konta do repozytorium.
     * @param account Konto do dodania.
     * @return Dodane konto.
     */

    private Account insert(Account account) {
        long newId = accountIdCounter.incrementAndGet();
        account.setId(newId);
        try (FileWriter fileWriter = new FileWriter(ACCOUNTS_FILE, true)) {
            fileWriter.write(AccountConverter.toCsvString(account) + "\n");
            return account;
        } catch (Exception e) {
            Logger.getLogger().error(this.getClass(), "File write error");
            return null;
        }
    }

    /**
     * Prywatna metoda do aktualizowania istniejącego konta w repozytorium.
     * @param account Konto do aktualizacji.
     * @return Zaktualizowane konto.
     */

    private Account update(Account account) {
        try (FileReader fileReader = new FileReader(ACCOUNTS_FILE);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            List<Account> accounts = bufferedReader.lines()
                    .map(AccountConverter::toObject)
                    .map(storedAccount -> storedAccount.getId().equals(account.getId()) ? account : storedAccount)
                    .toList();
            try (FileWriter fileWriter = new FileWriter(ACCOUNTS_FILE)) {
                accounts.forEach(updatedAccount -> {
                    try {
                        fileWriter.write(AccountConverter.toCsvString(updatedAccount) + "\n");
                    } catch (IOException e) {
                        Logger.getLogger().error(this.getClass(), "File write error");
                    }
                });
            } catch (Exception e) {
                Logger.getLogger().error(this.getClass(), "Error opening file for writing");
            }
            return account;
        } catch (Exception e) {
            Logger.getLogger().error(this.getClass(), "Problem reading file");
            return null;
        }
    }
}
