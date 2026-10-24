
package com.bikeshare;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class BikeShareService {
    private final Map<String, Station> stations = new LinkedHashMap<>();
    private final Map<String, Rental> activeRentals = new LinkedHashMap<>();
    private final RideRepository rideRepository;
    private int nextBikeId = 1;

    private record Rental(
            String memberId,
            MemberType memberType,
            Bike bike,
            String startStation) {}

    public BikeShareService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;

        for (String name : new String[]{"Library", "Canteen", "Dorm"}) {
            Station station = new Station(name);
            station.addBike(createBike());
            station.addBike(createBike());
            stations.put(name.toLowerCase(), station);
        }
    }

    private Bike createBike() {
        return new Bike("B" + nextBikeId++);
    }

    private Station findStation(String name) {
        Station station = stations.get(name.trim().toLowerCase());
        if (station == null) {
            throw new IllegalArgumentException(
                    "Unknown station. Use Library, Canteen or Dorm.");
        }
        return station;
    }

    public String rentBike(String memberId, MemberType memberType,
                           String stationName) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("Member ID cannot be empty.");
        }
        if (memberType == null) {
            throw new IllegalArgumentException("Invalid member type.");
        }

        Station station = findStation(stationName);
        Bike bike = station.rentBike();

        activeRentals.put(bike.getId(),
                new Rental(memberId.trim(), memberType, bike,
                        station.getName()));

        return bike.getId();
    }

    public Ride returnBike(String bikeId, String stationName, int minutes)
            throws IOException {
        if (minutes <= 0) {
            throw new IllegalArgumentException(
                    "Ride time must be greater than zero.");
        }

        Station returnStation = findStation(stationName);
        Rental rental = activeRentals.get(bikeId.trim());

        if (rental == null) {
            throw new IllegalArgumentException(
                    "Bike not found in active rentals.");
        }

        int fee = minutes * rental.memberType().getFeePerMinute();

        Ride ride = new Ride(
                rental.memberId(),
                rental.memberType(),
                rental.bike().getId(),
                rental.startStation(),
                returnStation.getName(),
                minutes,
                fee);

        rideRepository.save(ride);
        activeRentals.remove(bikeId.trim());
        returnStation.addBike(rental.bike());

        return ride;
    }
}
