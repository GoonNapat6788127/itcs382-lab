
package com.bikeshare;

public class Ride {
    private final String memberId;
    private final MemberType memberType;
    private final String bikeId;
    private final String startStation;
    private final String returnStation;
    private final int minutes;
    private final int fee;

    public Ride(String memberId, MemberType memberType, String bikeId,
                String startStation, String returnStation,
                int minutes, int fee) {
        this.memberId = memberId;
        this.memberType = memberType;
        this.bikeId = bikeId;
        this.startStation = startStation;
        this.returnStation = returnStation;
        this.minutes = minutes;
        this.fee = fee;
    }

    public String getMemberId() {
        return memberId;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getReturnStation() {
        return returnStation;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getFee() {
        return fee;
    }
}
