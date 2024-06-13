package storage;

import dto.AccountOperationsLog;

import java.util.List;

/**
 * Interfejs do obsługi repozytorium dziennika operacji na koncie.
 */

public interface AccountOperationsLogRepo {

    /**
     * Metoda do zapisywania nowego wpisu dziennika operacji.
     * @param log Wpis dziennika operacji do zapisania.
     * @return Zapisany wpis dziennika operacji.
     */

    AccountOperationsLog save(AccountOperationsLog log);

    /**
     * Metoda do pobierania wszystkich wpisów dziennika operacji dla danego źródłowego identyfikatora konta.
     * @param sourceAccId Identyfikator źródłowego konta, dla którego pobierane są wpisy dziennika operacji.
     * @return Lista wpisów dziennika operacji dla danego źródłowego identyfikatora konta.
     */

    List<AccountOperationsLog> findAllBySourceAccId(final Long sourceAccId);
}
