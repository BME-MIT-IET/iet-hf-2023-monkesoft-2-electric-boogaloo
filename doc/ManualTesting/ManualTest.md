# Tervezett tesztek
- Kevésbé intuitív részek keresése a program használatában:
    - felhasználói inteface hiányosságai, gyengeségei
    - visszajelzések hiánya
    - félrevezető UI elemek

- Rendeltetés szerű használat közben előfordulható hibák keresése:
    - dokumentáció alapján a lehető legtöbb szcenáriót végigjátszva

- Nem rendeltetésszerű használatból fakadó hibák keresése:
    - min-max hibák
    - program szerkezetéből adódható hibák
    - jelzések ingnorálásából fakadó hibák/nem kezelt kivételek

# Kevésbé intuitív User Interface elemek

## Félreérthető UI jelzések
A karakterek mozgását a 'Move' gombbal lehet kezdeményezni. Ez a gomb akkor is aktív marad, ha már a karakter lépésszáma elfogyott. Ez félreérthető mivel a többi UI elem is azt sugallja, hogy mozoghat még a karakter.

## Loot Loop
Loot esetén nincs semmilyen visszajelzés, hogy az adott opciónak nincs hatása. Ez csak olyan téren zavaró, hogy mivel nem mozgás ezért ilyen akciót akármennyiszer végre lehet hajtani, így nincs értelme annak, hogy ne áruljuk el azt, hogy az adott dolgot tárgyat el lehet-e tulajdonítani. Minden tárgyat/ nyersanyagot el tud a körében tulajdonítani.

## Moves = 3, Stun active 
Stun helyzetben a virológus továbbra is rendelkezik move left jelző szerint lépéssel annak ellenére, hogy nem működik a lépése.

## Kör a halottaknak
Amennyiben a játékos meghal, nem marad ki. Annak ellenére, hogy már nem tud semmilyen akciót végrehajtani körhöz jut. Ez azért rossz, mert a játék adott állapotára semmi sem utal. Azt a játékosoknak kell fejben tartani. Ezen még ront az hogy az akciók hiánya ellenére is kap lépést a karakter. Tehát a felhasználói felület teljes mértékben azt sugallja, hogy még játékban van.

## Győzelem
Győzelem esetén ha valamiért nem kerül fókuszba a győzelmet jelző ablak, akkor csak a zene jelzi a játék végét. A játék továbbra is aktív marad, más játékos is képes nyerni és az első győztes is képes újra nyerni (minden egyes nyerés feltétel teszt győzelmet detektál addig, amíg minden el nem felejti a genetikai leírásokat)

# Rendeltetésszerű használat közben előfordulható hibák
## Védőfelszerelés felvétele az inventory-ba
### Elvárt működés
Megjelenik az adott virológus inventory-jában az adott felszerelés, amennyiben volt felvehető felszerelés
### Tényleges működés
Megegyezik az elvárttal.

## Védőfelszerelés eldobása
### Elvárt működés
Az adott tárgy eltűnik az inventory-ból és megsemmisül
### Tényleges működés
Megegyezik az elvárttal. 
**Kivétel a bag**
#### Hiba a táskával
Táska nem eldobható _(discard)_ tárgy.

## Védőfelszerelés felvétele a virolóusra
### Elvárt működés
A virológuson megjelenik a felszerelés, mint viselt felszerelés
### Tényleges működés
Megegyezik az elvárttal.

## Védőfelszerelés levétele a virológusról
### Elvárt működés
A virológusról eltűnik a felszerelés, majd az inventory-ba kerül
### Tényleges működés
Megegyezik az elvárttal.

## Védőfelszerelés ellopása
### Elvárt működés
A virológus megpróbál ellopni egy felszerelést egy másik virológustól
### Tényleges működés
Megegyezik az elvárttal.

## Anyag ellopása
### Elvárt működés
A virológus megpróbál ellopni valamekkora mennyiségű és valamilyen típusú anyagot egy másik virológustól
### Tényleges működés
Megegyezik az elvárttal.

## Vakcina használata
### Elvárt működés
A virológus magán használ egy vakcinát, a vakcina száma az inventory-ban csökken eggyel, a vakcina védelmet bíztosít a fertőződéssel szemben
### Tényleges működés
Megegyezik az elvárttal.

## Vírus készítése
### Elvárt működés
A virológus egy már megismert geneikai kódból vírust készít, az alapanyagok száma csökken eggyel
### Tényleges működés
Megegyezik az elvárttal.

## Vakcina készítése
### Elvárt működés
A virológus egy már megismert geneikai kódból vakcinát készít, az alapanyagok száma csökken eggyel
### Tényleges működés
Megegyezik az elvárttal.

## Óvóhellyel történő interakció
### Elvárt működés
A virlógus az Óvóhely mezőn állva megpróbál felvenni egy védőfelszerelést az inventory-jába
### Tényleges működés
Megegyezik az elvárttal.

## Raktárral történő interakció
### Elvárt működés
A virlógus az Raktár mezőn állva megpróbál felvenni egy anyagot az inventory-jába
### Tényleges működés
Megegyezik az elvárttal.

## Vírus kenése
### Elvárt működés
A virológus megpróbál egy vírust kenni egy másik virológusra, a vírus száma csökken az inventory-ban
### Tényleges működés
Megegyezik az elvárttal.

## A virológus lép
### Elvárt működés
A virológus megpróbál rálépni egy mezőre
### Tényleges működés
Megegyezik az elvárttal. Vannak kivételek, erről a Move = End Turn részen részletesebben.

## Laborral történő interakció
### Elvárt működés
A virlógus az Labor mezőn állva megpróbál megtanulni egy genetikai kódot
### Tényleges működés
Megegyezik az elvárttal.

## Move = End Turn
Ha a virológus meghal, néha előfordul, hogy a 'move' gomb úgy viselkedik mint az end turn. Az adott karakternek nem muszáj halottnak lennie lehet stun-olva is.

    A hibát előhozni egy másik később leírt hiba segítségével a legkönnyebb: 

    - két játékos kell és mind a kettőnél kell legyen balta. Az első játékos megöli a másodikat a baltával majd rányom az 'end turn' gombra. Ekkor a második játékos is megöli az elsőt a baltával. Ekkor nagy valószínűséggel a második játékos 'move' gombja produkálja a hibát.

# Nem rendeltetésszerű használatból fakadó hibák

## Overpopulation
A játék elején nincs megszabva, hogy mennyi virológus lehet, de nagy számok esetén azt eredményezi hogy a játék jelentősen lelassul és a megjelenítés sem támogatja igazán.

## Halottak visszavágnak
A halott karakter képes öni /lootolni mozgáson kívűl mindent. Előfordulhat, hogy A,B és C virológus egy mezőre ér:
- A megöli C-t.
- B stun-olja A-t.
- C elveszi A baltáját és megöli A-t és B-t.

Ekkor mindenki veszít, de a játék nem ér véget. 

# Összegzés
Szerintem nagyon intuitív a játék használata csupán apró hibák hiányosságok vannak.