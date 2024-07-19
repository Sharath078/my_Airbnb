package com.airbnb.dto;

public class BookingDto {

    private long BookingId;
    private  String guestName;
    private int price;

    public long getBookingId() {
        return BookingId;
    }

    public void setBookingId(long bookingId) {
        BookingId = bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }

    private int TotalPrice;
}
