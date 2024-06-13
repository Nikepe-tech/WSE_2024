package storage.impl;

import converter.AccountOperationLogConverter;
import dto.AccountOperationsLog;
import logger.Logger;
import storage.AccountOperationsLogRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementacja interfejsu repozytorium dziennika operacji na koncie.
 */

public class AccountOperationsLogRepoImpl implements AccountOperationsLogRepo {

    private final String ACC_OPS_LOG = "./resources/acc_ops_log.csv";
    private static AccountOperationsLogRepo instance;
    private AtomicLong logIdCounter;

    /**
     * Prywatny konstruktor, który inicjalizuje repozytorium.
     * Wczytuje dane z pliku dziennika operacji konta i ustawia licznik identyfikatorów logów.
     */

    private AccountOperationsLogRepoImpl() {
        try (FileReader fileReader = new FileReader(ACC_OPS_LOG);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            OptionalLong max = bufferedReader.lines()
                    .map(line -> line.split(",")[0])
                    .mapToLong(Long::parseLong)
                    .max();
            if (max.isPresent()) {
                logIdCounter = new AtomicLong(max.getAsLong());
                Logger.getLogger().debug(this.getClass(), "Created UserIdCounter with initial value: %d".formatted(logIdCounter.get()));
            } else {
                logIdCounter = new AtomicLong();
                Logger.getLogger().debug(this.getClass(), "Created empty UserIdCounter started with 0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda do uzyskania instancji repozytorium.
     * @return Instancja repozytorium dziennika operacji na koncie.
     */

    public static AccountOperationsLogRepo getInstance() {
        if (instance == null) {
            instance = new AccountOperationsLogRepoImpl();
        }
        return instance;
    }

    /**
     * Metoda do zapisywania nowego wpisu dziennika operacji.
     * @param log Wpis dziennika operacji do zapisania.
     * @return Zapisany wpis dziennika operacji.
     */

    @Override
    public AccountOperationsLog save(AccountOperationsLog log) {
        long newId = logIdCounter.incrementAndGet();
        log.setId(newId);
        try (FileWriter fileWriter = new FileWriter(ACC_OPS_LOG, true)) {
            fileWriter.write(AccountOperationLogConverter.toCsvString(log) + "\n");
            return log;
        } catch (Exception e) {
            Logger.getLogger().error(this.getClass(), "File write error");
            return null;
        }
    }

    /**
     * Metoda do znajdowania wszystkich wpisów dziennika operacji dla danego źródłowego identyfikatora konta.
     * @param sourceAccId Identyfikator źródłowego konta, dla którego pobierane są wpisy dziennika operacji.
     * @return Lista wpisów dziennika operacji dla danego źródłowego identyfikatora konta.
     */

    @Override
    public List<AccountOperationsLog> findAllBySourceAccId(Long sourceAccId) {
        try (FileReader fileReader = new FileReader(ACC_OPS_LOG);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            return bufferedReader.lines()
                    .filter(line -> line.split(",")[3].equals(sourceAccId.toString()))
                    .map(AccountOperationLogConverter::toObject)
                    .toList();
        } catch (Exception e) {
            Logger.getLogger().error(this.getClass(), "Problem reading file");
            return Collections.emptyList();
        }
    }
}
