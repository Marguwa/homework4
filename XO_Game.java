import java.util.Scanner;
import java.util.Random;



public class XO_Game {

    private static char[][] map; // матрица игры
    private static int SIZE = 3; // размерность поля

    private static final char DOT_EMPTY = '•'; // пустой символ
    private static final char DOT_X = 'X'; // крестик
    private static final char DOT_O = '0'; // нолик

    private static final boolean SILLY_MODE = false;
    private static final boolean SCORING_MODE = true;

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {

        initMap();
        printMap();

        while (true) {

            humanTurn(); // ход человека

            if (isEndGame(DOT_X)) {
                break;
            }

            computerTurn(); // ход компьютера

            if (isEndGame(DOT_O)) {
                break;
            }
        }

        System.out.println("Игра закончена");

    }

    /*
    Метод подготовки игрового поля
     */

    private static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }


    /*
    Метод вывода игрового поля на экран
     */

    private static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

    /*
    Ход пользователя
     */

    private static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты ячейки (X Y)");
            y = scanner.nextInt() - 1; // Считывание номера строки
            x = scanner.nextInt() - 1; // Считывание номера столбца
        }
        while (!isCellValid(x, y));

        map[y][x] = DOT_X;
    }

    /*
    Ход компьютера
     */

    private static void computerTurn() {

        int x = -1;
        int y = -1;
        boolean moveFound = false;

        if (x == -1) {
            do {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);
            }
            while (!isCellValid(x, y));
        } else {


            if (!SCORING_MODE) {


                for (int i = 0; i < SIZE; i++)
                    for (int j = 0; j < SIZE; j++) {
                        if (map[i][j] == DOT_EMPTY) {


                            if (i - 1 >= 0 && j - 1 >= 0 && map[i - 1][j - 1] == DOT_O) {
                                y = i;
                                x = j;

                                moveFound = true;

                                System.out.println("LU");


                            } else if (i - 1 >= 0 && map[i - 1][j] == DOT_O) {
                                y = i;
                                x = j;

                                moveFound = true;

                                System.out.println("U");

                            } else if (i - 1 >= 0 && j + 1 < SIZE && map[i - 1][j + 1] == DOT_O) {
                                y = i;
                                x = j;

                                moveFound = true;

                                System.out.println("RU");
                            } else if (j + 1 < SIZE && map[i][j + 1] == DOT_O) {
                                y = i;
                                x = j;

                                moveFound = true;

                                System.out.println("R");
                            } else if (i + 1 < SIZE && j + 1 < SIZE && map[i + 1][j + 1] == DOT_O) {
                                y = i;
                                x = j;

                                moveFound = true;

                                System.out.println("RD");
                            } else if (i + 1 >= 0 && map[i + 1][j] == DOT_O) {
                                y = i;
                                x = j;

                                moveFound = true;

                                System.out.println("D");
                            } else if (i + 1 < SIZE && j - 1 >= 0 && map[i + 1][j - 1] == DOT_O) {
                                y = i;
                                x = j;

                                moveFound = true;

                                System.out.println("LD");
                            } else if (j - 1 >= 0 && map[i][j - 1] == DOT_O) {
                                y = i;
                                x = j;

                                moveFound = true;

                                System.out.println("L");
                            }

                            if (moveFound) {
                                break;
                            }
                        }

                        if (moveFound) {
                            break;
                        }
                    }



            }

        }
        map[y][x] = DOT_O;
    }



    /*
    Проверка корректности хода
     */

    public static boolean isCellValid(int x, int y) {
        boolean result = true;

        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            result = false;
        }

        if (map[y][x] != DOT_EMPTY) {
            result = false;
        }

        return result;
    }


    /*
    Проверка завершения игры
     */

    public static boolean isEndGame(char playerSymbol) {
        boolean result = false;

        printMap();

        // проверяем необходимость следующего хода

        if (checkWin(playerSymbol)) {
            System.out.println("Победили" + playerSymbol);
            result = true;
        }

        if (isMapFull()) {
            System.out.println("Ничья");
            result = true;
        }

        return result;
    }

    /*
    проверка на 100%-ю заполненность поля
     */

    private static boolean isMapFull() {
        boolean result = true;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    result = false;
                    break;
                }
            }

            if (!result) {

                break;
            }
        }
        return result;
    }


    /*
    Проверка победы
     */


    private static boolean checkWin(char playerSymbol) {
        boolean result = false;

        if(
                        (map[0][0] == playerSymbol && map[0][1] == playerSymbol && map[0][2] == playerSymbol) ||
                        (map[1][0] == playerSymbol && map[1][1] == playerSymbol && map[1][2] == playerSymbol) ||
                        (map[2][0] == playerSymbol && map[2][1] == playerSymbol && map[2][2] == playerSymbol) ||
                        (map[0][0] == playerSymbol && map[1][0] == playerSymbol && map[2][0] == playerSymbol) ||
                        (map[0][1] == playerSymbol && map[1][1] == playerSymbol && map[2][1] == playerSymbol) ||
                        (map[0][2] == playerSymbol && map[1][2] == playerSymbol && map[2][2] == playerSymbol) ||
                        (map[0][0] == playerSymbol && map[1][1] == playerSymbol && map[2][2] == playerSymbol) ||
                        (map[2][0] == playerSymbol && map[1][1] == playerSymbol && map[0][2] == playerSymbol)){
            result = true;
        }

        return result;
    }

}
