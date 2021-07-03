# Progetto-M21
Sistema per la gestione di un punto di ritiro.

## Strumenti necessari:

 - [MySql](https://dev.mysql.com/downloads/mysql/): permette di creare database relazionali
 - [Workbench](https://www.mysql.com/it/products/workbench/): permette la gestione del database
 - Se non previsto dal proprio IDE installare "Maven Integration": gestisce le dipendenze.
 - Se si utilizza Eclipse, installare da Eclipse Marketplace:
    - [ANSI Escape in Console](https://marketplace.eclipse.org/content/ansi-escape-console): permette la decodificare il testo della console in formato ANSI.
   - [Spring Tools 4](https://marketplace.eclipse.org/content/spring-tools-4-aka-spring-tool-suite-4): permette l'esecuzione del programma tramite IDE

## Connessione al Database:

- Creare uno schema di nome `progettom21`
- Inserire le credenziali di accesso in  ` /src/main/resources/application.properties `

## Compilazione tramite IDE:

1. Clonare il repository da qualsiasi IDE o tramite terminale
`
git clone https://github.com/IngSW-unipv/Progetto-M21.git
`
2. Importare progetto Maven
3. Eseguire l'applicazione `Run as Spring Boot App `
4. Aprire qualsiasi browser e navigare in http://localhost:8080

## Compilazione tramite terminale:
1. Dowload [Progetto-M21-0.0.1-SNAPSHOT.jar](https://github.com/IngSW-unipv/Progetto-M21/releases/tag/v1.0)
2. `java -jar Progetto-M21-0.0.1-SNAPSHOT.jar`
3. Aprire qualsiasi browser e navigare in http://localhost:8080
