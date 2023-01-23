import java.io.IOException;

public class Passenger {

    private int currFloor;
    private int targetFloor;
    private int totalFloors;
    private boolean inElevator;

    Passenger(int _currFlat, int _totalFloors) throws IOException {
        if (_currFlat <= 0 || _totalFloors <= 0)
            throw new IOException("currFlat is lower than 1");
        inElevator = false;
        currFloor = _currFlat;
        totalFloors = _totalFloors;
        setTargetFlat(); // низя так (метод в конструкторе)
    }

    public boolean isInElevator() {
        return inElevator;
    }

    public void setInElevator(boolean inElevator) {
        this.inElevator = inElevator;
    }

    public int getCurrFloor() {
        return currFloor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public void setCurrFlat(int currFloor) throws IOException {
        if (currFloor <= 0)
            throw new IOException("currFloor is lower than 1");
        this.currFloor = currFloor;
    }

    public void setTargetFlat() {
        targetFloor = (int) (Math.random() * totalFloors) + 1;
        if (targetFloor == currFloor && targetFloor != totalFloors) {
            targetFloor += 1;
        } else if (targetFloor == currFloor) {
            targetFloor -= 1;
        }
    }

}
