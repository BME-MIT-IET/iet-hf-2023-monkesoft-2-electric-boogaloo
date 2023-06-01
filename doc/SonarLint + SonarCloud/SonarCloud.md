# SonarCloud integráció

## Beállítás

Nagyon sokat vesződtem a kezdeti beállítással, mert azt hittem van valami mód arra, hogy szervezet import nélkül be lehet állítani a SonarCloudot a repository analizálására. Rengeteg dokumentáció olvasás után arra kellett rájönnöm, hogy ez nem lehetséges, ezért segítséget kértem egy szervezet tulajdonostól. Ezután kaptam jogot minden szükséges művelet elvégzéséhez.

Néhány kisebb probléma mégis adódott a beállítás során:
- A laborhoz hasonlóan kértem a project beállításához kiegészítéseket a gradle build és a workflow fájlokhoz, azonban a verziószám, amit először adott, nem volt kompatibilis a gradle verzónkkal, így online kellett Sonar help fórumokat böngészve egy újabb érvényes verziószámot keresnem, ami már támogatja a 8-as gradle-t. Ami érdekes, hogy később újra kértem a projecthez kódot a labor alapján, és ekkor már egy újabb verziót adott, mint elsőnek.
- A következő probléma az volt, hogy nem tudta megfelelően "indexelni" a source fájlokat. Ezt a SonarCloud felületén include minták segítségével oldottam meg. Jelenlegi beállítás szerint a source fájlokat analizálja.
- Kicsit félrevezető volt, hogy a branchen, amin beállítottam, sosem jelzett hibát a pull requestekre. Végül rájöttem, hogy ilyenkor nem a teljes projektet analizálja, hanem a változtatásokat az adott ágon, és azért nem volt hiba, mert source fájlokat nem módosítottam.

## Jelzett hibák javítása

A hibák többsége ugyanaz volt, mint SonarLint esetén: nem használt virtuális függvény paraméterek és üres implementációk. Ezen kívül rengeteg code smellt jelzett, mivel nem a java konvenciót követtük függvények elnevezésekor. Ezzel sem foglalkoztam.

 Azonban volt néhány hiba, amit a SonarLint nem jelzett, vagy csak elkerülte a figyelmem:

### Nem megfelelően lezárt streamek
Egy input- és egy outputstream nem volt megfelelően lezárva a catch blokkjukban. Ezt try-with-resurces használatával javvítottam. (Kb. a C#-os "using" java megfalalője)

### Securerandom
Sima Random helyett Securerandomot használunk.

### @Override annotációk
Néhány függvényről lemaradtak. Ezeket pótoltam.

### toLower() / toUpper() és equals()
Helyettük biztosabb equalsIgnoreCase()

### Redundáns kódrészletek törlése
- return; void függvényben
- felesleges típusdefiníciók tárolók inicializálásánál (\<T> helyett \<>)
- Felesleges override függvények, amik csak visszahívnak az ősre.

## A projekt SonarCoud felülete az alábbi linken érhető el:
https://sonarcloud.io/project/overview?id=BME-MIT-IET_iet-hf-2023-monkesoft-2-electric-boogaloo