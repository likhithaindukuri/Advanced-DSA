public class RideHistoryList {

    private RideNode head;
    private RideNode tail;

    public RideHistoryList() {
        head = null;
        tail = null;
    }
    public void addCompletedRide(Booking booking) {

        RideHistory ride = new RideHistory(booking);

        RideNode newNode = new RideNode(ride);

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        System.out.println("Ride added to history.");
    }
    public RideHistory searchRide(int bookingId) {

        RideNode current = head;

        while (current != null) {

            if (current.data.booking.bookingId == bookingId) {
                return current.data;
            }

            current = current.next;
        }

        return null;
    }
    public void searchRideByCustomer(String customerName) {

        RideNode current = head;

        boolean found = false;

        while (current != null) {

            if (current.data.booking.customerName.equalsIgnoreCase(customerName)) {

                current.data.display();
                found = true;
            }

            current = current.next;
        }

        if (!found)
            System.out.println("No ride history found.");
    }
    public void displayRideHistory() {

        if (head == null) {

            System.out.println("No completed rides.");
            return;
        }

        RideNode current = head;

        while (current != null) {

            current.data.display();

            current = current.next;
        }
    }
}