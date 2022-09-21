package model;

public class BookingDetails {
    private String bookingId;
    private String roomId;
    private String guestId;
    private String checkingInDate;
    private String checkingInTime;
    private String checkingOutDate;
    private String checkingOutTime;
    private Double cost;

    public BookingDetails() {
    }

    public BookingDetails(String bookingId, String roomId, String guestId, String checkingInDate, String checkingInTime, String checkingOutDate, String checkingOutTime, Double cost) {
        this.setBookingId(bookingId);
        this.setRoomId(roomId);
        this.setGuestId(guestId);
        this.setCheckingInDate(checkingInDate);
        this.setCheckingInTime(checkingInTime);
        this.setCheckingOutDate(checkingOutDate);
        this.setCheckingOutTime(checkingOutTime);
        this.setCost(cost);
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getCheckingInDate() {
        return checkingInDate;
    }

    public void setCheckingInDate(String checkingInDate) {
        this.checkingInDate = checkingInDate;
    }

    public String getCheckingInTime() {
        return checkingInTime;
    }

    public void setCheckingInTime(String checkingInTime) {
        this.checkingInTime = checkingInTime;
    }

    public String getCheckingOutDate() {
        return checkingOutDate;
    }

    public void setCheckingOutDate(String checkingOutDate) {
        this.checkingOutDate = checkingOutDate;
    }

    public String getCheckingOutTime() {
        return checkingOutTime;
    }

    public void setCheckingOutTime(String checkingOutTime) {
        this.checkingOutTime = checkingOutTime;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "BookingDetails{" +
                "bookingId='" + bookingId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", guestId='" + guestId + '\'' +
                ", checkingInDate='" + checkingInDate + '\'' +
                ", checkingInTime='" + checkingInTime + '\'' +
                ", checkingOutDate='" + checkingOutDate + '\'' +
                ", checkingOutTime='" + checkingOutTime + '\'' +
                ", cost=" + cost +
                '}';
    }
}
