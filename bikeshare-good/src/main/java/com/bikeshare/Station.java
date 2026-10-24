
package com.bikeshare;

import java.util.ArrayList;
import java.util.List;

public class Station {
    private final String name;
    private final List<Bike> availableBikes = new ArrayList<>();

    public Station(String name) {
        this.name = name;
    }

    public void addBike(Bike bike) {
        availableBikes.add(bike);
    }

    public Bike rentBike() {
        if (availableBikes.isEmpty()) {
            throw new IllegalStateException(
                    "No bikes available at " + name);
        }
        return availableBikes.remove(0);
    }

    public String getName() {
        return name;
    }
}
