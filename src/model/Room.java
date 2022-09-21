package model;

public class Room {
    private String roomId;
    private String roomType;
    private String floor;
    private Double price;

    public Room() {
    }

    public Room(String roomId, String roomType, String floor, Double price) {
        this.setRoomId(roomId);
        this.setRoomType(roomType);
        this.setFloor(floor);
        this.setPrice(price);
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", roomType='" + roomType + '\'' +
                ", floor='" + floor + '\'' +
                ", price=" + price +
                '}';
    }
}
