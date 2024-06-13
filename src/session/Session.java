package session;

import dto.User;
import logger.Logger;

/**
 * Klasa reprezentująca sesję użytkownika w systemie.
 */

public class Session {

    private User user;

    private static Session instance;
    private static final Logger log = Logger.getLogger();

    /**
     * Prywatny konstruktor, który jest wywoływany tylko raz dla uzyskania instancji sesji.
     */

    private Session() {
    }

    /**
     * Metoda do uzyskania instancji sesji (singleton).
     * @return Instancja sesji.
     */

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * Metoda do tworzenia nowej sesji dla określonego użytkownika.
     * @param user Użytkownik, dla którego tworzona jest sesja.
     */

    public void createSession(final User user) {
        log.info(this.getClass(), "Создана сессия с %s".formatted(user.getUserName()));
        this.user = user;
    }

    /**
     * Metoda do pobierania aktualnie zalogowanego użytkownika.
     * @return Zalogowany użytkownik.
     */

    public User getUser() {
        return this.user;
    }
}
