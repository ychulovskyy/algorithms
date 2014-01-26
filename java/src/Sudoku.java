import java.util.HashSet;
import java.util.Set;

public class Sudoku {
    private static int SIZE;
    private static int BLOCK_SIZE;
    private static int[][] DATA;
    private static long counter = 0;

    public static void main(String... args) {
        initData();
        findSudoku(DATA);
        System.out.println("There is no possible solution!");
    }

    private static void initData() {
        // put here any inputs you have
        DATA = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        SIZE = DATA.length;
        BLOCK_SIZE = (int) Math.sqrt(SIZE);
    }

    private static void findSudoku(int[][] data) {
        counter++;
        int[] point = findEmptyPlace(data);

        // done?
        if (point[0] == -1 && validate(data)) {
            printResult(data);
            System.exit(0);
        }

        // try next step
        for (int i = 1; i <= SIZE; i++) {
            data[point[0]][point[1]] = i;
            if (validate(data)) {
                findSudoku(data);
            }
        }

        // cleanup last step
        data[point[0]][point[1]] = 0;
    }

    private static int[] findEmptyPlace(int[][] data) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (data[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private static boolean validate(int[][] data) {
        // unique rows and columns
        for (int i = 0; i < SIZE; i++) {
            Set<Integer> row = new HashSet<Integer>(SIZE);
            Set<Integer> column = new HashSet<Integer>(SIZE);
            for (int j = 0; j < SIZE; j++) {
                row.add(data[i][j] == 0 ? -(j + 1) : data[i][j]);
                column.add(data[j][i] == 0 ? -(j + 1) : data[j][i]);
            }
            if (row.size() < SIZE || column.size() < SIZE) {
                return false;
            }
        }

        // correct blocks
        for (int a = 0; a < BLOCK_SIZE; a++) {
            for (int b = 0; b < BLOCK_SIZE; b++) {
                Set<Integer> block = new HashSet<Integer>(BLOCK_SIZE * BLOCK_SIZE);
                for (int i = 0; i < BLOCK_SIZE; i++) {
                    for (int j = 0; j < BLOCK_SIZE; j++) {
                        block.add(data[a * BLOCK_SIZE + i][b * BLOCK_SIZE + j] == 0
                                ? -(i + 1) * BLOCK_SIZE - j : data[a * BLOCK_SIZE + i][b * BLOCK_SIZE + j]);
                    }
                }

                if (block.size() < BLOCK_SIZE * BLOCK_SIZE) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void printResult(int[][] data) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(data[i][j]);
            }
            System.out.println();
        }
        System.out.println("Iterations: " + counter);
    }
}
