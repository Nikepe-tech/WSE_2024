package service;

import dto.Account;
import dto.User;
import logger.Logger;
import storage.AccountRepo;
import storage.impl.AccountRepoImpl;

import java.util.Optional;

import static dto.AccountOperationType.*;

/**
 * Serwis obsługujący operacje na kontach użytkowników.
 */

public class AccountService {

    private static AccountService instance;
    private static final Logger logger = Logger.getLogger();
    private final AccountRepo accountRepo = AccountRepoImpl.getInstance();
    private final AccountOperationsLogService logService = AccountOperationsLogService.getInstance();

    /**
     * Metoda do uzyskania instancji serwisu (singleton).
     * @return Instancja serwisu kont.
     */

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

        /**
        * Metoda do tworzenia nowego konta dla użytkownika.
        * @param user Użytkownik, dla którego tworzone jest konto.
        */

        public void createAccount (final User user){
            Account account = accountRepo.save(new Account(0, user.getId()));
            user.setAccount(account);
            logger.info(this.getClass(), "Utworzono konto dla użytkownika %d".formatted(user.getId()));
        }

        /**
        * Metoda do wypłaty środków z konta użytkownika.
        * @param user Użytkownik, któremu wypłacane są środki.
        * @param requestedAmount Żądana kwota do wypłaty.
        * @return true, jeśli wypłata zakończyła się powodzeniem, w przeciwnym razie false.
        */

        public boolean withdraw ( final User user, final int requestedAmount){
            if (user.getAccount().getAmount() < requestedAmount) {
                logger.info(this.getClass(), "Niewystarczające środki u użytkownika %d".formatted(user.getId()));
                return false;
            }
            Integer actualAmount = user.getAccount().getAmount();
            user.getAccount().setAmount(actualAmount - requestedAmount);
            accountRepo.save(user.getAccount());
            logService.logAccOperation(user.getAccount().getId(), requestedAmount, null , WITHDRAW);
            logger.info(this.getClass(), "Wypłacono %d z konta %d użytkownika %d".formatted(requestedAmount, user.getAccount().getId(), user.getId()));
            return true;
        }

        /**
        * Metoda do wpłaty środków na konto użytkownika.
        * @param user Użytkownik, na którego konto wpłacane są środki.
        * @param putAmount Kwota do wpłacenia.
        * @return true, jeśli wpłata zakończyła się powodzeniem, w przeciwnym razie false.
        */

        public boolean put ( final User user, final int putAmount){
            if (Integer.toUnsignedLong(user.getAccount().getAmount() + putAmount) >= Integer.MAX_VALUE) {
                return false;
            }

            Integer actualAmount = user.getAccount().getAmount();
            user.getAccount().setAmount(actualAmount + putAmount);
            accountRepo.save(user.getAccount());
            logService.logAccOperation(user.getAccount().getId(), putAmount, null , PUT);
            logger.info(this.getClass(), "Wpłacono %d na konto %d użytkownika %d".formatted(putAmount, user.getAccount().getId(), user.getId()));
            return true;
        }

        /**
        * Metoda do transferu środków с одного счета пользователя на другой.
        * @param user Użytkownik, с чьего счета производится трансфер.
        * @param targetAccNumber Номер счета, на который переводятся средства.
        * @param requestedAmount Запрошенная сумма для перевода.
        * @return true, если трансфер выполнен успешно, в противном случае false.
        */

        public boolean transfer ( final User user, final Long targetAccNumber, final int requestedAmount){
        Optional<Account> sourceAccount = accountRepo.findById(user.getAccount().getId());
            Optional<Account> targetAccount = accountRepo.findById(targetAccNumber);
            if (targetAccount.isEmpty() || sourceAccount.isEmpty()){
                return false;
            }
            if (sourceAccount.get().getAmount() < requestedAmount) {
                return false;
            }
            if ((targetAccount.get().getAmount() + requestedAmount) >= Integer.MAX_VALUE) {
                return false;
            }

            Integer actualSourceAmount = sourceAccount.get().getAmount();
            sourceAccount.get().setAmount(actualSourceAmount - requestedAmount);

            Integer actualTargetAmount = targetAccount.get().getAmount();
            targetAccount.get().setAmount(actualTargetAmount + requestedAmount);

            user.setAccount(sourceAccount.get());

            accountRepo.save(sourceAccount.get());
            accountRepo.save(targetAccount.get());
            logService.logAccOperation(user.getAccount().getId(), requestedAmount, targetAccNumber, TRANSFER);
            return true;
        }
    }
