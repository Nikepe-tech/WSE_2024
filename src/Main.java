import exception.ExitAppException;
import ui.UI;

/**
 * Główna klasa aplikacji.
 */

public class Main {

    /**
     * Metoda główna, która uruchamia aplikację.
     */

    public static void main(String[] args) {

        UI ui = new UI();

        try {
            ui.loginLoop();
            ui.mainLoop();
        } catch (ExitAppException e) {
            System.out.println("Praca zakończona");
        }
    }
}

