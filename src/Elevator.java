import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

public class Elevator {
    static boolean lifting = true;
    static int floorsCount;
    static int liftTargetFloor = 1;
    static int currFloor = 1;
    static char elevationMark = 94; // its '^', for mark 'V' - 86

    static final int MAX_PASSENGERS = 5;

    static LinkedList<Passenger> passengerList = new LinkedList<>();
    static LinkedList<Passenger> elevatorList = new LinkedList<>();

    static int getDiff(Passenger passenger) {
        return passenger.getTargetFloor() - passenger.getCurrFloor();
    }

    static void findNewTargetFloor() {
        for (Passenger p : passengerList) {
            if ((p.getCurrFloor() > liftTargetFloor && lifting) || (p.getCurrFloor() < liftTargetFloor && !lifting))
                liftTargetFloor = p.getCurrFloor();
        }
    }

    static boolean changeDirection() {
        if (currFloor == liftTargetFloor) {
            lifting = !lifting;
            findNewTargetFloor();
            return true;
        }
        return false;
    }

    static void nextFloor() {
        if (lifting) {
            currFloor++;
            elevationMark = 94;
        } else {
            currFloor--;
            elevationMark = 86;
        }
    }

    static void addPassengers() {
        for (Passenger p : passengerList) {
            if (elevatorList.size() >= MAX_PASSENGERS)
                break;
            int temp = p.getCurrFloor();
            if (temp == currFloor) {
                if (getDiff(p) > 0 && lifting || getDiff(p) < 0 && !lifting) {
                    elevatorList.add(p);
                    p.setInElevator(true);
                    if ((p.getTargetFloor() > liftTargetFloor && lifting) || (p.getTargetFloor() < liftTargetFloor && !lifting))
                        liftTargetFloor = p.getTargetFloor();
                }
            }
        }
        passengerList.removeIf(Passenger::isInElevator);
    }

    static void removePassengers() {
        if (elevatorList.size() == 0)
            return;
        elevatorList.removeIf(p -> p.getTargetFloor() == currFloor);
    }

    static void paint(int step) {
        System.out.println("*** STEP " + step + " ***");
        for (int i = floorsCount; i >= 1; i--) {
            if (currFloor == i) {
                System.out.print(" |" + elevationMark + " ");
                if (elevatorList.size() == 0)
                    System.out.print("          ");
                else {
                    for (Passenger p : elevatorList) {
                        System.out.print(p.getTargetFloor() + " ");
                    }
                    for (int j = 0; j < MAX_PASSENGERS - elevatorList.size(); j++) {
                        System.out.print("  ");
                    }
                }
                System.out.print(" " + elevationMark + "| ");
            } else {
                System.out.print(" |              | ");
            }
            for (Passenger p : passengerList) {
                if (p.getCurrFloor() == i)
                    System.out.print(p.getTargetFloor() + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int step = 1;

        floorsCount = (int) (Math.random() * 15) + 5; // min floors - 5, max => 5 + 15 = 20
        for (int currFloor = 0; currFloor < floorsCount; currFloor++) {
            int passengersOnFloor = (int) (Math.random() * 10); // from 0 to 10 people on the floor
            for (int i = 0; i < passengersOnFloor; i++) {
                passengerList.add(new Passenger(currFloor + 1, floorsCount));
            }
        }

        findNewTargetFloor();
        paint(step++);

        while (true) {
            removePassengers();
            addPassengers();
            if (changeDirection())
                addPassengers();
            nextFloor();
            paint(step++);
//            sleep(1000); // 1 sec timeout
            if (passengerList.isEmpty() && elevatorList.isEmpty())
                break;
        }
    }
}
