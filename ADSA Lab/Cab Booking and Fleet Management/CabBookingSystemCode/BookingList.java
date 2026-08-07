public class BookingList {

    private BookingNode head;
    private BookingNode tail;

    public BookingList() {
        head = null;
        tail = null;
    }

    public void insertAtBeginning(Booking booking) {

        BookingNode newNode = new BookingNode(booking);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }

        System.out.println("Booking inserted at beginning.");
    }
    public void insertAtEnd(Booking booking) {

        BookingNode newNode = new BookingNode(booking);

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        System.out.println("Booking inserted at end.");
    }

    public BookingNode searchBooking(int bookingId) {

        BookingNode current = head;

        while (current != null) {

            if (current.data.bookingId == bookingId) {
                return current;
            }

            current = current.next;
        }

        return null;
    }
    public void insertAfterBooking(int bookingId, Booking booking) {

        BookingNode current = searchBooking(bookingId);

        if (current == null) {
            System.out.println("Booking not found.");
            return;
        }

        BookingNode newNode = new BookingNode(booking);

        newNode.next = current.next;
        newNode.prev = current;

        if (current.next != null) {
            current.next.prev = newNode;
        } else {
            tail = newNode;
        }

        current.next = newNode;

        System.out.println("Booking inserted.");
    }
    public void displayForward() {

        if (head == null) {
            System.out.println("No Active Bookings.");
            return;
        }

        BookingNode current = head;

        while (current != null) {
            current.data.display();
            current = current.next;
        }
    }
    public void displayBackward() {

        if (tail == null) {
            System.out.println("No Active Bookings.");
            return;
        }

        BookingNode current = tail;

        while (current != null) {

            current.data.display();

            current = current.prev;
        }
    }
    public void removeBooking(int bookingId) {

        BookingNode current = searchBooking(bookingId);

        if (current == null) {
            System.out.println("Booking not found.");
            return;
        }

        if (current == head && current == tail) {
            head = tail = null;
        }
        else if (current == head) {
            head = head.next;
            head.prev = null;
        }
        else if (current == tail) {
            tail = tail.prev;
            tail.next = null;
        }
        else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }

        System.out.println("Booking removed successfully.");
    }
    public void cancelBooking(int bookingId) {

        BookingNode current = searchBooking(bookingId);

        if (current == null) {
            System.out.println("Booking not found.");
            return;
        }

        current.data.tripStatus = "Cancelled";

        System.out.println("Booking cancelled successfully.");
    }
    public void updateBooking(int bookingId, String pickup,String drop,double fare,String status) {

        BookingNode current = searchBooking(bookingId);

        if (current == null) {
            System.out.println("Booking not found.");
            return;
        }

        current.data.pickupLocation = pickup;
        current.data.dropLocation = drop;
        current.data.fare = fare;
        current.data.tripStatus = status;

        System.out.println("Booking updated successfully.");
    }
    public int countBookings() {

        int count = 0;

        BookingNode current = head;

        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }
    public void getBookingsByCustomer(String customerName) {

        BookingNode current = head;

        boolean found = false;

        while (current != null) {

            if (current.data.customerName.equalsIgnoreCase(customerName)) {

                current.data.display();
                found = true;
            }

            current = current.next;
        }

        if (!found)
            System.out.println("No bookings found.");
    }
    public void sortBookings() {

        if (head == null)
            return;

        boolean swapped;

        do {

            swapped = false;

            BookingNode current = head;

            while (current.next != null) {

                if (current.data.fare > current.next.data.fare) {

                    Booking temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;

                    swapped = true;
                }

                current = current.next;
            }

        } while (swapped);

        System.out.println("Bookings sorted by fare.");
    }
}