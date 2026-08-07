public class BookingNode {

    Booking data;
    BookingNode prev;
    BookingNode next;

    public BookingNode(Booking data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}