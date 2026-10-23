package parcelpoint;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class ParcelService {
    private List<Locker> lockers = new ArrayList<>();
    private List<Parcel> parcels = new ArrayList<>();
    

    public ParcelService() {
        lockers.add(new Locker(1, ParcelSize.SMALL));
        lockers.add(new Locker(2, ParcelSize.MEDIUM));
        lockers.add(new Locker(3, ParcelSize.LARGE));
    }

    public List<Locker> getLockers() {
        return lockers;
    }

    public Locker storeParcel(Parcel parcel) {
        for (Locker locker : lockers) {
            if (locker.isFree() && parcel.getSize().fitsIn(locker.getSize())) {
                locker.setParcel(parcel);
                parcel.status = "STORED";
                parcels.add(parcel);
                System.out.println("SMS to " + parcel.getStudentId() + ": parcel "
                        + parcel.getCode() + " is in locker " + locker.getNumber());
                saveToFile();
                return locker;
            }
        }
        return null;
    }

    public double pickUp(String code, int days) {
        for (Locker locker : lockers) {
            Parcel parcel = locker.getParcel();
            if (parcel != null && parcel.getCode().equals(code)) {
                locker.setParcel(null);
                parcel.status = "PICKED_UP";
                saveToFile();
                return fee(parcel, days);
            }
        }
        return 0;
    }

    public double fee(Parcel parcel, int days) {
        if (days <= 3) {
            return 0;
        }
        return (days - 3) * ratePerDay(parcel.getSize());
    }

    private double ratePerDay(ParcelSize size) {
        return switch (size) {
            case SMALL -> 10.0;
            case MEDIUM -> 15.0;
            case LARGE -> 20.0;
        };
    }

    public void printStaffList() {
    for (Parcel parcel : parcels) {
        System.out.println(parcel.getCode() + " " + parcel.getStudentId() + " " + parcel.status);
    }
}

public void printWaitingList() {
    for (Parcel parcel : parcels) {
        if (parcel.status.equals("STORED")) {
            System.out.println(parcel.getCode() + " is waiting");
        }
    }
}

    private void saveToFile() {
        try (FileWriter writer = new FileWriter("parcels.csv")){
            for (Parcel parcel : parcels) {
                writer.write(parcel.getCode() + "," + parcel.status + "\n");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
