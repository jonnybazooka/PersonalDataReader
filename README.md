# Personal Data Reader
Aplikacja przeznaczona do czytania danych osobowych z plików .txt i .xml i zapisywania ich do bazy danych.

## Biblioteki zewnętrzne
Core'owa Java dostarcza wszystkich niezbędnych narzędzi, więc jedynymi
zewnętrznymi bibliotekami dołączonymi do aplikacji są sterowniki do dwóch
najpopularniejszych SQL'owych baz danych: MySQL i PostgreSQL.

## Konfiguracja połączenia z bazą danych
Połączenie z bazą danych jest konfigurowane przy starcie aplikacji na podstawie
danych zawartych w pliku `connection.properties`. Niezbędne dane to url, user i password.
Plik musi się znajdować w tym samym katalogu co plik `PersonalDataReader-1.0.jar`.

Dodatkowo taki sam plik znajduje się w katalogu głównym projektu, jeżeli jest
on uruchamiany z IDE.

## Uruchomienie aplikacji
Aplikację można uruchomić z IDE, ale docelowo powinno się ją uruchamiać z linii
komend z pliku `PersonalDataReader-1.0.jar`. Aplikacja przyjmuje za argumenty
ścieżki do plików, które mają zostać przetworzone. Zarówno ścieżki relatywne (w stosunku do pliku .jar), 
jak i bezwzględne są prawidłowe. Aplikacja może przyjąć wiele takich
argumentów. Każdy z nich musi być poprzedzony argumentem _-in_.
Przykład:
```
java -jar PersonalDataReader.jar -in dane-osoby.txt -in "D:\\dane-osoby (1).xml"
```

Aplikację można też uruchomić bez podania żadnego argumentu. Wtedy poprosi ona użytkownika 
o podanie ścieżki do jednego pliku.

Plik _.jar_ jest dostępny [TU](https://1drv.ms/u/s!AgTphPW2o3ZIgP9yjpqgb_vxuWPRKg?e=ekud71)

## Poczynione założenia
1. Wyszedłem z założenia, że aplikacja ma działać na istniejącej już bazie danych.
Plik `MySQL_DB_Setup.sql` zawiera przykładowy skrypt, którego wykonanie powinno zapewnić srukturę
odpowiadającą strukturze z treści zadania.
2. Kolumna `Age` tabeli `customers` została oznaczona jako NULL. W związku z tym
pozostałe kolumny tej tabeli pozwoliłem sobie oznaczyć jako NOT NULL. Oznacza to, że
linie z czytanych plików gdzie będzie brakowało imienia lub nazwiska nie będą zapisywane
do bazy danych.
3. W specyfikacji struktury bazy danych nie pojawia się kolumna `city` mimo, że
taka kolumna pojawia się w przetwarzanych plikach. Zakładam, że nie jest to przeoczenie, tylko
że użytkownik aplikacji nie potrzebuje przechowywać tej informacji w bazie danych.
4. W plikach _.xml_ pojawiają sie takie zapisy jak ```<contact type="email">123123123</contact>```.
W plikach _.txt_ takie zapisy są weryfikowane i klasyfikowane przez Regex. Uznaję, że w
przypadku plików _.xml_ określenie kontaktu przez atrybut `type="email"` ma znaczenie nadrzędne.
W przeciwnym razie straciłby on swoje znaczenie. Może to jednak prowadzić do dziwnych zapisów
w bazie danych.