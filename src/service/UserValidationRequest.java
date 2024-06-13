package service;

// Klasa reprezentująca żądanie walidacji danych użytkownika.
// Zapis (record) jest używany tutaj do definiowania niezmiennej struktury danych,
// która przechowuje informacje potrzebne do walidacji nowego użytkownika.

public record UserValidationRequest(String userName, String password, String firstName,
                                    String lastName, String birthDate, String sex, String email) {
}
