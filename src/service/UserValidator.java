package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Klasa odpowiedzialna za walidację danych użytkownika na podstawie określonych reguł.
 */

public class UserValidator {

    private static UserValidator instance;

    private UserValidator() {
    }

    /**
     * Metoda do uzyskania instancji walidatora (singleton).
     * @return Instancja UserValidator.
     */

    public static UserValidator getInstance() {
        if (instance == null) {
            instance = new UserValidator();
        }
        return instance;
    }

    /**
     * Metoda przeprowadzająca walidację żądania walidacji danych użytkownika.
     * @param validationRequest Obiekt UserValidationRequest zawierający dane do walidacji.
     * @return Wynik walidacji jako obiekt UserValidationResult.
     */

    public UserValidationResult validate(final UserValidationRequest validationRequest) {
        String userNameRegexp = "^\\w{4,8}$";
        Pattern userNamePattern = Pattern.compile(userNameRegexp);
        String userNameInvalid = "Nieprawidłowa nazwa użytkownika. Musi być jednym słowem z liter i cyfr angielskich o długości od 4 do 8 znaków";

        String passwordRegexp = "^[0-9]{6,10}$";
        Pattern passwordPattern = Pattern.compile(passwordRegexp);
        String passwordInvalid = "Nieprawidłowe hasło. Musi składać się z cyfr o długości od 6 do 10 znaków";

        String nameRegexp = "^[a-zA-Z]+$";
        Pattern namePattern = Pattern.compile(nameRegexp);
        String nameInvalid = "Nieprawidłowe imię lub nazwisko. Musi być jednym słowem z liter angielskich dowolnej wielkości, minimum 1 znak";

        String birthDateInvalid = "Nieprawidłowa data urodzenia. Musi być w formacie YYYY-MM-DD, na przykład 2022-12-24";

        String sexRegexp = "^[M,W]$";
        Pattern sexPattern = Pattern.compile(sexRegexp);
        String sexInvalid = "Nieprawidłowa płeć. Dozwolone wartości to M lub W";

        String emailRegexp = "^\\w+@\\w+\\.\\w+$";
        Pattern emailPattern = Pattern.compile(emailRegexp);
        String emailInvalid = "Nieprawidłowy adres email. Musi zawierać \"@\" i \".\" oraz literki pomiędzy nimi";

        UserValidationResult result = new UserValidationResult();

        // Walidacja poszczególnych pól

        if (!userNamePattern.matcher(validationRequest.userName()).matches()) {
            result.addError(userNameInvalid);
        }
        if (!passwordPattern.matcher(validationRequest.password()).matches()) {
            result.addError(passwordInvalid);
        }
        if (!namePattern.matcher(validationRequest.firstName()).matches()) {
            result.addError(nameInvalid);
        }
        if (!namePattern.matcher(validationRequest.lastName()).matches()) {
            result.addError(nameInvalid);
        }

        // Sprawdzenie poprawności daty urodzenia

        try {
            LocalDate.parse(validationRequest.birthDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            result.addError(birthDateInvalid);
        }

        if (!sexPattern.matcher(validationRequest.sex()).matches()) {
            result.addError(sexInvalid);
        }
        if (!emailPattern.matcher(validationRequest.email()).matches()) {
            result.addError(emailInvalid);
        }
        return result;
    }
}
