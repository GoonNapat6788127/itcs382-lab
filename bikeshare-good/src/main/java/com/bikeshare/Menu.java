
package com.bikeshare;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final BikeShareService service;

    public Menu(Scanner scanner, BikeShareService service) {
        this.scanner = scanner;
        this.service = service;
    }

    public void run() {
        System.out.println("=== BikeShare ===");

        while (true) {
            System.out.println(
                    "\nCommands: rent, return, quit");
            System.out.print("> ");

            String command = scanner.nextLine().trim()
                    .toLowerCase();

            if (command.equals("quit")) {
                System.out.println("Goodbye!");
                return;
            }

            try {
                switch (command) {
                    case "rent" -> rentBike();
                    case "return" -> returnBike();
                    default -> System.out.println("Unknown command.");
                }
            } catch (IllegalArgumentException
                     | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Could not save ride: "
                        + e.getMessage());
            }
        }
    }

    private void rentBike() {
        System.out.print("Member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Member type (student/staff): ");
        MemberType type = MemberType.fromString(scanner.nextLine());

        System.out.print("Station (Library/Canteen/Dorm): ");
        String station = scanner.nextLine();

        String bikeId = service.rentBike(memberId, type, station);
        System.out.println("Bike rented successfully: " + bikeId);
    }

    private void returnBike() throws IOException {
        System.out.print("Bike ID: ");
        String bikeId = scanner.nextLine().trim();

        System.out.print("Return station (Library/Canteen/Dorm): ");
        String station = scanner.nextLine();

        System.out.print("Ride time in minutes: ");
        int minutes;

        try {
            minutes = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Ride time must be a whole number.");
        }

        Ride ride = service.returnBike(bikeId, station, minutes);

        System.out.println("\n--- Ride Receipt ---");
        System.out.println("Member: " + ride.getMemberId());
        System.out.println("Bike: " + ride.getBikeId());
        System.out.println("From: " + ride.getStartStation());
        System.out.println("To: " + ride.getReturnStation());
        System.out.println("Minutes: " + ride.getMinutes());
        System.out.println("Fee: " + ride.getFee() + " baht");
        System.out.println("Ride saved to rides.csv");
    }
}
