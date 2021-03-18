Feature: Bestellung

  Scenario: Rufe Produkte ab
    When ein Kunde die verfügbaren Produkte abrufen möchte
    Then erhält er eine Liste mit 6 Produkten

  Scenario Outline: Bestelle Produkte
    Given Eine Bestellung mit den Lieferdaten "<vorname>", "<nachname>", "<strasse>", "<plz>", "<ort>"
    And den zur Auswahl stehenden Produkten:
      | id | name     |
      | 1  | Salat    |
      | 2  | Dressing |
      | 3  | Ciabatta |
      | 4  | Dip      |
      | 5  | Bowl     |
      | 6  | Getränk  |
    When der Kunde bestellt die Produkte "<produkt_auswahl>"
    Then erhält er eine BestellungInfo mit einem Betrag von <betrag>

    Examples:
      | vorname   | nachname | strasse          | plz   | ort        | produkt_auswahl | betrag |
      | Christian | Weh      | Apolloplatz 42   | 11111 | Düsseldorf | [1:1,2:1]       | 9.0    |
      | Max       | Weh      | Lindenallee 1337 | 22222 | Berlin     | [3:10,4:5]      | 20.0   |
      | Stephan   | Mhm      | II. Hagen 7      | 33333 | Essen      | [1:1,5:1,6:2]   | 23.5   |
