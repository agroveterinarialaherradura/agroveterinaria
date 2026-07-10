package com.AgroVeterinaria.Herramientas;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class ValidationUtils {
	// RFC mexicano (persona física): 4 letras + 6 dígitos + 3 alfanuméricos
    private static final Pattern RFC_PATTERN = 
        Pattern.compile("^[A-Z&Ñ]{4}\\d{6}[A-Z0-9]{3}$", Pattern.CASE_INSENSITIVE);

    // Teléfono: extrae dígitos y debe tener 10
    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        String digits = phone.replaceAll("[^0-9]", "");
        return digits.length() == 10;
    }

    public static boolean isValidRFC(String rfc) {
        return rfc != null && RFC_PATTERN.matcher(rfc.toUpperCase()).matches();
    }

    // Obtener fecha actual en Madagascar con formato dd-MM-yyyy HH:mm
    public static String getCurrentMadagascarTime() {
        ZoneId madagascarZone = ZoneId.of("Indian/Antananarivo");
        ZonedDateTime now = ZonedDateTime.now(madagascarZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return now.format(formatter);
    }
}