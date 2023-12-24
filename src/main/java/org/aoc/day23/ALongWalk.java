package org.aoc.day23;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ALongWalk {
    public static final String INPUT_FILE = "src/main/resources/inputs/day23.txt";

    public static void main(String[] args) {
        String data = "";
        try {
            data = ReadFiles.readFileToString(INPUT_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(partOne(data));
        //System.out.println(partTwo(data));
    }

    private static int partOne(String data) {
        List<List<String>> matrix = Arrays.stream(data.split(System.lineSeparator()))
                .map(item -> Arrays.stream(item.split(""))
                        .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));

        List<List<Integer>> directions = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(0, -1),
                Arrays.asList(1, 0), Arrays.asList(-1, 0));

        List<List<Integer>> queue = new ArrayList<>();
        List<List<Integer>> seen = new ArrayList<>();
        queue.add(Arrays.asList(0, 1));
        seen.add(Arrays.asList(0, 1));

        Map<List<Integer>, Node> graph = new HashMap<>();

        while (!queue.isEmpty()) {
            List<Integer> current = queue.removeFirst();
            Node node = new Node(current.get(0), current.get(1));
            graph.put(Arrays.asList(node.row, node.col), node);

            for (List<Integer> direction : directions) {
                List<Integer> newPos = Arrays.asList(current.get(0) + direction.get(0),
                        current.get(1) + direction.get(1));

                if (nodeIsValid(newPos.get(0), newPos.get(1), matrix.size(), matrix.get(0).size(), matrix)) {
                    if (!seen.contains(newPos)) {
                        node.edges.add(Arrays.asList(newPos.get(0), newPos.get(1)));
                        queue.add(newPos);
                        seen.add(newPos);
                    }
                }
            }
        }
        System.out.println(seen);
        System.out.println(graph);

//
//        Map<List<Integer>, Node> graph = new HashMap();
//        for (int i = 0; i < matrix.size(); i++) {
//            for (int j = 0; j < matrix.get(i).size(); j++) {
//                if (!matrix.get(i).get(j).equals("#")) {
//                    Node node = new Node(i, j);
//                    graph.put(Arrays.asList(node.row, node.col), node);
//                    for (List<Integer> direction : directions) {
//                        int row = i + direction.get(0);
//                        int col = j + direction.get(1);
//
//                        if (nodeIsValid(row, col, matrix.size(), matrix.get(0).size(), matrix)) {
//                            node.edges.add(new Node(row, col));
//                        }
//                    }
//                }
//
//            }
//        }
//        System.out.println(graph);



        return 0;
    }

    private static boolean nodeIsValid(int row, int col, int rowSize, int colSize, List<List<String>> matrix) {
        if (row < 0 || col < 0 || row >= rowSize || col >= colSize) {
            return false;
        }
        if (matrix.get(row).get(col).equals("#")) {
            return false;
        }

        return true;
    }
}
