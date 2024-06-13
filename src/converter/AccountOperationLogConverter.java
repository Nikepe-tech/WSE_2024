package converter;

import dto.AccountOperationType;
import dto.AccountOperationsLog;

/**
 * Klasa narzędziowa do konwersji obiektów typu {@link AccountOperationsLog} na ciągi CSV
 * i z powrotem oraz do tworzenia obiektów {@link AccountOperationsLog} na podstawie ciągów CSV.
 */

public class AccountOperationLogConverter {

    private AccountOperationLogConverter() {
    }

    /**
     * Konwertuje obiekt {@link AccountOperationsLog} na ciąg tekstowy w formacie CSV.
     * @param log Obiekt {@link AccountOperationsLog} do konwersji.
     * @return Ciąg sformatowany jako CSV reprezentujący operację na koncie.
     */

    public static String toCsvString(final AccountOperationsLog log) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(log.getId()).append(",");
        stringBuilder.append(log.getType()).append(",");
        stringBuilder.append(log.getAmount()).append(",");
        stringBuilder.append(log.getSourceAccountId()).append(",");
        if (log.getTargetAccountId() != null) {
            stringBuilder.append(log.getTargetAccountId());
        }
        return stringBuilder.toString();
    }

    /**
     * Konwertuje ciąg tekstowy w formacie CSV na obiekt {@link AccountOperationsLog}.
     * @param csvString Ciąg sformatowany jako CSV.
     * @return Obiekt {@link AccountOperationsLog} utworzony na podstawie ciągu CSV.
     */

    public static AccountOperationsLog toObject(final String csvString) {
        String[] strings = csvString.split(",");
        int i = 0;

        AccountOperationsLog log = new AccountOperationsLog();
                log.setId(Long.parseLong(strings[i++]));
                log.setType(AccountOperationType.valueOf(strings[i++]));
                log.setAmount(Integer.parseInt(strings[i++]));
                log.setSourceAccountId(Long.parseLong(strings[i++]));
        if ((strings.length - 1) > i) {
            log.setTargetAccountId(Long.parseLong(strings[i]));
        }

        return log;
    }
}
