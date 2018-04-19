/**
 * Class containing 4 QuickSort methods:
 *  1. Select the first item of the partition as the pivot.
 *     Treat partitions of size one and two as stopping cases.
 *  2. Select the first item of the partition as the pivot.
 *     Treat a partition of size 100 as a stopping case. Use an insertion sort to finish.
 *  3. Select the first item of the partition as the pivot.
 *     Treat a partition of size 50 as a stopping case. Use an insertion sort to finish.
 *  4. Select the median-of-three as the pivot.
 *     Treat partitions of size one and two as stopping cases.
 *
 * @author Yan Vinokur
 */
public class QuickSort {
    /**
     * Partition array such that all items less than or equal to 'pivot' are
     * to the left of pivot and the rest are to the right. Pivot is selected
     * based on type parameter.
     * @param numbers Array of numbers to partition
     * @param i Start index (anything below is ignored)
     * @param k End index (anything above is ignored)
     * @param type Specifies how pivot is selected see class description.
     * @return location of last item in low partition.
     */
    private static int partition(int numbers [], int i, int k, String type) {
        int l = 0;
        int h = 0;
        int midpoint = 0;
        int pivot = 0;
        int temp = 0;
        boolean done = false;

        switch (type) {
            case "First":
            case "Stop100":
            case "Stop50": {
                midpoint = i;
                pivot = numbers[midpoint];
                break;
            }
            case "MedianOf3": {
                midpoint = i + (k - i) / 2;
                int[] medianOf3 = new int[3];
                medianOf3[0] = i;
                medianOf3[1] = midpoint;
                medianOf3[2] = k;
                InsertionSort.insertionSort(medianOf3, 3);
                pivot = numbers[medianOf3[1]];
                break;
            }
        }

        l = i;
        h = k;

        while (!done) {

         /* Increment l while numbers[l] < pivot */
            while (numbers[l] < pivot) {
                ++l;
            }

         /* Decrement h while pivot < numbers[h] */
            while (pivot < numbers[h]) {
                --h;
            }

         /* If there are zero or one items remaining,
            all numbers are partitioned. Return h */
            if (l >= h) {
                done = true;
            } else {
            /* Swap numbers[l] and numbers[h],
               update l and h */
                temp = numbers[l];
                numbers[l] = numbers[h];
                numbers[h] = temp;

                ++l;
                --h;
            }
        }
        return h;
    }

    /**
     * Iterative implementation of QuickSort sorting algorithm.
     * @param numbers Array of numbers to be sorted.
     * @param type type of QuickSort variation.
     *             Accepted values are: "First", "Stop50", "Stop100", "MedianOf3".
     */
    public static void quickSort(int[] numbers, String type) {
        int j = 0;
        Stack stack = new Stack();
        stack.push(0);
        stack.push(numbers.length - 1);

        while (!stack.isEmpty()) {
            int end = stack.pop();
            int start = stack.pop();

            if (end - start < 2) {
                continue;
            }

            switch (type) {
                case "Stop100": {
                    if (end - start <= 100) {
                        InsertionSort.insertionSort(numbers, end - start + 1);
                        continue;
                    }
                    break;
                }
                case "Stop50": {
                    if (end - start <= 50) {
                        InsertionSort.insertionSort(numbers, end - start + 1);
                        continue;
                    }
                    break;
                }
            }

            /* Partition the data within the array. Value j returned
               from partitioning is location of last item in low partition. */
            j = partition(numbers, start, end, type);

            stack.push(j + 1);
            stack.push(end);

            stack.push(start);
            stack.push(j);
        }
    }

    /**
     * Recursive QuickSort method.
     * @param numbers array of integers to be sorted
     * @param i start index
     * @param k end index
     * @param type type of QuickSort variation.
     *             Accepted values are: "First", "Stop50", "Stop100", "MedianOf3".
     */
    public static void recursiveQsort(int numbers[], int i, int k, String type) {
        int j = 0;

        /* Base case: If there are 1 or zero entries to sort,
           partition is already sorted */
        if (i >= k) {
            return;
        }

        /* Partition the data within the array. Value j returned
           from partitioning is location of last item in low partition. */
        j = partition(numbers, i, k, type);

        /* Recursively sort low partition (i to j) and
           high partition (j + 1 to k) */
        switch (type) {
            case "Stop100": {
                if (j - i > 100) {
                    recursiveQsort(numbers, i, j, type);
                } else {
                    InsertionSort.insertionSort(numbers, j - i + 1);
                }
                if (k - (j + 1) > 100) {
                    recursiveQsort(numbers, j + 1, k, type);
                } else {
                    InsertionSort.insertionSort(numbers, k - j + 1);
                }
                break;
            }
            case "Stop50": {
                if (j - i > 50) {
                    recursiveQsort(numbers, i, j, type);
                } else {
                    InsertionSort.insertionSort(numbers, j - i + 1);
                }
                if (k - (j + 1) > 50) {
                    recursiveQsort(numbers, j + 1, k, type);
                } else {
                    InsertionSort.insertionSort(numbers, k - j + 1);
                }
                break;
            }
            default: {
                recursiveQsort(numbers, i, j, type);
                recursiveQsort(numbers, j + 1, k, type);
                break;
            }
        }
    }
}
