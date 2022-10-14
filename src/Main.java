import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();
                if (command == 1) {
                    // Считать все месячные отчёты.
                    monthlyReport.loadData();
                } else if (command == 2) {
                    // Считать годовой отчёт
                    yearlyReport.loadData((short) 2021);
                } else if (command == 3) {
                    // Сверить отчёты
                    if (monthlyReport.isDataLoaded() && yearlyReport.isDataLoaded()) {
                        ArrayList<Integer> months = yearlyReport.getNotNorthernMonths(monthlyReport);
                        if (months.size() > 0) {
                            System.out.println("Обнаружено несоответствие:");
                            for (Integer month : months) {
                                System.out.println(Service.getMonthNameByNumber(month));
                            }
                        } else {
                            System.out.println("Операция успешна завершина.");
                        }
                    } else {
                        System.out.println("Вы не считали месячные и годовой отчеты!");
                    }
                } else if (command == 4) {
                    if (monthlyReport.isDataLoaded()) {
                        for (Integer month : monthlyReport.getMoths()) {
                            System.out.println(Service.getMonthNameByNumber(month) + ":");
                            ProductInfo mostProfitable = monthlyReport.getMostProfOrExpenseByMonth(month, false);
                            System.out.println("Самый прибыльный товар: \"" +
                                    mostProfitable.item_name + "\", цена " + mostProfitable.sum);
                            ProductInfo biggestExpense = monthlyReport.getMostProfOrExpenseByMonth(month, true);
                            System.out.println("Самуя большая трата: \"" +
                                    biggestExpense.item_name + "\", цена " + biggestExpense.sum);
                            System.out.println();
                        }
                    } else {
                        System.out.println("Вы не считали месячные отчеты!");
                    }
                } else if (command == 5) {
                    // Вывести информацию о годовом отчёте
                    if (yearlyReport.isDataLoaded()) {
                        System.out.println("Годовой отчёт.");
                        System.out.println("Год " + yearlyReport.getReportYear());
                        System.out.println("Прибыль по каждому месяцу:");
                        for (Map.Entry<Integer, Integer> profit : yearlyReport.getAllProfits().entrySet()) {
                            System.out.printf("\t%s %d%n",
                                    Service.getMonthNameByNumber(profit.getKey()),
                                    profit.getValue());
                        }
                        System.out.printf(
                                "Средний расход за все месяцы в году: %.2f%n",
                                yearlyReport.getAverage(true));
                        System.out.printf(
                                "Средний доход за все месяцы в году: %.2f%n",
                                yearlyReport.getAverage(false));
                        System.out.println();
                    } else {
                        System.out.println("Вы не считали годовой отчет!");
                    }
                } else {
                    System.out.println("Извините, такой команды пока нет");
                }
            } else {
                String command = scanner.next();
                if (command.equals("exit")) {
                    break;
                } else {
                    System.out.println("Извините, такой команды пока нет");
                }
            }
        }
        System.out.println("Программа завершина");
    }

    // печать меню
    private static void printMenu() {
        System.out.println("1 - Считать все месячные отчёты.");
        System.out.println("2 - Считать годовой отчёт.");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("exit - Выйти из программы");
    }
}

