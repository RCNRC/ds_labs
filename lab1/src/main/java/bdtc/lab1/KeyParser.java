package bdtc.lab1;

/**
 * Специальный класс с методом парсера.
 * Парсер по заданной допустимой строке из SysLog возвращает код важности и час появления сообщения.
 */
public class KeyParser {
    private static final String[] severityLevels = {
            "0 - Emergency",
            "1 - Alert",
            "2 - Critical",
            "3 - Error",
            "4 - Warning",
            "5 - Notice",
            "6 - Informational",
            "7 - Debug"
    };
    public static String parseKey(String line){
        int severity = Integer.parseInt(line.split(">")[0].split("<")[1])%8;
        int hour = Integer.parseInt(line.split(" ")[2].split(":")[0]);
        return severityLevels[severity] + "; h: " + hour;
    }
}
