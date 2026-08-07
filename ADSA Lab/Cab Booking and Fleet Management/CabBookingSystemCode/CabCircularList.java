public class CabCircularList {

    private CabNode head;
    private CabNode tail;
    private CabNode allocationPointer;

    public CabCircularList() {
        head = null;
        tail = null;
        allocationPointer = null;
    }
    public void addCab(Cab cab) {

        CabNode newNode = new CabNode(cab);

        if (head == null) {

            head = tail = newNode;
            newNode.next = head;
            allocationPointer = head;

        } else {

            tail.next = newNode;
            newNode.next = head;
            tail = newNode;
        }

        System.out.println("Cab added successfully.");
    }
    public CabNode searchCab(int cabNumber) {

        if (head == null)
            return null;

        CabNode current = head;

        do {

            if (current.data.cabNumber == cabNumber)
                return current;

            current = current.next;

        } while (current != head);

        return null;
    }
    public void removeCab(int cabNumber) {

        if (head == null) {
            System.out.println("No cabs available.");
            return;
        }

        CabNode current = head;
        CabNode previous = tail;

        do {

            if (current.data.cabNumber == cabNumber) {

                if (head == tail) {

                    head = tail = allocationPointer = null;
                }

                else {

                    previous.next = current.next;

                    if (current == head)
                        head = head.next;

                    if (current == tail)
                        tail = previous;

                    tail.next = head;

                    if (allocationPointer == current)
                        allocationPointer = current.next;
                }

                System.out.println("Cab removed successfully.");
                return;
            }

            previous = current;
            current = current.next;

        } while (current != head);

        System.out.println("Cab not found.");
    }
    public Cab assignCab() {

        if (allocationPointer == null) {
            System.out.println("No cabs available.");
            return null;
        }

        CabNode start = allocationPointer;

        do {

            if (allocationPointer.data.available) {

                allocationPointer.data.available = false;

                Cab assignedCab = allocationPointer.data;

                allocationPointer = allocationPointer.next;

                return assignedCab;
            }

            allocationPointer = allocationPointer.next;

        } while (allocationPointer != start);

        System.out.println("No available cab found.");

        return null;
    }
    public void makeCabAvailable(int cabNumber) {

        CabNode cab = searchCab(cabNumber);

        if (cab != null) {

            cab.data.available = true;

            System.out.println("Cab is available again.");
        }
    }
    public void displayAvailableCabs() {

        if (head == null) {

            System.out.println("No cabs available.");
            return;
        }

        CabNode current = allocationPointer;

        do {

            current.data.display();

            current = current.next;

        } while (current != allocationPointer);
    }
    public void sortCabs() {

        if (head == null)
            return;

        boolean swapped;

        do {

            swapped = false;

            CabNode current = head;

            while (current.next != head) {

                if (current.data.cabNumber > current.next.data.cabNumber) {

                    Cab temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;

                    swapped = true;
                }

                current = current.next;
            }

        } while (swapped);

        System.out.println("Cabs sorted successfully.");
    }
}