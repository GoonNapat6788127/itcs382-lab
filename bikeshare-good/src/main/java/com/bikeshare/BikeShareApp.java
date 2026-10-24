
package com.bikeshare;

import java.nio.file.Path;
import java.util.Scanner;

public class BikeShareApp {
    public static void main(String[] args) {
        RideRepository repository =
                new CsvRideRepository(Path.of("rides.csv"));

        BikeShareService service = new BikeShareService(repository);

        try (Scanner scanner = new Scanner(System.in)) {
            Menu menu = new Menu(scanner, service);
            menu.run();
        }
    }
}
