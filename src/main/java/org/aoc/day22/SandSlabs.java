package org.aoc.day22;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.*;

public class SandSlabs {
    public static final String INPUT_FILE = "src/main/resources/inputs/day22.txt";

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
        List<Brick> bricksList = new ArrayList<>();
        int id = 1;

        String[] lines = data.split(System.lineSeparator());
        for (String line : lines) {
            String[] startEnd = line.split("~");
            List<Integer> start = Arrays.stream(startEnd[0].split(","))
                    .map(Integer::parseInt).toList();
            List<Integer> end = Arrays.stream(startEnd[1].split(","))
                    .map(Integer::parseInt).toList();

            bricksList.add(new Brick(id++, start.get(0), start.get(1), start.get(2), end.get(0), end.get(1), end.get(2)));
        }

        int maxX = bricksList.stream().map(brick -> brick.endX).reduce(Integer.MIN_VALUE, Integer::max) + 1;
        int maxY = bricksList.stream().map(brick -> brick.endY).reduce(Integer.MIN_VALUE, Integer::max) + 1;
        int maxZ = bricksList.stream().map(brick -> brick.endZ).reduce(Integer.MIN_VALUE, Integer::max) + 1;


        int[][][] matrix3D = new int[maxX][maxY][maxZ];
        for (Brick brick: bricksList) {
            System.out.println(brick);
            //matrix3D[brick.startX][brick.startY][brick.startZ] = brick.id;

            for (int i = 0; i <= brick.endX - brick.startX; i++) {
                matrix3D[brick.startX + i][brick.startY][brick.startZ] = brick.id;
            }
            for (int i = 0; i <= brick.endY - brick.startY; i++) {
                matrix3D[brick.startX][brick.startY + i][brick.startZ] = brick.id;
            }
            for (int i = 0; i <=  brick.endZ - brick.startZ ; i++) {
                matrix3D[brick.startX][brick.startY][brick.startZ + i] = brick.id;
            }
        }
        System.out.println(matrix3D[1][1][8]);
        for (int[][] xy : matrix3D) {
            System.out.println(Arrays.toString(xy));
            for (int[] x : xy) {
                System.out.println(Arrays.toString(x));
            }
        }
        bricksList.sort(Comparator.comparingInt(a -> a.startZ));
        System.out.println(bricksList);
        for (int i = 0; i < bricksList.size(); i++) {
            Brick currentBrick = bricksList.get(i);
            System.out.println("Checking below: " + currentBrick);
            while (true) {
                if (currentBrick.startZ == 1) {
                    // brick on floor
                    System.out.println("\tBrick on floor");
                    break;
                } else if (isBrickBelow(currentBrick, matrix3D)) {
                    // Brick on top of brick below
                    System.out.println("\tBrick on top of other brick");
                    break;
                } else {
                    for (int k = currentBrick.startX; k <= currentBrick.endX ; k++) {
                        for (int q = currentBrick.startY; q <= currentBrick.endY; q++) {
                            for (int j = currentBrick.startZ; j <= currentBrick.endZ; j++) {
                                matrix3D[k][q][j] = 0;
                                //System.out.println("Setting " + );
                            }
                        }
                    }
                    System.out.println(currentBrick.endZ);
                    currentBrick.endZ -= 1;
                    currentBrick.startZ -= 1;
                    System.out.println(currentBrick.endZ);
                    for (int k = currentBrick.startX; k <= currentBrick.endX ; k++) {
                        for (int q = currentBrick.startY; q <= currentBrick.endY; q++) {
                            for (int j = currentBrick.startZ; j <= currentBrick.endZ; j++) {
                                matrix3D[k][q][j] = currentBrick.id;
                            }
                        }
                    }

                    System.out.println("\tmoving brick down");
                }
            }
        }
        int result = 0;
        List<List<Integer>> brickIds = new ArrayList<>();
        for (Brick brick: bricksList) {
            System.out.println(brick);
            brickIds.addAll(supportsBricks(brick, matrix3D));
        }
        Map<Integer, Set<Integer>> brickToSupportedBy = new HashMap<>();
        Map<Integer, Set<Integer>> supportToBrick = new HashMap<>();
        for (List<Integer> item : brickIds) {
            if (brickToSupportedBy.containsKey(item.get(1))) {
                Set<Integer> newList = new HashSet<>(brickToSupportedBy.get(item.get(1)));
                newList.add(item.get(0));
                brickToSupportedBy.put(item.get(1), newList);
            } else {
                Set<Integer> mySet = new HashSet<>();
                mySet.add(item.get(0));
                brickToSupportedBy.put(item.get(1), mySet);
            }
            if (supportToBrick.containsKey(item.get(0))) {
                Set<Integer> newList = new HashSet<>(supportToBrick.get(item.get(0)));
                newList.add(item.get(1));
                supportToBrick.put(item.get(0), newList);
            } else {
                Set<Integer> mySet = new HashSet<>();
                mySet.add(item.get(1));
                supportToBrick.put(item.get(0), mySet);
            }
        }
        System.out.println("Brick to Support: " + brickToSupportedBy);
        System.out.println("Support to Brick: " + supportToBrick);
        System.out.println(brickIds);
        Set<Integer> disintegrated = new HashSet<>();

