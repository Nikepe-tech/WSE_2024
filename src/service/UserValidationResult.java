package service;

/**
 * Klasa reprezentująca wynik walidacji danych użytkownika.
 */

public class UserValidationResult {

    private final StringBuilder validationMessage = new StringBuilder();
    private boolean isSuccess = true;

    /**
     * Metoda dodająca komunikat błędu do wyniku walidacji.
     * @param errorMassage Komunikat błędu do dodania.
     */

    public void addError(final String errorMassage) {
        validationMessage.append(errorMassage).append("\n");
        isSuccess = false;
    }

    /**
     * Metoda zwracająca wszystkie zgromadzone komunikaty walidacyjne jako String.
     * @return Komunikaty walidacyjne.
     */

    public String getValidationMessage() {
        return validationMessage.toString();
    }

    /**
     * Metoda zwracająca informację, czy walidacja zakończyła się sukcesem.
     * @return true, jeśli walidacja zakończyła się sukcesem, false w przeciwnym razie.
     */

    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * Metoda ustawiająca flagę isSuccess, określającą stan walidacji.
     * @param success true, jeśli walidacja zakończyła się sukcesem, false w przeciwnym razie.
     */

    public void setSuccess(final  boolean success) {
        isSuccess = success;
    }
}
