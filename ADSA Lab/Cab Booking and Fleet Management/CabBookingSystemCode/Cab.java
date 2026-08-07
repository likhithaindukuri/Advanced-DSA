public class Cab {

    int cabNumber;
    String driverName;
    double driverRating;
    String currentLocation;
    boolean available;

    public Cab(int cabNumber,
               String driverName,
               double driverRating,
               String currentLocation,
               boolean available) {

        this.cabNumber = cabNumber;
        this.driverName = driverName;
        this.driverRating = driverRating;
        this.currentLocation = currentLocation;
        this.available = available;
    }

    public void display() {

        System.out.println("-----------------------------");
        System.out.println("Cab Number : " + cabNumber);
        System.out.println("Driver     : " + driverName);
        System.out.println("Rating     : " + driverRating);
        System.out.println("Location   : " + currentLocation);
        System.out.println("Available  : " + available);
    }
}