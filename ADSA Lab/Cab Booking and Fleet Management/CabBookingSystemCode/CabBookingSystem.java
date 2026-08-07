import java.util.Scanner;
import java.time.LocalTime;

public class CabBookingSystem {
    static int nextBookingId = 1001;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookingList bookingList = new BookingList();
        CabCircularList cabList = new CabCircularList();
        RideHistoryList rideHistory = new RideHistoryList();

        int choice;

        do {

            System.out.println("\n===== CAB BOOKING SYSTEM =====");
            System.out.println("1. Add Cab");
            System.out.println("2. Add Booking");
            System.out.println("3. Assign Cab");
            System.out.println("4. Complete Ride");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Display Active Bookings");
            System.out.println("7. Display Available Cabs");
            System.out.println("8. Display Ride History");
            System.out.println("9. Search Booking");
            System.out.println("10. Count Active Bookings");
            System.out.println("11. Sort Bookings by Fare");
            System.out.println("12. Sort Cabs");
            System.out.println("13. Exit");

            System.out.print("Enter Choice : ");
            choice = sc.nextInt();

            switch(choice) {
                case 1:

                    System.out.print("Cab Number : ");
                    int cabNo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Driver Name : ");
                    String driver = sc.nextLine();

                    System.out.print("Driver Rating : ");
                    double rating = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Current Location : ");
                    String location = sc.nextLine();

                    Cab cab = new Cab(cabNo, driver, rating, location, true);

                    cabList.addCab(cab);

                    break;
                case 2:

                    sc.nextLine();
                    int id = nextBookingId;
                    nextBookingId++;
                    System.out.println("Booking ID Generated : " + id);

                    System.out.print("Customer Name : ");
                    String customer = sc.nextLine();

                    System.out.print("Pickup : ");
                    String pickup = sc.nextLine();

                    System.out.print("Drop : ");
                    String drop = sc.nextLine();

                    String time = LocalTime.now().withNano(0).toString();
                    System.out.println("Booking Time : " + time);

                    System.out.print("Fare : ");
                    double fare = sc.nextDouble();

                    Booking booking = new Booking(
                            id,
                            customer,
                            pickup,
                            drop,
                            time,
                            "Pending",
                            fare);

                    bookingList.insertAtEnd(booking);

                    break;
                case 3:

                    System.out.print("Booking ID : ");
                    int bid = sc.nextInt();

                    BookingNode bookingNode = bookingList.searchBooking(bid);

                    if (bookingNode == null) {

                        System.out.println("Booking not found.");
                    }
                    else {

                        Cab assigned = cabList.assignCab();

                        if (assigned != null) {

                            bookingNode.data.assignedCab = assigned;
                            bookingNode.data.tripStatus = "Assigned";

                            System.out.println("Cab Assigned Successfully.");
                        }
                    }

                    break;
                case 4:

                    System.out.print("Booking ID : ");
                    int completeId = sc.nextInt();

                    BookingNode node = bookingList.searchBooking(completeId);

                    if(node != null){

                        node.data.tripStatus = "Completed";

                        rideHistory.addCompletedRide(node.data);

                        if(node.data.assignedCab != null){

                            cabList.makeCabAvailable(
                                    node.data.assignedCab.cabNumber);
                        }

                        bookingList.removeBooking(completeId);

                        System.out.println("Ride Completed.");
                    }
                    else{

                        System.out.println("Booking Not Found.");
                    }

                    break;
                
                case 5:

                    System.out.print("Booking ID : ");
                    int cancelId = sc.nextInt();

                    bookingList.cancelBooking(cancelId);

                    break;
                case 6:

                    System.out.println("1. Display Forward");
                    System.out.println("2. Display Backward");
                    System.out.print("Enter Choice : ");

                    int displayChoice = sc.nextInt();

                    if(displayChoice == 1){
                        bookingList.displayForward();
                    }
                    else if(displayChoice == 2){
                        bookingList.displayBackward();
                    }
                    else{
                        System.out.println("Invalid Choice");
                    }

                    break;
                case 7:

                    cabList.displayAvailableCabs();

                    break;
                case 8:

                    rideHistory.displayRideHistory();

                    break;
                case 9:

                    System.out.print("Booking ID : ");

                    int search = sc.nextInt();

                    BookingNode result =
                            bookingList.searchBooking(search);

                    if(result != null)
                        result.data.display();
                    else
                        System.out.println("Booking Not Found.");

                    break;
                case 10:

                    System.out.println(
                            "Total Active Bookings : "
                                    + bookingList.countBookings());

                    break;
                case 11:

                    bookingList.sortBookings();

                    break;
                case 12:

                    cabList.sortCabs();

                    break;
                case 13:

                    System.out.println("Thank You");

                    break;
                default:

                    System.out.println("Invalid Choice");
                            }

        } while(choice != 13);

        sc.close();
    }
}
                