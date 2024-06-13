package converter;

import dto.Account;

public class AccountConverter {

    private AccountConverter() {
    }

    /**
     * Konwertuje obiekt {@link Account} na ciąg sformatowany jako CSV.
     * @param account Obiekt {@link Account} do konwersji.
     * @return Ciąg sformatowany jako CSV reprezentujący konto.
     */

    public static String toCsvString(final Account account) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(account.getId()).append(",");
        stringBuilder.append(account.getAmount()).append(",");
        stringBuilder.append(account.getUserId());
        return stringBuilder.toString();
    }

    /**
     * Konwertuje ciąg sformatowany jako CSV na obiekt {@link Account}.
     * @param csvString Ciąg sformatowany jako CSV.
     * @return Obiekt {@link Account} utworzony na podstawie ciągu CSV.
     */

    public static Account toObject(final String csvString) {
        String[] strings = csvString.split(",");
        int i = 0;

        Account account = new Account(
                Long.parseLong(strings[i++]),
                Integer.parseInt(strings[i++]),
                Long.parseLong(strings[i++])
        );

        return account;
    }
}
