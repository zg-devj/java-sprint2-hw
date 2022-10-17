import java.util.HashMap;

public class Service {
    // Возвращает название месяца по номеру
    public static String getMonthNameByNumber(Integer month) {
        HashMap<Integer, String> months = new HashMap<>();
        months.put(1, "Январь");
        months.put(2, "Февраль");
        months.put(3, "Март");
        months.put(4, "Апрель");
        months.put(5, "Май");
        months.put(6, "Июнь");
        months.put(7, "Июль");
        months.put(8, "Август");
        months.put(9, "Сентябрь");
        months.put(10, "Октябрь");
        months.put(11, "Ноябрь");
        months.put(12, "Декабрь");
        return months.getOrDefault(month, null);
    }

    // Возвращает месяц в виде строки от 01 до 12
    public static String getTwoDigitMonthByNumber(Integer number) {
        if (number >= 1 && number <= 10) {
            return String.format("0%d", number);
        } else if (number >= 11 && number <= 12) {
            return number.toString();
        } else {
            return null;
        }
    }
}
