
package com.bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BikeShare {
    public static Scanner input = new Scanner(System.in);
    public static String[] bikes = {"L1", "L2", "C1", "C2", "D1", "D2"};
    public static String[] stations = {"Library", "Canteen", "Dorm"};
    public static boolean[] available = {true, true, true, true, true, true};
    public static String[] members = new String[6];
    public static int[] types = new int[6];
    public static int STUDENT = 1, STAFF = 2;

    public static void main(String[] args) {
        String command = "";
        while (!command.equalsIgnoreCase("quit")) {
            System.out.println("\nBikeShare: rent, return, quit");
            System.out.print("Command: ");
            command = input.nextLine().trim();

            if (command.equalsIgnoreCase("rent")) {
                process(false);
            } else if (command.equalsIgnoreCase("return")) {
                process(true);
            } else if (!command.equalsIgnoreCase("quit")) {
                System.out.println("Invalid command.");
            }
        }
        System.out.println("Goodbye!");
    }

    public static void process(boolean returning) {
        if (!returning) {
            System.out.print("Member ID: ");
            String member = input.nextLine().trim();
            System.out.print("Type (1=student, 2=staff): ");
            int type = readNumber();
            if (member.isEmpty() || (type != STUDENT && type != STAFF)) {
                System.out.println("Invalid member information.");
                return;
            }

            System.out.println("Stations: 1=Library, 2=Canteen, 3=Dorm");
            System.out.print("Station: ");
            int station = readNumber();
            if (station < 1 || station > 3) {
                System.out.println("Invalid station.");
                return;
            }

            for (int i = 0; i < bikes.length; i++) {
                if (available[i] && i / 2 == station - 1) {
                    available[i] = false;
                    members[i] = member;
                    types[i] = type;
                    System.out.println("Bike " + bikes[i] + " rented by " + member);
                    return;
                }
            }
            System.out.println("No bikes available at " + stations[station - 1] + ".");
        } else {
            System.out.print("Bike ID: ");
            String bike = input.nextLine().trim();
            int index = -1;
            for (int i = 0; i < bikes.length; i++) {
                if (bikes[i].equalsIgnoreCase(bike)) {
                    index = i;
                    break;
                }
            }

            if (index == -1 || available[index]) {
                System.out.println("Invalid bike or bike is not rented.");
                return;
            }

            System.out.println("Return stations: 1=Library, 2=Canteen, 3=Dorm");
            System.out.print("Return station: ");
            int station = readNumber();
            if (station < 1 || station > 3) {
                System.out.println("Invalid station.");
                return;
            }

            System.out.print("Ride time in minutes: ");
            int minutes = readNumber();
            if (minutes <= 0) {
                System.out.println("Ride time must be positive.");
                return;
            }

            int fee = minutes * (types[index] == STUDENT ? 2 : 3);
            available[index] = true;

            System.out.println("\n--- BikeShare Receipt ---");
            System.out.println("Member: " + members[index]);
            System.out.println("Bike: " + bikes[index]);
            System.out.println("Return station: " + stations[station - 1]);
            System.out.println("Minutes: " + minutes);
            System.out.println("Fee: " + fee + " baht");
            saveRide(members[index], bikes[index], stations[station - 1],
                    minutes, fee);
            members[index] = null;
        }
    }

    public static int readNumber() {
        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void saveRide(String member, String bike, String station,
                                int minutes, int fee) {
        try (FileWriter writer = new FileWriter("rides.csv", true)) {
            writer.write(member + "," + bike + "," + station + ","
                    + minutes + "," + fee + "\n");
        } catch (IOException e) {
            System.out.println("Could not save ride: " + e.getMessage());
        }
    }
}
