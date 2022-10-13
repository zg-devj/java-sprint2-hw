public class Service {
    // Возвращает название месяца по номеру
    public static String getMonthNameByNumber(Integer month) {
        if (month == 1) {
            return "Январь";
        } else if (month == 2) {
            return "Февраль";
        } else if (month == 3) {
            return "Март";
        } else if (month == 4) {
            return "Апрель";
        } else if (month == 5) {
            return "Май";
        } else if (month == 6) {
            return "Июнь";
        } else if (month == 7) {
            return "Июль";
        } else if (month == 8) {
            return "Август";
        } else if (month == 9) {
            return "Сентябрь";
        } else if (month == 10) {
            return "Октябрь";
        } else if (month == 11) {
            return "Ноябрь";
        } else if (month == 12) {
            return "Декабрь";
        } else {
            return null;
        }
    }
}
