# Teljesítmény teszt

    A teszthez használt számítógép:
    - Windows 10 64Bit 22H2 19045.2965 Build
    - Intel core i5-5300U @2.3 GHz (4 core)
    - 12 GB Memória
    - Intel HD Graphics 5500 integrált GPU

## Normál használat mellett
 A CPU terhelése nem haladta meg a 10%-ot, általában 3-5%-os terheléssel.
 
 A GPU 0.2% és 1.7%-os terhelést kapot a győzelmi felugró ablaktól 10%-ra kiugrott az érték, de ez még így sem jelentős. 
 
 A memóriaigény nem haladja meg a 150-200MB-t átlagosan csak 100-110MB-ot használ.

## Magas játékos szám mellett
A CPU átlagos terhelése 7-8%-kal nő 100 fölötti játékosszámnál.
Az egyes körvégék közti idő másodperc hosszúra növekedett.

A memoria használat több mint 2x akkora, 500-600 MB.

A GPU kihasználtsága viszont nem változott említésre méltó módon.

## Maximális játékos szám mellet
A teszt megismétléséhez szükséges játékos szám: 999 999 999
A jéték betöltési ideje percekben mérhető. CPU használat 40-50% körüli ekkor és 3GB vagy több memóriát fogyaszt. 
A játék ekkor kifogy a allokálható memóriából:

    Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
Ekkor már nem indul el a játék.