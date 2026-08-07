public class Booking {

    int bookingId;
    String customerName;
    String pickupLocation;
    String dropLocation;
    String bookingTime;
    String tripStatus;
    double fare;

    Cab assignedCab;

    public Booking(int bookingId,
                   String customerName,
                   String pickupLocation,
                   String dropLocation,
                   String bookingTime,
                   String tripStatus,
                   double fare) {

        this.bookingId = bookingId;
        this.customerName = customerName;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.bookingTime = bookingTime;
        this.tripStatus = tripStatus;
        this.fare = fare;
        this.assignedCab = null;
    }

    public void display() {
        System.out.println("--------------------------------");
        System.out.println("Booking ID : " + bookingId);
        System.out.println("Customer   : " + customerName);
        System.out.println("Pickup     : " + pickupLocation);
        System.out.println("Drop       : " + dropLocation);
        System.out.println("Time       : " + bookingTime);
        System.out.println("Status     : " + tripStatus);
        System.out.println("Fare       : " + fare);

        if (assignedCab != null)
            System.out.println("Cab        : " + assignedCab.cabNumber);
        else
            System.out.println("Cab        : Not Assigned");
    }
}