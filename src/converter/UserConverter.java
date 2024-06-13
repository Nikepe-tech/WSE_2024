package converter;

import dto.Sex;
import dto.User;
import service.UserValidationRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Klasa narzędziowa do konwersji obiektów typu {@link User} na różne formaty danych
 * oraz do tworzenia obiektów {@link User} na podstawie tych formatów.
 */

public class UserConverter {

    private UserConverter() {
    }

    /**
     * Konwertuje obiekt {@link User} na ciąg tekstowy w formacie CSV.
     * @param user Obiekt {@link User} do konwersji.
     * @return Ciąg sformatowany jako CSV reprezentujący użytkownika.
     */

    public static String toCsvString(final User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(user.getId()).append(",");
        stringBuilder.append(user.getUserName()).append(",");
        stringBuilder.append(user.getPassword()).append(",");
        stringBuilder.append(user.getFirstName()).append(",");
        stringBuilder.append(user.getLastName()).append(",");
        stringBuilder.append(user.getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE)).append(",");
        stringBuilder.append(user.getSex()).append(",");
        stringBuilder.append(user.getEmail());
        return stringBuilder.toString();
    }

    /**
     * Konwertuje ciąg tekstowy w formacie CSV na obiekt {@link User}.
     * @param csvString Ciąg sformatowany jako CSV.
     * @return Obiekt {@link User} utworzony na podstawie ciągu CSV.
     */

    public static User toObject(final String csvString) {
        String[] strings = csvString.split(",");
        int i = 0;

        User user = new User(
                Long.parseLong(strings[i++]),
                strings[i++],
                strings[i++],
                strings[i++],
                strings[i++],
                LocalDate.parse(strings[i++], DateTimeFormatter.ISO_LOCAL_DATE),
                Sex.valueOf(strings[i++]),
                strings[i++]);

        return user;
    }

    /**
     * Konwertuje obiekt {@link UserValidationRequest} na obiekt {@link User}.
     * @param request Obiekt {@link UserValidationRequest} do konwersji.
     * @return Obiekt {@link User} utworzony na podstawie obiektu {@link UserValidationRequest}.
     */

    public static User toObject(final UserValidationRequest request) {
        User user = new User();
        user.setUserName(request.userName());
        user.setPassword(request.password());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setBirthDate(LocalDate.parse(request.birthDate(), DateTimeFormatter.ISO_LOCAL_DATE));
        user.setSex(Sex.valueOf(request.sex()));
        user.setEmail(request.email());

        return user;
    }
}
