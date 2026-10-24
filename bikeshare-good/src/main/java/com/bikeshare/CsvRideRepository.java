
package com.bikeshare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CsvRideRepository implements RideRepository {
    private final Path file;

    public CsvRideRepository(Path file) {
        this.file = file;
    }

    @Override
    public void save(Ride ride) throws IOException {
        boolean writeHeader = !Files.exists(file)
                || Files.size(file) == 0;

        try (BufferedWriter writer = Files.newBufferedWriter(
                file,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            if (writeHeader) {
                writer.write("member_id,member_type,bike_id,"
                        + "start_station,return_station,minutes,fee");
                writer.newLine();
            }

            writer.write(String.join(",",
                    ride.getMemberId(),
                    ride.getMemberType().name(),
                    ride.getBikeId(),
                    ride.getStartStation(),
                    ride.getReturnStation(),
                    String.valueOf(ride.getMinutes()),
                    String.valueOf(ride.getFee())));
            writer.newLine();
        }
    }
}
