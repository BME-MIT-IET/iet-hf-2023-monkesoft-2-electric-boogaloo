# Keretrendszer beüzemelése

## Gradle build

Két fájlt kellett létrehozni: `build.gradle` és `settings.gradle`

A forrás és a resource fájlokat át kellett helyezni a `src/main/java` és a `src/main/resources` könyvtárakba.

## Github actions

A következő tutorialt követtem: https://docs.gradle.org/current/userguide/github-actions.html

Ezen kívül a workflow-ban futtathatóvá kellett tenni a `gradlew` fájlt.

A projekt lokálisan `gradle build`, majd `gradle run` VAGY `gradlew`, majd `gradlew run` parancsok kiadásával futtatható.

## Összefoglaló

A build keretrendszer beüzemelése lehetővé tette számunkra a buildek automatizálását és ellenőrzését, valamint a projekt dependenciáinak könnyű kezelését (pl.: Cucumber, SonarCloud). A github actions által megvalósított folyamatos integráció pedig gyors és korai visszajelzést ad a projekt állapotáról, és az esetleges hibákról.