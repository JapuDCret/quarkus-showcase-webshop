Feature: Bestellung

  Scenario: Rufe Produkte ab
    When ein Kunde die verfügbaren Produkte abrufen möchte
    Then erhält er eine Liste mit 6 Produkten

  Scenario Outline: Erstelle Bestellung
    Given Eine Bestellung mit den Lieferdaten "<vorname>", "<nachname>", "<strasse>", "<plz>", "<ort>"
    When der Kunde bestellt die Produkte <p1_anzahl>-Mal "<p1_name>" und <p2_anzahl>-Mal "<p2_name>"
    Then erhält er eine Zahlungsaufforderung mit einem Betrag von <betrag>

    Examples:
      | vorname   | nachname | strasse          | plz   | ort        | p1_name  | p1_anzahl | p2_name  | p2_anzahl | betrag |
      | Christian | Weh      | Apolloplatz 42   | 11111 | Düsseldorf | Salat    | 1         | Dressing | 2         | 9.5    |
      | Max       | Weh      | Lindenallee 1337 | 22222 | Berlin     | Ciabatta | 10        | Dip      | 5         | 20.0   |
      | Stephan   | Mhm      | II. Hagen 7      | 33333 | Essen      | Bowl     | 2         | Getränk  | 2         | 26.0   |
