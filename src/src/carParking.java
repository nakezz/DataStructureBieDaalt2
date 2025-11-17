import java.io.*;
import java.util.*;

public class carParking {
    static final int MAX_CAPACITY = 10;
    static Stack<car> garage = new Stack<>();

    public static void main(String[] args) {
        input("/home/nake/BieDaalt2/DataStructureBieDaalt2/src/src/cars.txt");
    }
    public static void input(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                process(line.trim());
            }
        } catch (IOException e) {
            System.out.println("There is error occured while trying to read the file : " + e.getMessage());
        }
    }
    public static void process(String line) {
        if (line.isEmpty()) return;

        String[] parts = line.split(" ");
        if (parts.length != 2) return;

        String action = parts[0];
        String plate = parts[1];
        car car = new car(plate);

        if (action.equals("A")) {
            if (garage.size() < MAX_CAPACITY) {
                garage.push(car);
                System.out.println("Arrival " + plate + " -> There is room.");
            } else {
                System.out.println("Arrival " + plate + " -> Garage full, this car cannot enter.");
            }
        } else if (action.equals("D")) {
            if (garage.isEmpty()) {
                System.out.println("Departure " + plate + " -> This car not in the garage.");
                return;
            }

            Stack<car> temp = new Stack<>();
            boolean found = false;
            int moved = 0;

            while (!garage.isEmpty()) {
                car top = garage.pop();
                if (top.plateNumber.equals(plate)) {
                    found = true;
                    break;
                } else {
                    temp.push(top);
                    moved++;
                }
            }

            if (found) {
                System.out.println("Departure " + plate + " -> " + moved + " cars moved out.");
                while (!temp.isEmpty()) {
                    garage.push(temp.pop());
                }
            } else {
                while (!temp.isEmpty()) {
                    garage.push(temp.pop());
                }
                System.out.println("Departure " + plate + " -> This car not in the garage.");
            }
        }
    }
}