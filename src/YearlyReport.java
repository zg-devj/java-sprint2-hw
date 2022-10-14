import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    HashMap<Integer, ArrayList<YearlyData>> listYearly;

    // Загружен ли отчет
    private boolean isDataLoaded = false;
    private int year;

    // Вернуть год отчета
    public int getReportYear() {
        return year;
    }

    public boolean isDataLoaded() {
        return isDataLoaded;
    }

    public YearlyReport() {
        listYearly = new HashMap<>();
    }

    // средний расход или расход за все месяцы
    public double getAverage(boolean is_expense) {
        int sum = 0;
        double count = 0;
        for (ArrayList<YearlyData> values : listYearly.values()) {
            for (YearlyData value : values) {
                if (value.is_expense == is_expense) {
                    sum += value.amount;
                    count++;
                }
            }
        }
        return sum / count;
    }

    // прибыль  за каждый месяц
    public HashMap<Integer, Integer> getAllProfits() {
        HashMap<Integer, Integer> profit = new HashMap<>();
        for (Integer month : listYearly.keySet()) {
            int expense = 0;
            int income = 0;
            for (YearlyData data : listYearly.get(month)) {
                if (data.is_expense) {
                    expense = data.amount;
                } else {
                    income = data.amount;
                }
            }
            int difference = income - expense;
            profit.put(month, difference);
        }
        return profit;
    }

    // Возвращаем список не прошедших сверку месяцев
    public ArrayList<Integer> getNotNorthernMonths(MonthlyReport report) {
        ArrayList<Integer> result = new ArrayList<>();
        for (Integer month : listYearly.keySet()) {
            for (YearlyData data : listYearly.get(month)) {
                if (data.amount != report.getSumForMonth(month, data.is_expense)) {
                    if (!result.contains(month)) result.add(month);
                }
            }
        }
        return result;
    }

    // загрузка отчета из файла
    public void loadData(short year) {
        if (isDataLoaded) {
            System.out.println("Данные годового отчета были считаны ранее.");
            return;
        }
        this.year = year;
        String fileContents = ServiceData.readFileContentsOrNull("resources/y." + year + ".csv");
        String[] lines = fileContents.split(System.lineSeparator());

        ArrayList<YearlyData> dataList;
        YearlyData data;

        for (int i = 1; i < lines.length; i++) {
            String[] lineContents = lines[i].split(",");

            int month = Integer.parseInt(lineContents[0]);
            int amount = Integer.parseInt(lineContents[1]);
            boolean is_expense = Boolean.parseBoolean(lineContents[2]);

            dataList = new ArrayList<>();
            data = new YearlyData(amount, is_expense);
            if (listYearly.containsKey(month)) {
                dataList = listYearly.get(month);
                dataList.add(data);
            } else {
                dataList.add(data);
                listYearly.put(month, dataList);
            }
        }
        isDataLoaded = true;
    }

    class YearlyData {
        // сумма
        int amount;
        // является ли запись тратой
        boolean is_expense;

        public YearlyData(int amount, boolean is_expense) {
            this.amount = amount;
            this.is_expense = is_expense;
        }

    }
}
