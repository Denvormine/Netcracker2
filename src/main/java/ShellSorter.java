import java.util.concurrent.Phaser;

// Алгоритм сортировки был взят с википедии https://ru.wikipedia.org/wiki/Сортировка_Шелла
// И переработан под многопоточность
public class ShellSorter extends Thread {

    private Integer[] array;
    private int space;
    private int startPos;
    private Phaser phaser;

    public ShellSorter(Integer[] array, Phaser phaser, int startPos, int space) {
        this.array = array;
        this.phaser = phaser;
        this.startPos = startPos;
        this.space = space;
    }

    public void run() {
        sort();
    }

    public void sort() {
        for (int step = array.length / 2; step >= 1; step = step / 2) {
            for (int i = startPos; i < step; i += space) {
                insertionSort (i, step);
            }
            phaser.arriveAndAwaitAdvance();
        }
    }
    // Обычная сортировка вставками
    private void insertionSort(int start, int step)
    {
        int tmp;
        for (int i = start; i < array.length - 1; i += step)
            for (int j = Math.min(i + step, array.length - 1); j - step >= 0; j = j - step)
                if (array[j - step] > array[j])
                {
                    tmp = array[j];
                    array[j] = array[j - step];
                    array[j - step] = tmp;
                }
                else break;
    }
}

/*
 * @param array array of Integer
 * @param startPos starting position of sorting
 * @param step amount of objects, between 3 numbers
 * @param space (how many objects to skip - 1) (other threads will sort them).
 *                Value must be equal to amount of threads
 */