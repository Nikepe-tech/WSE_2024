# Repozytorium aplikacji bankowej

To repozytorium zawiera aplikację bankową napisaną w języku Java. Aplikacja symuluje podstawowe operacje bankowe, takie jak tworzenie konta, wpłata, wypłata i transfer pieniędzy między kontami.

## Opis projektu

Projekt składa się z kilku kluczowych komponentów:

- **dto**: Pakiet zawierający klasy obiektów transferowych, takie jak `User`, `Account`, `AccountOperationType`, `AccountOperationsLog` i `Sex`.
- **logger**: Pakiet obsługujący logowanie z różnymi poziomami logów (FATAL, ERROR, WARN, INFO, DEBUG).
- **storage**: Pakiet zawierający interfejsy i implementacje repozytoriów do przechowywania danych użytkowników, kont bankowych i operacji na kontach.
- **service**: Pakiet serwisów wykonujących logikę biznesową, taką jak zarządzanie użytkownikami, kontami i operacjami na nich.
- **converter**: Pakiet zawierający klasy konwertujące obiekty na różne formaty, takie jak CSV.

## Struktura pakietów

- **dto**: Zawiera podstawowe klasy reprezentujące użytkowników, konta, typy operacji bankowych i operacje na kontach.
- **logger**: Obsługuje logowanie do pliku `log.txt` z różnymi poziomami logów.
- **storage**: Zawiera repozytoria do zarządzania danymi użytkowników, kont i operacji na kontach.
- **service**: Odpowiada za logikę biznesową aplikacji, taką jak zarządzanie użytkownikami, tworzenie i zarządzanie kontami, logowanie operacji na kontach.
- **converter**: Zawiera narzędzia konwertujące obiekty na różne formaty, takie jak CSV.

## Instrukcja uruchomienia

1. **Klonowanie repozytorium:**

```bash
git clone https://github.com/Nikepe-tech/WSE_2024
cd WSE_2024
```

2. Uruchomienie aplikacji:

* Zaimportuj projekt do środowiska IDE obsługującego Java (np. IntelliJ IDEA, Eclipse).
* Skompiluj i uruchom klasę Main znajdującą się w pakiecie main.

3. Testowanie aplikacji:

* Użyj narzędzi do testowania jednostkowego, takich jak JUnit, aby przetestować różne komponenty aplikacji.

## Autor
* Mikita Kryvenia
* Kontakt: nikita.krivenia@gmail.com





