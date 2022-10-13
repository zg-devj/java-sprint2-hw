import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Поехали!
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();
                if(command == 1) {

                } else if (command==2) {

                } else if (command==3) {

                } else if (command==4) {

                } else if (command==5) {

                }
                else {
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

