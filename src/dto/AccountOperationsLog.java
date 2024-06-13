package dto;

/**
 * Klasa reprezentująca log operacji na koncie.
 */

public class AccountOperationsLog {
    private Long id;
    private AccountOperationType type;
    private Integer amount;
    private Long sourceAccountId;
    private Long targetAccountId;

    /**
     * Konstruktor domyślny bez argumentów.
     */

    public AccountOperationsLog() {
    }

    /**
     * Konstruktor dla tworzenia nowego logu operacji.
     * @param type            Typ operacji (WITHDRAW, PUT, TRANSFER)
     * @param amount          Kwota operacji
     * @param sourceAccountId Identyfikator źródłowego konta
     * @param targetAccountId Identyfikator docelowego konta
     */

    public AccountOperationsLog(AccountOperationType type, Integer amount, Long sourceAccountId, Long targetAccountId) {
        this.type = type;
        this.amount = amount;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
    }

    /**
     * Konstruktor pełny z identyfikatorem logu operacji.
     * @param id              Identyfikator logu operacji
     * @param type            Typ operacji (WITHDRAW, PUT, TRANSFER)
     * @param amount          Kwota operacji
     * @param sourceAccountId Identyfikator źródłowego konta
     * @param targetAccountId Identyfikator docelowego konta
     */

    public AccountOperationsLog(Long id, AccountOperationType type, Integer amount, Long sourceAccountId, Long targetAccountId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
    }

    /**
     * Metoda zwracająca identyfikator logu operacji.
     * @return Identyfikator logu operacji
     */

    public Long getId() {
        return id;
    }

    /**
     * Metoda ustawiająca identyfikator logu operacji.
     * @param id Identyfikator logu operacji
     */

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metoda zwracająca typ operacji.
     * @return Typ operacji
     */

    public AccountOperationType getType() {
        return type;
    }

    /**
     * Metoda ustawiająca typ operacji.
     * @param type Typ operacji
     */

    public void setType(AccountOperationType type) {
        this.type = type;
    }

    /**
     * Metoda zwracająca kwotę operacji.
     * @return Kwota operacji
     */

    public Integer getAmount() {
        return amount;
    }

    /**
     * Metoda ustawiająca kwotę operacji.
     * @param amount Kwota operacji
     */

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Metoda zwracająca identyfikator źródłowego konta.
     * @return Identyfikator źródłowego konta
     */

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    /**
     * Metoda ustawiająca identyfikator źródłowego konta.
     * @param sourceAccountId Identyfikator źródłowego konta
     */

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    /**
     * Metoda zwracająca identyfikator docelowego konta.
     * @return Identyfikator docelowego konta
     */

    public Long getTargetAccountId() {
        return targetAccountId;
    }

    /**
     * Metoda ustawiająca identyfikator docelowego konta.
     * @param targetAccountId Identyfikator docelowego konta
     */

    public void setTargetAccountId(Long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    /**
     * Przesłonięta metoda toString generująca czytelny opis logu operacji.
     * @return Opis logu operacji
     */

    @Override
    public String toString() {
        String result = type +
                " w wysokości " + amount +
                " z konta źródłowego " + sourceAccountId
                + " na konto docelowe " + targetAccountId;
        return result;
    }
}
