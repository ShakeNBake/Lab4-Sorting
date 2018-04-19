import java.io.*;
import java.util.Scanner;

/**
 * Enumeration of QuickSort type variations. See QuickSort class documentation
 * for further details.
 */
enum SortType {First, Stop100, Stop50, MedianOf3, NaturalMerge}

/**
 * Main program class.
 * Runs a folder of data files through 4 QuickSorts and a Natural Merge sort.
 * Timing of each sort is recorded in summary.txt
 * In addition, the result of each sort is written to a _sortType.output file
 * (to validate sort algo correctness, for example).
 */
public class SortAlgoBenchmark {
    /**
     * Main program entry point
     * @param args Expects one argument: path to folder with data files to sort/analyze.
     */
    public static void main(String [] args) {
        if (args.length < 1) {
            System.out.println("Usage: dataFolderPath");
            System.exit(1);
        }
        final File dir = new File(args[0]);
        final File summary = new File(dir + "/summary.txt");

        //take files that end with ".dat"
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".dat"));
        final int iterations = 27;

        SortType allTypes[] = SortType.values();
        System.out.println("# of data files: " + files.length);
        String header = "FileName,Count,First,Stop100,Stop50,MedianOf3,NaturalMerge";
        writeOutput(header,summary);

        for (File file:files) {
            StringBuilder record = new StringBuilder(file + "," + countItems(file));
            System.out.print("Processing file: " + file + " ; ");
            System.out.println("# of items: " + countItems(file));

            int[] numbers = loadSimData(file);

            for (SortType type:allTypes) {
                double resultInMillis = runSortBenchmark(numbers, type, iterations);
                record = record.append(",").append(resultInMillis);
            }
            writeOutput(record.toString(), summary);
        }
    }

    /**
     * Method executes a sort algo specified by SortType on an array of
     * integers a given number of times and returns an average time it took
     * to sort, in milliseconds.
     * @param numbers array to be sorted
     * @param type type of sort algorithm to apply
     * @param iterations number of sort iterations required
     * @return average time to sort, in milliseconds
     */
    private static double runSortBenchmark(int[] numbers, SortType type, int iterations) {
        long start, finish;
        double elapsed = 0.0;
//        StringBuilder str = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            if (type.equals(SortType.NaturalMerge)) {
                Node list = arrayToList(numbers);
                start = System.currentTimeMillis();
                Node sortedList = NaturalMergeSort.mergeSort(list);
                finish = System.currentTimeMillis();
//                    Node p = sortedList;
//                    while (p != null) {
//                        str.append(p.number).append("\n");
//                        p = p.next;
//                    }
            } else {
                int[] numCopy = new int[numbers.length];
                System.arraycopy(numbers,0,numCopy,0,numbers.length);
                start = System.currentTimeMillis();
                QuickSort.quickSort(numCopy, type.toString());
                finish = System.currentTimeMillis();
            }
            elapsed += (finish - start);
//                    for (int number : numCopy) {
//                        str.append(number).append("\n");
//                    }
        }
//                writeOutput(str.toString(),
//                        new File(file + "_" + type.toString() + ".output"));
        elapsed /= iterations;          //avg sort time in millis
        return elapsed;

    }

    /**
     * Utility method to  build a linked list from an array.
     * @param intArray Array of integers
     * @return A pointer to a linked list of integers.
     */
    private static Node arrayToList (int[] intArray) {
        Node n = new Node(intArray[0],null);
        Node list = n;
        for (int i = 1; i < intArray.length; i++) {
            n.next = new Node (intArray[i], null);
            n = n.next;
        }
        return list;
    }

    /**
     * Load array of numbers from a file.
     * @param fInput File object of data file.
     * @return Array of integers.
     */
    private static int[] loadSimData (File fInput) {
        int[] numbers;
        int count = countItems(fInput);
        numbers = new int[count];
        try (Scanner input = new Scanner(new FileReader(fInput)))
        {
            for (int i = 0; i < numbers.length; i++)
            {
                numbers[i] = input.nextInt();
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File Not Found: " + e);
            System.exit(1);
        }
        return numbers;
    }

    /**
     * Utility to count lines in the input file. Assumes
     * @param fInput a File to count line in.
     * @return number of items in a file
     */
    private static int countItems(File fInput) {
        int cnt = 0;
        try (Scanner input = new Scanner(new FileReader(fInput))) {
            while (input.hasNext()) {
                input.next();
                cnt++;
            }
        }
        catch (IOException ioe) {
            cnt = -1;
        }
        return cnt;
    }

    /**
     * Routine to append a String to a text file
     * @param str String to be appended to the file
     * @param fName File object to be written to.
     */
    private static void writeOutput(String str, File fName) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(
                new FileWriter(fName, true)))) {
            pw.println(str);
        }
        catch (IOException ioe) {
            System.out.println("IO Exception.");
            System.out.println(ioe.getMessage());
        }
    }
}
