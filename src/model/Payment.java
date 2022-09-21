package model;

public class Payment {
    private String billId;
    private String bookingCost;
    private String mealPlanCost;
    private String date;
    private Double total;

    public Payment() {
    }

    public Payment(String billId, String bookingCost, String mealPlanCost, String date, Double total) {
        this.setBillId(billId);
        this.setBookingCost(bookingCost);
        this.setMealPlanCost(mealPlanCost);
        this.setDate(date);
        this.setTotal(total);
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBookingCost() {
        return bookingCost;
    }

    public void setBookingCost(String bookingCost) {
        this.bookingCost = bookingCost;
    }

    public String getMealPlanCost() {
        return mealPlanCost;
    }

    public void setMealPlanCost(String mealPlanCost) {
        this.mealPlanCost = mealPlanCost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "billId='" + billId + '\'' +
                ", bookingCost='" + bookingCost + '\'' +
                ", mealPlanCost='" + mealPlanCost + '\'' +
                ", date='" + date + '\'' +
                ", total=" + total +
                '}';
    }
}
