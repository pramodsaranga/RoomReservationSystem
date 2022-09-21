package model;

public class Bill {
    private String billId;
    private Double cost;
    private String date;

    public Bill() {
    }

    public Bill(String billId, Double cost, String date) {
        this.setBillId(billId);
        this.setCost(cost);
        this.setDate(date);
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billId='" + billId + '\'' +
                ", cost=" + cost +
                ", date='" + date + '\'' +
                '}';
    }
}
