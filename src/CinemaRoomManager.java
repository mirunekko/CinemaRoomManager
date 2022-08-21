import java.util.Scanner;

public class CinemaRoomManager {
    final static Scanner scanner = new Scanner(System.in);
    private static char[][] room;
    private static int rows;
    private static int seats;
    private static int countTicket = 0;
    private static float percentage = 0;
    private static int currentIncome = 0;

    private static int calculateIncome() {
        int totalSeats = rows * seats ;
        int firstHalf = rows  / 2 * seats;
        int secondHalf = (rows / 2 + rows  % 2) * seats;

        if (totalSeats < 60) {
            return totalSeats * 10;
        } else if (totalSeats > 60 && rows  % 2 == 0) {
            return (totalSeats / 2 * 10) + (totalSeats / 2 * 8);
        } else {
            return firstHalf * 10 + secondHalf * 8;
        }
    }

    public static void chooseRoom() {
        System.out.println("\nEnter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();
        room = new char[rows + 1][seats + 1];
    }

    public static void createRoom() {
        char a = 48;
        char b = 48;
        for (char i = 0; i < room.length; i++, b++) {
            for (char j = 0; j < room[i].length; j++, a++) {
                if (room[i][j] == room[0][j] && room[i][j] != room[0][0]) {
                    room[i][j] = a;
                } else if (room[i][j] == room[i][0] && room[i][j] != room[0][0]) {
                    room[i][j] = b;
                } else if (room[i][j] == room[0][0]) {
                    room[i][j] = 32;
                } else room[i][j] = 'S';
            }
        }
    }
    private static void printRoom() {
        System.out.print("Cinema:" + "\n ");
        for (char [] i : room) {
            for (char j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    private static void buyTicket() {
        System.out.println("\nEnter a row number:");
        int clientRow = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int clientSeat = scanner.nextInt();
        if (clientRow < 1 || clientRow >= room.length
                || clientSeat < 1 || clientSeat >= room.length) {
            System.out.println("Wrong input!");
            buyTicket();
        } else if (room[clientRow][clientSeat] == 'B') {
            System.out.println("That ticket has already been purchased!");
            buyTicket();
        } else {
            int totalSeats = rows * seats ;
            int firstHalf = rows/ 2;
            int price = totalSeats <= 60 || clientRow <= firstHalf ? 10 : 8;
            room[clientRow][clientSeat] = 'B';
            countTicket++;
            percentage = (float) countTicket * 100 / (float) totalSeats;
            currentIncome += price;

            System.out.printf("\nTicket price: $%d\n", price);
        }
    }

    private static void showStats() {
        char percent = '%';
        System.out.printf("Number of purchased tickets: %d\n", countTicket);
        System.out.printf("Percentage: %.2f%c\n", percentage, percent);
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", calculateIncome());
    }

    public static void showMenu() {
        System.out.println("1. Show the seats\n2. Buy a ticket" +
                "\n3. Statistics\n0. Exit");
        int menu = scanner.nextInt();

        switch (menu) {
            case 1:
                printRoom();
                showMenu();
                break;
            case 2:
                buyTicket();
                showMenu();
                break;
            case 3:
                showStats();
                showMenu();
                break;
            case 0:
                System.out.println("Exit");
                break;
        }
    }
}
