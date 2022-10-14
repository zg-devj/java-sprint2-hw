import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    HashMap<Integer, ArrayList<YearlyData>> listYearly;
    private boolean isDataLoaded = false;
    private int year;

    public YearlyReport() {
        listYearly = new HashMap<>();
    }

    // Вернуть год отчета
    public int getReportYear() {
        return year;
    }

    // Загружен ли отчет
    public boolean isDataLoaded() {
        return isDataLoaded;
    }

    // средний расход или расход за все месяцы
    public double getAverage(boolean is_expense) {
        int sum = 0;
        double count = 0;
        for (ArrayList<YearlyData> values : listYearly.values()) {
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i).is_expense == is_expense) {
                    sum += values.get(i).amount;
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

    //TODO: delete this
    public void printList() {
        for (Integer key : listYearly.keySet()) {
            System.out.println("key =  " + key);
            for (YearlyData data : listYearly.get(key)) {
                System.out.println(data);
            }
        }
    }

    class YearlyData {
        int amount;
        boolean is_expense;

        public YearlyData(int amount, boolean is_expense) {
            this.amount = amount;
            this.is_expense = is_expense;
        }

        //TODO: delete this
        @Override
        public String toString() {
            return amount + " : " + is_expense;
        }
    }
}
