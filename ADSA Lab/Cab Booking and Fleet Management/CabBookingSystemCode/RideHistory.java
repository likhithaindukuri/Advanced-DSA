public class RideHistory {

    Booking booking;

    public RideHistory(Booking booking) {
        this.booking = booking;
    }

    public void display() {
        booking.display();
    }
}