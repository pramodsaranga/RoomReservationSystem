package model;

public class MealPlan {
    private String mealPlanId;
    private String bookingId;
    private String guestId;
    private String mealPlanType;
    private Double mealPlanPrice;
    private Double total;

    public MealPlan() {
    }

    public MealPlan(String mealPlanId, String bookingId, String guestId, String mealPlanType, Double mealPlanPrice, Double total) {
        this.setMealPlanId(mealPlanId);
        this.setBookingId(bookingId);
        this.setGuestId(guestId);
        this.setMealPlanType(mealPlanType);
        this.setMealPlanPrice(mealPlanPrice);
        this.setTotal(total);
    }

    public String getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(String mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getMealPlanType() {
        return mealPlanType;
    }

    public void setMealPlanType(String mealPlanType) {
        this.mealPlanType = mealPlanType;
    }

    public Double getMealPlanPrice() {
        return mealPlanPrice;
    }

    public void setMealPlanPrice(Double mealPlanPrice) {
        this.mealPlanPrice = mealPlanPrice;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
