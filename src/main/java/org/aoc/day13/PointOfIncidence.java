package org.aoc.day13;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.*;

public class PointOfIncidence {
    public static final String INPUT_FILE = "src/main/resources/inputs/day13.txt";
    public static void main(String[] args) {
        String data = "";
        try {
            data = ReadFiles.readFileToString(INPUT_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //System.out.println(partOne(data));
        System.out.println(partTwo(data));
    }

    private static long partOne(String data) {
        List<String> lines = List.of(data.split(System.lineSeparator()));
        List<List<List<String>>> matrices = new ArrayList<>();

        List<List<String>> matrix = new ArrayList<>();
        lines = lines.subList(1, lines.size());
        for (String s : lines) {
            if (s.isEmpty()) {
                matrices.add(matrix);
                matrix = new ArrayList<>();
            } else{
                matrix.add(List.of(s.split("")));
            }
        }
        matrices.add(matrix);

        long result = 0;

        for (List<List<String>> m : matrices) {
            for (List<String> r : m) {
                System.out.println(r);
            }
            List<List<String>> transposed = transpose(m);
            System.out.println("Transposed");
            for (List<String> t : transposed) {
                System.out.println(t);
            }
            result += findHorizontalLinesOfReflection(m) * 100;
            result += findHorizontalLinesOfReflection(transposed);

            System.out.println();
        }

        return result;
    }

    private static long partTwo(String data) {
        List<String> lines = List.of(data.split(System.lineSeparator()));
        List<List<List<String>>> matrices = new ArrayList<>();

        List<List<String>> matrix = new ArrayList<>();
        lines = lines.subList(1, lines.size());
        for (String s : lines) {
            if (s.isEmpty()) {
                matrices.add(matrix);
                matrix = new ArrayList<>();
            } else{
                matrix.add(Arrays.asList(s.split("")));
            }
        }
        matrices.add(matrix);

        long result = 0;

        for (List<List<String>> m : matrices) {
            for (List<String> r : m) {
                System.out.println(r);
            }

            List<List<String>> transposed = transpose(m);
            System.out.println("Transposed");
            for (List<String> t : transposed) {
                System.out.println(t);
            }
            long preRepair = findHorizontalLinesOfReflection(m);
            long preRepairTransposed = findHorizontalLinesOfReflection(transpose(m));

            m = checkAndRepairSmudges(m);

            System.out.println("Fixed: ");
            for (List<String> r : m) {
                System.out.println(r);
            }

            long postRepair = findHorizontalLinesOfReflection(m);
            long postRepairTransposed = findHorizontalLinesOfReflection(transpose(m));
            //result += (preRepair * 100) + preRepairTransposed;
            result += (postRepair * 100) + postRepairTransposed;
//            System.out.println("Post Repair: " + postRepair);
//            if (preRepair != postRepair) {
//                result += (postRepair * 100) + postRepairTransposed;
//            } else {
//                result += (preRepair * 100) + preRepairTransposed;
//            }

            System.out.println();
        }

        return result;
    }

    private static List<List<String>> checkAndRepairSmudges(List<List<String>> m) {
        int l = 0;
        int r = m.size() - 1;

        while (l < r) {
            int index = compareLists(m.get(l), m.get(r));
            if (index != -1) {
                // found smudge
                System.out.println("smudge: " + m.get(l) + " " + index);
                m.get(l).set(index, m.get(r).get(index));
                return m;
            }
            l++;
            r--;
        }

        return m;
    }

    private static int compareLists(List<String> a, List<String> b) {
        boolean oneDiff = false;
        int index = 0;
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i))) {
                if (oneDiff) {
                    return -1;
                } else {
                    oneDiff = true;
                    index = i;
                }
            }
        }
        if (oneDiff) return index;

        return -1;
    }

    public static long findHorizontalLinesOfReflection(List<List<String>> matrix) {
        long result = 0;
        for (int i = 0; i < matrix.size() - 1; i++) {
            int rowsAbove = 0;
            if (matrix.get(i).equals(matrix.get(i + 1))) {
                System.out.println(matrix.get(i) + "\nEQUALS\n"  + matrix.get(i + 1));
                int j = i + 2;
                int k = i - 1;
                boolean isReflection = true;
                while(j < matrix.size() && k >= 0) {
                    if (!matrix.get(k).equals(matrix.get(j))) {
                        isReflection = false;
                        break;
                    }
                    j++;
                    k--;
                }
                if (isReflection) {
                    // Count num rows above line of reflection
                    rowsAbove = i + 1;
                    return rowsAbove;
                }
            }
            result += rowsAbove;
        }
        System.out.println(result);
        return result;
    }

    public static List<List<String>> transpose(List<List<String>> A) {
        List<List<String>> t = new ArrayList<>();
        for (int col = 0; col < A.get(0).size(); col++) {
            List<String> newRow = new ArrayList<>();
            for (int row = 0; row < A.size(); row++) {
                newRow.add(A.get(row).get(col));
            }
            t.add(newRow);
        }
        return t;
    }
}
