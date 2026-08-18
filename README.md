# Spis pacjentów — raport Stimulsoft

Aplikacja Spring Boot generująca raport PDF ze spisem pacjentów na podstawie danych z PostgreSQL, z wykorzystaniem Stimulsoft Reports.Java.


## Technologie

Java, Spring Boot, Spring Data JPA, PostgreSQL, Flyway, Stimulsoft Reports.Java, Testcontainers, JUnit 5


## Wymagania wstępne

- JDK 25
- Maven
- Docker + Docker Compose

## Uruchomienie projektu

### 1. Skonfiguruj zmienne środowiskowe

Skopiuj `.env.example` do `.env` i uzupełnij wartości:

DB_NAME=nazwa bazy danych\
DB_USER=nazwa użytkownika\
DB_PASSWORD=hasło do bazy danych


### 2. Stwórz i uruchom bazę danych PostgreSQL

W głównym folderze projektu:

docker compose up -d


Sprawdź, czy kontener działa poprawnie:

docker compose ps


### 3. Uruchom aplikację

Uruchom klasę główną (`PatientsReportApplication`), upewniając się, że zmienne z `.env` są dostępne dla konfiguracji uruchomieniowej.

Przy starcie aplikacji Flyway automatycznie utworzy tabelę `patients` i wstawi 30 przykładowych, fikcyjnych rekordów.


## Przykładowe wywołanie endpointu

curl -v "http://localhost:8080/api/reports/patients?format=pdf" --output spis-pacjentow.pdf

Oczekiwana odpowiedź:

HTTP/1.1 200 OK\
Content-Type: application/pdf\
Content-Disposition: attachment; filename="spis-pacjentow.pdf"


Jeśli w bazie nie ma żadnych pacjentów, endpoint zwraca `200 OK` z treścią `Brak pacjentów`. Przy nieobsługiwanym formacie (aktualnie `format` innym niż `pdf`) zwracane jest `400 Bad Request`.


## Licencja Stimulsoft

Biblioteka Stimulsoft Reports.Java jest używana w trybie próbnym.
Wygenerowane raporty PDF zawierają znak wodny "TRIAL" na każdej stronie.
Korzystanie z Stimulsoft Designer do tworzenia i edytowania szablonów `.mrt` wymaga zarejestrowania konta na 30-dniowy okres próbny lub wykupienia licencji.
Stimulsoft Reports.Java jest licencjonowany w modelu subskrypcyjnym, rozliczanym na developera.
Koszt licencji zależy od liczby osób w zespole mających dostęp do bibliotek Stimulsoft i tworzących szablony raportów.
Dystrybucja gotowej aplikacji jest bezpłatna (royalty-free).
Dostępnych jest kilka poziomów subskrypcji, różniących się liczbą obsługiwanych developerów oraz dodatkowymi uprawnieniami (np. możliwość white-labelingu czy licencja na serwer budujący CI/CD).