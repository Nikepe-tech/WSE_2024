package service;

import dto.AccountOperationType;
import dto.AccountOperationsLog;
import logger.Logger;
import storage.AccountOperationsLogRepo;
import storage.impl.AccountOperationsLogRepoImpl;

import java.util.List;

/**
 * Klasa serwisowa obsługująca operacje na logach operacji konta.
 */

public class AccountOperationsLogService {
    private static AccountOperationsLogService instance;

    private static final Logger logger = Logger.getLogger();
    private final AccountOperationsLogRepo logRepo = AccountOperationsLogRepoImpl.getInstance();

    /**
     * Prywatny konstruktor, który jest wywoływany tylko raz dla uzyskania instancji serwisu.
     */

    private AccountOperationsLogService() {}

    /**
     * Metoda do uzyskania instancji serwisu (singleton).
     * @return Instancja serwisu logów operacji konta.
     */

    public static AccountOperationsLogService getInstance() {
        if (instance == null) {
            instance = new AccountOperationsLogService();
        }
        return instance;
    }

    /**
     * Metoda do rejestrowania operacji na koncie.
     * @param sourceAccId Identyfikator źródłowego konta.
     * @param amount Kwota operacji.
     * @param targetAccountId Identyfikator docelowego konta (jeśli dotyczy).
     * @param type Typ operacji.
     */

    public void logAccOperation(final Long sourceAccId, final Integer amount,
                                final Long targetAccountId, final AccountOperationType type) {
        logRepo.save(new AccountOperationsLog(type, amount, sourceAccId, targetAccountId));
    }

    /**
     * Metoda do pobierania logów operacji na koncie na podstawie identyfikatora konta.
     * @param accountId Identyfikator konta.
     * @return Lista logów operacji na koncie.
     */

    public List<AccountOperationsLog> logByAccountId(final Long accountId) {
        return logRepo.findAllBySourceAccId(accountId);
    }
}
