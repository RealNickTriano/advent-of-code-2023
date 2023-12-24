package org.aoc.day24;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class NeverTellMeTheOdds {
    public static final String INPUT_FILE = "src/main/resources/inputs/day24.txt";

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

    private static long partOne(String data) {
        String[] lines = data.split(System.lineSeparator());
        List<List<List<Long>>> hails = Arrays.stream(lines)
                .map(line -> Arrays.stream(line.split(" @ "))
                        .map(item -> Arrays.stream(item.split(", "))
                                .map(val -> Long.parseLong(val.strip()))
                                .toList())
                        .toList())
                .toList();

        System.out.println(hails);

        long lowerBound = 200000000000000L;
        long upperBound = 400000000000000L;
        long result = 0;

        for (int i = 0; i < hails.size(); i++) {
            List<List<Long>> hail1 = hails.get(i);
            Hail o1 = new Hail(hail1.get(0).get(0), hail1.get(0).get(1), hail1.get(0).get(2),
                    hail1.get(1).get(0), hail1.get(1).get(1), hail1.get(1).get(2));
            for (int j = i + 1; j < hails.size(); j++) {
                List<List<Long>> hail2 = hails.get(j);
                Hail o2 = new Hail(hail2.get(0).get(0), hail2.get(0).get(1), hail2.get(0).get(2),
                        hail2.get(1).get(0), hail2.get(1).get(1), hail2.get(1).get(2));

                if (checkIntersection(o1, o2, lowerBound, upperBound)) {
                    System.out.println("Intersection with: " + o1 + "\n\t" + o2);
                    result++;
                }
            }
        }

        return result;
    }

    private static boolean checkIntersection(Hail o1, Hail o2, long lowerBound, long upperBound) {

        if (((o1.velocityY /  o1.velocityX) - (o2.velocityY /  o2.velocityX)) == 0) {
            System.out.println("Same Slope:\n" + o1 + "\n" + o2);
            return false;
        }

        double b1 = (o1.posY - (((double) o1.velocityY / (double) o1.velocityX) * o1.posX));
        //System.out.println("Slope 1: " + ((double) o1.velocityY / (double) o1.velocityX));
        double b2 = o2.posY - (((double) o2.velocityY / (double) o2.velocityX) * o2.posX);
        System.out.println("y intercepts: " + b1 + " " + b2);
        double x = (b2 - b1) / (((double) o1.velocityY /  (double) o1.velocityX) - ((double) o2.velocityY /  (double) o2.velocityX));
        double y = ((double) o1.velocityY / (double) o1.velocityX) * x + b1;
        System.out.println("Intersection: " + x + ", " + y);

        // check vector direction and if intersection is other side of vector
        if (o1.velocityX < 0 && o1.velocityY < 0) {
            // Going left and down
            if (!(x < o1.posX) && !(y < o1.posY)) return false;
        } else if (o1.velocityX > 0 && o1.velocityY < 0) {
            // Going right and down
            if (!(x > o1.posX) && !(y < o1.posY)) return false;
        }  else if (o1.velocityX < 0 && o1.velocityY > 0) {
            // Going left and up
            if (!(x < o1.posX) && !(y > o1.posY)) return false;
        }  else if (o1.velocityX > 0 && o1.velocityY > 0) {
            // Going right and up
            if (!(x > o1.posX) && !(y > o1.posY)) return false;
        }

        if (o2.velocityX < 0 && o2.velocityY < 0) {
            // Going left and down
            if (!(x < o2.posX) && !(y < o2.posY)) return false;
        } else if (o2.velocityX > 0 && o2.velocityY < 0) {
            // Going right and down
            if (!(x > o2.posX) && !(y < o2.posY)) return false;
        }  else if (o2.velocityX < 0 && o2.velocityY > 0) {
            // Going left and up
            if (!(x < o2.posX) && !(y > o2.posY)) return false;
        }  else if (o2.velocityX > 0 && o2.velocityY > 0) {
            // Going right and up
            if (!(x > o2.posX) && !(y > o2.posY)) return false;
        }

        // If intersection point is in bounds then true, otherwise false
        if (x >= lowerBound && x <= upperBound && y >= lowerBound && y <= upperBound){
            System.out.println("in bounds");
            return true;
        } else
            return false;
    }

    static class Hail {
        public double posX;
        public double posY;
        public double posZ;
        public double velocityX;
        public double velocityY;
        public double velocityZ;

        public Hail(double posX,
                    double posY,
                    double posZ,
                    double velocityX,
                    double velocityY,
                    double velocityZ) {

            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.velocityZ = velocityZ;
        }

        public String toString() {
            return "posX: " + posX
                    + " posY: " + posY
                    +" posZ: " + posZ
                    +" velocityX: " + velocityX
                    +" velocityY: " + velocityY
                    +" velocityZ: " + velocityZ;
        }
    }
}
