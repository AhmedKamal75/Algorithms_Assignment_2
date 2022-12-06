import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Main {
    private static HashMap<Integer, Integer> memo1 = new HashMap<>();
    private static HashMap<Integer, Integer> memo2 = new HashMap<>();


//    # # # # # test
// 3
// 1 2 1
// 2 3 2
// 3 4 5 ==> 8


// 8
// 0 3 3
// 1 4 2
// 0 5 4
// 3 6 1
// 4 7 2
// 3 9 5
// 5 10 2
// 8 10 1 ==> 8


    public static void main(String[] args) {
        memo1.put(0, 0);
        String inputPath = args[0];
        int[][] list = readFile(inputPath);
        int result = activitySelectionDynamicProgramming(list);

        String outputPath = inputPath;
        outputPath = outputPath.replaceAll("\\.txt", "_17010210_output.txt");
        System.out.println(inputPath);
        System.out.println(outputPath);

        writeToFile(outputPath, String.valueOf(result));

    }

    // gets the job closest to job k.
    // k is rank, and it returns a rank
    // works.
    private static int p(int[][] list, int key) {
        return pTemp(list, key - 1);
    }

    private static int pTemp(int[][] list, int k) {
        if (memo2.containsKey(k)) {
            return memo2.get(k);
        }
        int result = 0;
        for (int i = k - 1; i >= 0; i--) {
            if (list[i][1] <= list[k][0]) { // compatible
                result = i + 1;
                break;
            }
        }
        memo2.put(k, result);
        return result;
    }

    private static int optimal(int[][] list, int key) {
        if (memo1.containsKey(key)) {
            return memo1.get(key);
        }
        for (int k = 1; k <= key; k++) {
            memo1.put(k, Math.max(list[k - 1][2] + optimal(list, p(list, k)), optimal(list, k - 1)));
        }
        return memo1.get(key);
    }


    private static int activitySelectionDynamicProgramming(int[][] list) {
        if (list == null || list.length < 1) {
            return -1;
        }
        Arrays.sort(list, Comparator.comparingInt(obj -> obj[1]));
        return optimal(list, list.length);
    }


    private static int[][] readFile(String path) {
        int[][] list = null;

        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                if (index == 0) {
                    int n = Integer.parseInt(line.strip());
                    list = new int[n][3];
                } else {
                    String[] info = line.strip().split(" ");
                    for (int i = 0; i < 3; i++) {
                        list[index - 1][i] = Integer.parseInt(info[i]);
                    }
//                    list[index - 1][1] = Integer.parseInt(info[1]);
//                    list[index - 1][2] = Integer.parseInt(info[2]);
                }
                index++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    private static void writeToFile(String path, String data) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(path));
            bf.write(data);
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}