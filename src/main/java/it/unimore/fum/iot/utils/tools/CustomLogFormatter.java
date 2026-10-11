package it.unimore.fum.iot.utils.tools;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomLogFormatter extends Formatter {

    // Definisce il formato: giorno/mese/anno ora:minuti:secondi
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        // 1. Ottiene la data e ora locale dal timestamp del log
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(record.getMillis()), ZoneId.systemDefault());
        String timestamp = dateTime.format(formatter);

        // 2. Recupera la Severity (Livello del log)
        String level = record.getLevel().getLocalizedName();

        // 3. Recupera il nome del Logger che sta stampando
        String loggerName = record.getLoggerName() != null ? record.getLoggerName() : "UnknownLogger";

        // 4. Recupera l'ID del Thread (o il nome se disponibile tramite Thread.currentThread())
        // Nota: record.getThreadID() fornisce l'ID numerico del thread che ha generato il log
        String threadInfo = "Thread-" + record.getThreadID();

        // 5. Recupera il messaggio finale compilato
        String message = formatMessage(record);

        // Costruisce la riga di log finale: [Data] [Severity] [Thread] [Logger]: Messaggio
        return String.format("[%s] [%s] [Thread-ID: %s] [%s]: \n\t%s%n",
                timestamp,
                level,
                threadInfo,
                loggerName,
                message
        );
    }
}
