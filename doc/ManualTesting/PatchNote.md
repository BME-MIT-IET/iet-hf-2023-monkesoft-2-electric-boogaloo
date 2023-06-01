# Pach Note
## Moves = 3, Stun active
_Stun helyzetben a virológus továbbra is rendelkezik move left jelző szerint lépéssel annak ellenére, hogy nem működik a lépése._

    Mivel a játék jelzi egy icon-al, hogy stun az aktív állapot ezért nem szükséges nullára redukálni a moves értékét.

## Kör a halottaknak hiba
_Amennyiben a játékos meghal, nem marad ki. Annak ellenére, hogy már nem tud semmilyen akciót végrehajtani körhöz jut. Ez azért rossz, mert a játék adott állapotára semmi sem utal. Azt a játékosoknak kell fejben tartani. Ezen még ront az hogy az akciók hiánya ellenére is kap lépést a karakter. Tehát a felhasználói felület teljes mértékben azt sugallja, hogy még játékban van._

    Kijavítva a hallottak nem kaptnak már kört sőt most már látszódik ki él és ki halt meg.

## Halottak visszavágnak hiba
_A halott karakter képes öni /lootolni mozgáson kívűl mindent. Előfordulhat, hogy A,B és C virológus egy mezőre ér:-A megöli C-t.-B stun-olja A-t.-C elveszi A baltáját és megöli A-t és B-t.Ekkor mindenki veszít, de a játék nem ér véget._

    Hallotak körjutásának kiküszöbölése megoldotta ezt a problémát is.

## Védőfelszerelés eldobása hiba
_Megegyezik az elvárttal (kivéve a táska)._

A táska levételével most már vissza esik a nyersanyagszám az elvárt mennyiségre.

## A virológus lépés hiba
_Megegyezik az elvárttal. Vannak kivételek, erről a Move = End Turn részen részletesebben._

    A move gomb már nem vet véget a körnek.

    

## Move = End Turn hiba
_Ha a virológus meghal, néha előfordul, hogy a 'move' gomb úgy viselkedik mint az end turn. Az adott karakternek nem muszáj halottnak lennie lehet stun-olva is._

_A hibát előhozni egy másik később leírt hiba segítségével a legkönnyebb: két játékos kell és mind a kettőnél kell legyen balta. Az első játékos megöli a másodikat a baltával majd rányom az 'end turn' gombra. Ekkor a második játékos is megöli az elsőt a baltával. Ekkor nagy valószínűséggel a második játékos 'move' gombja produkálja a hibát._

    Javításra került mivel már nem jut körhöz a halott virulógus.

## Overpopulation hiba
_A játék elején nincs megszabva, hogy mennyi virológus lehet, de nagy számok esetén azt eredményezi hogy a játék jelentősen lelassul és a megjelenítés sem támogatja igazán._

    Negatív, 0, vagy 24-nél nagyobb létszám limittel meg lett oldva a hiba.


# Összegzés
A meglévő és eddig hiba nélkül funkcionáló rész továbbra is működik error-ok és anomáliák nélkül.