import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Phaser;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int arraySize = 100;
        int threadAmount = 4;
        Integer[] array = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            array[i] = 100 - i;
            //array[i] = random.nextInt(100);
        }

        for (int num : array) {
            System.out.println(num + " ");
        }

        Phaser phaser = new Phaser(threadAmount);
        ArrayList<ShellSorter> sorters = new ArrayList<>();

        for (int i = 0; i < threadAmount; i++) {
            ShellSorter shellSorter = new ShellSorter(array, phaser, i, threadAmount);
            sorters.add(shellSorter);
            shellSorter.start();
        }

        for (ShellSorter sorter : sorters) {
            try {
                sorter.join();
            }
            catch (InterruptedException ex) {
                System.err.println("Thread was interrupted");
            }
        }

        boolean isSorted = true;
        for (int i = 0; i < arraySize - 1; i++) {
            if (array[i] > array[i + 1]) {
                isSorted = false;
            }
        }
        System.out.println("Array sorted: " + isSorted);
    }
}
