# Szavazás

Spring Boot alkalmazás országgyűlési szavazások rögzítésére, lekérdezésére és statisztikáinak elkészítésére.

## Elkészült feladatok

- [x] 1 Szavazás adatainak rögzítése
- [x] 2 Egy képviselő adott szavazáson leadott szavazatának lekérdezése
- [x] 3 Egy szavazás eredményének kiszámítása
- [x] 4 Egy adott napon megtartott szavazások lekérdezése
- [x] 5.1. Képviselői részvételi átlag lekérdezése egy időszakra
- [x] 6 Docker image építése Gradle taskkal

## Alkalmazás indítása

```bash
./gradlew bootRun
```

A Swagger felület az alkalmazás indítása után itt érhető el:

```text
http://localhost:8080/swagger-ui.html
```

## Docker image építése

Szükséges egy futó Docker daemon, például Docker Desktop vagy Colima.

A Gradle task előbb elkészíti a futtatható Spring Boot JAR-t, majd a projekt gyökerében található `Dockerfile` segítségével Docker image-et épít:

```bash
./gradlew dockerBuild
```

Az elkészített image neve:

```text
szavazas_app_image_by_gradle_docker_build:latest
```

Ellenőrzés:

```bash
docker images
```

## Konténer indítása

Az image az alábbi paranccsal indítható el:

```bash
docker run --rm --name szavazas -p 8080:8080 szavazas_app_image_by_gradle_docker_build
```
--rm törli a konténert leállítás után
