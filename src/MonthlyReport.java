import java.util.ArrayList;
import java.util.HashMap;

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
    public ArrayList<Integer> getMonths() {
        ArrayList<Integer> months = new ArrayList<>();
        months.addAll(listMonthly.keySet());
        return months;
    }


    // Возвращает сумму по месяцу
    // с учетом  расхода или дохода
    public int getSumForMonth(int month, boolean isExpense) {
        ArrayList<MonthlyData> data = listMonthly.get(month);
        int sum = 0;
        for (MonthlyData item : data) {
            if (item.isExpense == isExpense) {
                sum += item.totalSum();
            }
        }
        return sum;
    }

    // Вывод самый прибыльный товар или Самую большую трату
    public ProductInfo getMostProfOrExpenseByMonth(int month, boolean isExpense) {
        ArrayList<MonthlyData> list = listMonthly.get(month);
        ProductInfo productInfo = new ProductInfo();
        int maxSum = 0;
        for (MonthlyData data : list) {
            if (data.isExpense == isExpense) {
                int totalSum = data.totalSum();
                if (totalSum > maxSum) {
                    maxSum = totalSum;
                    productInfo.itemName = data.itemName;
                    productInfo.sum = data.sumOfOne;
                }
            }
        }
        return productInfo;
    }


    // загрузка отчета из файла
    public void loadData(short year) {

        if (isDataLoaded) {
            listMonthly.clear();
            System.out.println("Данные месячных отчетов считаны повторно.");
        }

        for (int month = 1; month <= 3; month++) {
            String monthString = Service.getTwoDigitMonthByNumber(month);
            String path = "resources/m."+ year + monthString + ".csv";

            String fileContents = ServiceData.readFileContentsOrNull(path);
            String[] lines = fileContents.split(System.lineSeparator());

            ArrayList<MonthlyData> dataList = new ArrayList<>();
            MonthlyData data;

            for (int j = 1; j < lines.length; j++) {
                String[] lineContents = lines[j].split(",");

                String itemName = lineContents[0];
                boolean isExpense = Boolean.parseBoolean(lineContents[1]);
                int quantity = Integer.parseInt(lineContents[2]);
                int sumOfOne = Integer.parseInt(lineContents[3]);

                data = new MonthlyData(itemName, isExpense, quantity, sumOfOne);
                dataList.add(data);
            }
            listMonthly.put(month, dataList);
        }
        isDataLoaded = true;
    }

    class MonthlyData {
        // название товара
        String itemName;
        // является ли запись тратой
        boolean isExpense;
        // количество закупленного или проданного товара
        int quantity;
        // стоимость одной единицы товара
        int sumOfOne;

        public MonthlyData(String itemName, boolean isExpense, int quantity, int sumOfOne) {
            this.itemName = itemName;
            this.isExpense = isExpense;
            this.quantity = quantity;
            this.sumOfOne = sumOfOne;
        }

        // сумма всех товаров
        public int totalSum() {
            return quantity * sumOfOne;
        }
    }
}
