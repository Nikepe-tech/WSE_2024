package dto;

/**
 * Klasa reprezentująca konto użytkownika.
 */

public class Account {

    private Long id;
    private Integer amount;
    private Long userId;

    public Account() {
    }

    /**
     * Konstruktor dla tworzenia nowego konta.
     * @param amount Początkowa kwota na koncie.
     * @param userId Identyfikator użytkownika powiązanego z kontem.
     */

    public Account(Integer amount, Long userId) {
        this.amount = amount;
        this.userId = userId;
    }

    /**
     * Konstruktor pełny z identyfikatorem.
     * @param id     Identyfikator konta.
     * @param amount Aktualna kwota na koncie.
     * @param userId Identyfikator użytkownika powiązanego z kontem.
     */

    public Account(Long id, Integer amount, Long userId) {
        this.id = id;
        this.amount = amount;
        this.userId = userId;
    }

    /**
     * Metoda zwracająca identyfikator konta.
     * @return Identyfikator konta.
     */

    public Long getId() {
        return id;
    }

    /**
     * Metoda ustawiająca identyfikator konta.
     * @param id Identyfikator konta.
     */

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metoda zwracająca aktualną kwotę na koncie.
     * @return Aktualna kwota na koncie.
     */

    public Integer getAmount() {
        return amount;
    }

    /**
     * Metoda ustawiająca aktualną kwotę na koncie.
     * @param amount Aktualna kwota na koncie.
     */

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Metoda zwracająca identyfikator użytkownika powiązanego z kontem.
     * @return Identyfikator użytkownika.
     */

    public Long getUserId() {
        return userId;
    }

    /**
     * Metoda ustawiająca identyfikator użytkownika powiązanego z kontem.
     * @param userId Identyfikator użytkownika.
     */

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
