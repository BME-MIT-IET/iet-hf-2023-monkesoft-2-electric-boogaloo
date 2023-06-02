# BDD tesztelés Cucumberrel

## Beállítás

Sok probléma volt a gradle inicializálásával, ami akkor derült ki, amikor a Cucumbert működésre akartam bírni.
Egész jelentős mennyiségű időt vett el ezeknek a problémáknak a feltárása, majd kijavítása.\
Ezek között volt nem megfelelő package struktúra vagy elnevezések, source fájlok olvasásának hiánya és különböző szintaktikákat nem vegyíthető *build.gradle* fájl.

Amint ezeket megoldottuk, az új függőségeket hozzáadva működött a Cucumber.

## Tesztek

A teszteket négy főbb kategóriába soroltam, ezek külön forrásfájlokban találhatók.

A *test/resources* mappában a *.feature* kiterjesztésű fájlok tartalmazzák a forgatókönyveket,
a *test/java* mappában található *.java* fájlok pedig az ezekhez tartozó lépések implementációját, logikáját.

Egy adott *{FeatureName}.feature* fájlhoz a névnek megfelelő *{FeatureName}Stepdefs.java* fájl tartozik.
A forgatókönyvek és az implementáció részletesen megtekinthető a projekt test mappájában.

A tesztek írásakor először rossz *assert* metódust használtam, ezt javítottam.

### Movement.feature

Itt mozgással kapcsolatos forgatókönyveket írtam.

Forgatókönyvek:
    -Egy játékos átmegy egy szomszédos mezőre
    -Egy játékos megpróbál egy nem szomszédos mezőre menni
    -Egy játékos egy olyan mezőre lép, ahol már van egy másik játékos.

### Equipment.feature

Itt az eszközök fel felvételével, használatával kapcsolatos forgatókönyveket írtam.

Forgatókönyvek:\
    &emsp; -Egy játékos felvesz különböző effektű és típusú tárgyakat\
    &emsp; -Egy játékos megtámad egy másik játékost egy éles fejszével\
    &emsp; -Egy játékos megtámad egy másik játékost egy kicsorbított fejszével\

### GloveReflect.feature

Itt a kesztyű eszköz speciális képességének működésével kapcsolatos forgatókönyveket írtam

Forgatókönyvek:\
    &emsp; -Egy kesztyűt viselő játékost megtámad egy ágenssel egy másik játékos\
    &emsp; -Egy kesztyűt viselő játékost megtámad egy ágenssel egy másik játékos, akin szintén kesztyű van\
    &emsp; -Egy kesztyűt viselő játékost megtámad egy ágenssel egy másik játékos, akin védőköpeny van, ami éppen nem védi meg\
    &emsp; -Egy kesztyűt viselő játékost megtámad egy ágenssel egy másik játékos, akin védőköpeny van, ami megvédi\

### Complex.feature

Ide komplex, összefogóbb forgatókönyveket írtam.

Forgatókönyvek:\
    -Játékos2 felvesz egy baltát a búvóhelyről, ahol áll,\
        &emsp; -majd Játékos1 egy szomszéd laboratóriumra lép,\
        &emsp; -majd Játékos1 interakcióba lép a laboratóriummal, így megtanulja az ottani genetikai kódot,\
        &emsp; -majd Játékos2 odalép arra a mezőre, amin Játékos1 áll,\
        &emsp; -majd Játékos2 megöli Játékos1-et a fejszéjével,\
        &emsp; -majd Játékos2 interakcióba lép a laboratóriummal, így megtanulja az ottani genetikai kódot\
    -Játékos1 interakcióba lép egy raktárral, ahonnan alapanyagokat szerez,\
        &emsp; -majd megtanul egy genetikai kódot egy laboratóriumból,\
        &emsp; -majd felhasználja az alapanyagokat vírus és vakcina készítésére,\
        &emsp; -és Játékos2 birtokol egy vírust, amit szintén azzal a genetikai kóddal készített,\
        &emsp; -majd Játékos2 arra a mezőre lép, amin Játékos1 áll,\
        &emsp; -majd Játékos2 elhasználja a vírusát Játékos1-re,\
        &emsp; -majd Játékos1 elhasználja a vakcináját magára,\
        &emsp; -majd Játékos1 elhasználja a vírusát Játékos2-re

## Tesztek eredményei

A tesztek javarészt mind helyesen lefutottak.\
Kisebb változtatásokra volt szükség, mint paraméterezhető protected konstruktorok, vagy getterek privát tagváltozókhoz.\
A Virus osztály Activate() metódusából kiszedtem egy félrevezető Virologist.removeAgent() hívást, mivel rossz virológusra volt hívva, sőt még rossz helyen is.\
(Egy felsőbb szintű függvénynek kell ezt meghívnia)\
A rossz működést helyesnek feltételezve sok időt töltöttem azzal, hogy rájöjjek miért nem működnek az ehhez kapcsolódó tesztek.






