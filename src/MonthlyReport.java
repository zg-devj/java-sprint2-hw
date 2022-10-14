import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonthlyReport {
    HashMap<Integer, ArrayList<MonthlyData>> listMonthly;

    // Загружен ли отчет
    private boolean isDataLoaded = false;

    public boolean isDataLoaded() {
        return isDataLoaded;
    }

    public MonthlyReport() {
        listMonthly = new HashMap<>();
    }

    // Возврат месяцев
    public ArrayList<Integer> getMothes() {
        ArrayList<Integer> months = new ArrayList<>();
        months.addAll(listMonthly.keySet());
        return months;
    }

    // Самый прибыльный товар
    public ProductInfo getMostProfitableByMonth(int month) {
        ArrayList<MonthlyData> list = listMonthly.get(month);
        ProductInfo productInfo = new ProductInfo();
        int maxSum = 0;
        for (MonthlyData data : list) {
            if (!data.is_expense) {
                int totalSum = data.totalSum();
                if (totalSum > maxSum) {
                    maxSum = totalSum;
                    productInfo.item_name = data.item_name;
                    productInfo.sum = data.sum_of_one;
                }
            }
        }
        return productInfo;
    }

    // Самуя большая трата
    public ProductInfo getBiggestExpenseByMonth(int month) {
        ArrayList<MonthlyData> list = listMonthly.get(month);
        ProductInfo productInfo = new ProductInfo();
        int maxSum = 0;
        for (MonthlyData data : list) {
            if(data.is_expense) {
                int totalSum = data.totalSum();
                if (totalSum > maxSum) {
                    maxSum = totalSum;
                    productInfo.item_name = data.item_name;
                    productInfo.sum = totalSum;
                }
            }
        }
        return productInfo;
    }

    // загрузка отчета из файла
    public void loadData() {
        if (isDataLoaded) {
            System.out.println("Данные месячных отчетов были считаны ранее.");
            return;
        }

        for (int i = 1; i <= 3; i++) {
            String fileContents = ServiceData.readFileContentsOrNull("resources/m.20210" + i + ".csv");
            String[] lines = fileContents.split(System.lineSeparator());

            ArrayList<MonthlyData> dataList = new ArrayList<>();
            MonthlyData data;
            int month = i;

            for (int j = 1; j < lines.length; j++) {
                String[] lineContents = lines[j].split(",");

                String item_name = lineContents[0];
                boolean is_expense = Boolean.parseBoolean(lineContents[1]);
                int quantity = Integer.parseInt(lineContents[2]);
                int sum_of_one = Integer.parseInt(lineContents[3]);

                data = new MonthlyData(item_name, is_expense, quantity, sum_of_one);
                dataList.add(data);
            }
            listMonthly.put(month, dataList);
        }
        isDataLoaded = true;
    }

    // TODO: delete this
    private void printList() {
        for (Map.Entry<Integer, ArrayList<MonthlyData>> item : listMonthly.entrySet()) {
            System.out.println(item.getKey() + ":");
            for (MonthlyData data : item.getValue()) {
                System.out.println(data);
            }
        }
    }

    class MonthlyData {
        // название товара
        String item_name;
        // является ли запись тратой
        boolean is_expense;
        // количество закупленного или проданного товара
        int quantity;
        // стоимость одной единицы товара
        int sum_of_one;

        public MonthlyData(String item_name, boolean is_expense, int quantity, int sum_of_one) {
            this.item_name = item_name;
            this.is_expense = is_expense;
            this.quantity = quantity;
            this.sum_of_one = sum_of_one;
        }

        public int totalSum() {
            return quantity * sum_of_one;
        }

        // TODO: delete this
        @Override
        public String toString() {
            return item_name + " : " + is_expense + " : " + quantity + " : " + sum_of_one;
        }
    }
}
