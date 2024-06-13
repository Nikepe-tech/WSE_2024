package ui;

import dto.Account;
import dto.User;
import exception.BackToPreviousMenuException;
import exception.ExitAppException;
import logger.Logger;
import service.*;
import session.Session;

import java.util.Scanner;

public class UI {

    /**
     * Klasa obsługująca interfejs użytkownika (UI) dla aplikacji bankowej.
     */

    private final Session session = Session.getInstance();
    private final UserService userService = UserService.getInstance();
    private final AccountService accountService = AccountService.getInstance();
    private final AccountOperationsLogService logService = AccountOperationsLogService.getInstance();
    private final Scanner scanner = new Scanner(System.in);
    private final Logger log = Logger.getLogger();

    /**
     * Metoda obsługująca pętlę logowania użytkownika.
     */

    public void loginLoop() {
        while (true) {
            System.out.println("Witaj w systemie. Zaloguj się lub zarejestruj");
            System.out.println("1 Zaloguj się");
            System.out.println("2 Zarejestruj się");
            System.out.println("0 Zakończ program");

            int loginChoose = scanner.nextInt();
            switch (loginChoose) {
                case 1: {
                    System.out.println("Podaj login");
                    String userName = scanner.next();
                    System.out.println("Podaj hasło");
                    String password = scanner.next();
                    User loginResult = userService.checkLogin(userName, password);
                    if (loginResult == null) {
                        System.out.println("Nieprawidłowe dane, spróbuj ponownie");
                        log.info(this.getClass(), "Podano nieprawidłowe dane logowania");
                        break;
                    } else {
                        System.out.println("Witaj w systemie, %s".formatted(loginResult.getUserName()));
                        session.createSession(loginResult);
                        log.info(this.getClass(), "Zalogowano pomyślnie");
                        return;
                    }
                }
                case 2: {
                    System.out.println("Podaj login");
                    String userName = scanner.next();
                    System.out.println("Podaj hasło");
                    String password = scanner.next();
                    System.out.println("Podaj imię");
                    String firstName = scanner.next();
                    System.out.println("Podaj nazwisko");
                    String lastName = scanner.next();
                    System.out.println("Podaj datę urodzenia");
                    String birthDate = scanner.next();
                    System.out.println("Podaj płeć");
                    String sex = scanner.next();
                    System.out.println("Podaj email");
                    String email = scanner.next();

                    UserValidationRequest request = new UserValidationRequest(userName, password, firstName,
                            lastName, birthDate, sex, email);
                    UserValidationResult result = userService.validate(request);

                    if (result.isSuccess()) {
                        User user = userService.create(request);
                        session.createSession(user);
                        System.out.printf("Witaj w systemie, %s \n", user.getFirstName());
                        return;
                    } else {
                        System.out.println("Błąd podczas tworzenia użytkownika. \n" + result.getValidationMessage());
                        continue;
                    }
                }
                case 0:
                    throw new ExitAppException();
            }
        }
    }

    /**
     * Metoda obsługująca główną pętlę interfejsu użytkownika.
     */

    public void mainLoop() {
        while (true) {
            System.out.println("1 Operacje na koncie");
            System.out.println("2 Historia operacji na koncie");
            System.out.println("0 Zakończ program");

            int mainChoose = scanner.nextInt();
            switch (mainChoose) {
                case 1: {
                    try {
                        accountOperationsLoop();
                    } catch (BackToPreviousMenuException e) {
                        break;
                    }
                }
                case 2: {
                    try {
                    accountOpsLogLoop();
                } catch (BackToPreviousMenuException e) {
                    break;
                }
            }
                case 0:
                    throw new ExitAppException();
            }
        }
    }

    /**
     * Metoda obsługująca pętlę dla operacji na historii operacji konta.
     */

    private void accountOpsLogLoop() {
        while (true) {
            System.out.println("1 Wyświetl historię operacji");
            System.out.println("2 Pobierz historię operacji");
            System.out.println("3 Załaduj operacje");
            System.out.println("0 Powrót do poprzedniego menu");

            int accountOpsLogChoose = scanner.nextInt();
            switch (accountOpsLogChoose) {
                case 1: {
                    logService.logByAccountId(session.getUser().getAccount().getId())
                            .forEach(System.out::println);
                        break;
                }
                case 2: {
                    try {
                        accountOpsLogLoop();
                    } catch (BackToPreviousMenuException e) {
                        break;
                    }
                }
                case 3:
                case 0:
                    throw new BackToPreviousMenuException();
            }
        }
    }

    /**
     * Metoda obsługująca pętlę operacji na koncie.
     */

    private void accountOperationsLoop() {
        while (true) {
            System.out.println("1 Sprawdź saldo");
            System.out.println("2 Wypłać pieniądze");
            System.out.println("3 Wpłać pieniądze");
            System.out.println("4 Przelej na numer konta");
            System.out.println("5 Utwórz konto");
            System.out.println("0 Powrót do poprzedniego menu");

            int accountOpsChoose = scanner.nextInt();
            switch (accountOpsChoose) {
                case 1: {
                    Account account = session.getUser().getAccount();
                    if (account == null) {
                        System.out.println("Brak konta. Utwórz konto\n");
                        break;
                    }
                    System.out.printf("Saldo: %d%n", account.getAmount());
                    System.out.println();
                    break;
                    }
                case 2: {
                    Account account = session.getUser().getAccount();
                    if (account == null) {
                        System.out.println("Brak konta. Utwórz konto\n");
                        break;
                    }

                    System.out.println("Podaj kwotę do wypłacenia");
                    int requestedAmount = scanner.nextInt();
                    boolean withdrawResult = accountService.withdraw(session.getUser(), requestedAmount);
                    if (withdrawResult) {
                        System.out.println("Pobierz pieniądze\n");
                    } else {
                        System.out.println("Niewystarczające środki\n");
                    }
                    break;
                }
                case 3: {
                    Account account = session.getUser().getAccount();
                    if (account == null) {
                        System.out.println("Brak konta. Stwórz konto\n");
                        break;
                    }
                    System.out.println("Podaj kwotę");
                    int putAmount = scanner.nextInt();
                    boolean putResult = accountService.put(session.getUser(), putAmount);
                    if (putResult) {
                        System.out.println("Sukces\n");
                    } else {
                        System.out.println("Za dużo pieniędzy. Skarbówka powiadomiona\n");
                    }
                    break;
                }
                case 4: {
                    Account account = session.getUser().getAccount();
                    if (account == null) {
                        System.out.println("Brak konta. Stwórz konto\n");
                        break;
                    }
                    System.out.println("Podaj numer docelowego konta");
                    long targetAccNumber = scanner.nextLong();
                    System.out.println("Podaj kwotę przelewu");
                    int requestedAmount = scanner.nextInt();
                    boolean transferResult = accountService.transfer(session.getUser(), targetAccNumber, requestedAmount);
                    if (transferResult) {
                        System.out.println("Sukces\n");
                    } else {
                        System.out.println("Niewystarczające środki lub podano nieprawidłowe konto docelowe\n");
                    }
                    break;
                }
                case 5: {
                    if (session.getUser().getAccount() != null) {
                        System.out.println("Konto już istnieje\n");
                        break;
                    }
                    accountService.createAccount(session.getUser());
                    System.out.println("Konto utworzone\n");
                    break;
                }
                case 0:
                    throw new BackToPreviousMenuException();
            }
        }
    }
}