        for (Integer key: brickToSupportedBy.keySet()) {
            if (brickToSupportedBy.get(key).size() > 1) {
                // supported by multiple bricks
                for (Integer b : brickToSupportedBy.get(key)) {
                    Set<Integer> bricksSupportedByB = supportToBrick.get(b);
                    //System.out.println(bricksSupportedByB);
                    boolean supported = true;
                    for (Integer j : bricksSupportedByB) {
                        if (brickToSupportedBy.get(j).size() < 2) {
                            supported = false;
                        }
                    }
                    if (supported) {
                        System.out.println(b);
                        disintegrated.add(b);
                    }
                }
            }
        }
        System.out.println(disintegrated.stream().toList().stream().sorted().toList());
        return disintegrated.size() + 1;
    }

    private static boolean isBrickBelow(Brick currentBrick, int[][][] matrix3D) {
        int lowerZ = currentBrick.startZ - 1;
        int[] xRange = new int[] {currentBrick.startX, currentBrick.endX};
        int[] yRange = new int[] {currentBrick.startY, currentBrick.endY};
        for (int j = xRange[0]; j <= xRange[1]; j++) {
            for (int k = yRange[0]; k <= yRange[1] ; k++) {
                if (matrix3D[j][k][lowerZ] != 0) {
                    // a brick is here, thus we are currently on top of it
                    System.out.println("Brick is here: " + matrix3D[j][k][lowerZ]);
                    System.out.println("\t" + j + " " + k + " " + lowerZ);
                    return true;
                }
                //break;
            }
        }
        return false;
    }

    // Check if any bricks are on same level and under same brick
    private static List<List<Integer>> supportsBricks(Brick currentBrick, int[][][] matrix3D) {
        List<List<Integer>> brickIdsBelow = new ArrayList<>();
        int lowerZ = currentBrick.startZ - 1;
        int[] xRange = new int[] {currentBrick.startX, currentBrick.endX};
        int[] yRange = new int[] {currentBrick.startY, currentBrick.endY};
        for (int j = xRange[0]; j <= xRange[1]; j++) {
            for (int k = yRange[0]; k <= yRange[1] ; k++) {
                if (matrix3D[j][k][lowerZ] != 0) {
                    // { ID-supporting, Z-level, ID-self lowerZ,
                    brickIdsBelow.add(Arrays.asList(matrix3D[j][k][lowerZ], currentBrick.id));
                }
                //break;
            }
        }
        // If > 1 brick is below, add all bricks as possible disintegration
        return brickIdsBelow;
    }

    static class Brick {
        public int id;
        public int startX;
        public int startY;
        public int startZ;
        public int endX;
        public int endY;
        public int endZ;

        public Brick(int id,
                     int startX,
                     int startY,
                     int startZ,
                     int endX,
                     int endY,
                     int endZ) {

            this.id = id;
            this.startX = startX;
            this.startY = startY;
            this.startZ = startZ;
            this.endX = endX;
            this.endY = endY;
            this.endZ = endZ;
        }

        public String toString() {
            return "Brick[" + "id=" + this.id + " startX=" + this.startX
                    + " startY=" + this.startY
                    + " startZ=" + this.startZ
                    + " endX=" + this.endX
                    + " endY=" + this.endY
                    + " endZ=" + this.endZ
                    +"]";
        }
    }
}
