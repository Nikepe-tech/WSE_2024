package logger;

import java.io.FileWriter;
import java.time.LocalDateTime;

/**
 * Klasa Logger służy do logowania różnych zdarzeń w aplikacji do pliku tekstowego.
 */

public class Logger {
    private static Logger logger;

    /**
     * Prywatny konstruktor Loggera, aby zapobiec bezpośredniemu tworzeniu instancji.
     */

    public Logger() {
    }

    /**
     * Metoda logująca zdarzenie do pliku tekstowego.
     *
     * @param clazz   Klasa, z której zostało wywołane zdarzenie.
     * @param message Wiadomość do zapisania w logu.
     * @param logLevel Poziom logowania (np. INFO, DEBUG, ERROR itp.).
     */

    private void log(final Class clazz, final String message, final LogLevel logLevel) {
        try (FileWriter fileWriter = new FileWriter("./resources/log.txt", true)) {
            LocalDateTime ldt = LocalDateTime.now();
            fileWriter.write("[" + logLevel + "] [" + clazz.getName() + "] ["+ ldt + "] " + message + '\n');
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda do logowania zdarzenia o poziomie FATAL.
     * @param clazz   Klasa, z której zostało wywołane zdarzenie.
     * @param message Wiadomość do zapisania w logu.
     */

    public void fatal(Class clazz, String message) {
        log(clazz, message, LogLevel.FATAL);
    }

    /**
     * Metoda do logowania zdarzenia o poziomie ERROR.
     * @param clazz   Klasa, z której zostało wywołane zdarzenie.
     * @param message Wiadomość do zapisania w logu.
     */

    public void error(Class clazz, String message) {
        log(clazz, message, LogLevel.ERROR);
    }

    /**
     * Metoda do logowania zdarzenia o poziomie WARN.
     * @param clazz   Klasa, z której zostało wywołane zdarzenie.
     * @param message Wiadomość do zapisania w logu.
     */

    public void warn(Class clazz, String message) {
        log(clazz, message, LogLevel.WARN);
    }

    /**
     * Metoda do logowania zdarzenia o poziomie INFO.
     * @param clazz   Klasa, z której zostało wywołane zdarzenie.
     * @param message Wiadomość do zapisania w logu.
     */

    public void info(Class clazz, String message) {
        log(clazz, message, LogLevel.INFO);
    }

    /**
     * Metoda do logowania zdarzenia o poziomie DEBUG.
     * @param clazz   Klasa, z której zostało wywołane zdarzenie.
     * @param message Wiadomość do zapisania w logu.
     */

    public void debug(Class clazz, String message) {
        log(clazz, message, LogLevel.DEBUG);
    }

    /**
     * Metoda do uzyskania instancji loggera (singleton).
     * @return Instancja Loggera.
     */

    public static Logger getLogger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }
}
