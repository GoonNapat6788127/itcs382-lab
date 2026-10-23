package parcelpoint;

public class Parcel {
    private String code;
    private String studentId;
    private ParcelSize size;          // 1 = small, 2 = medium, 3 = large
    public String status;

    public Parcel(String code, String studentId, ParcelSize size) {
        this.code = code;
        this.studentId = studentId;
        this.size = size;
        this.status = "NEW";
    }

    public String getCode() {
        return code;
    }

    public String getStudentId() {
        return studentId;
    }

    public ParcelSize getSize() {
        return size;
    }
}
