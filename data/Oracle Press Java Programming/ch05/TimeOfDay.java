
public class TimeOfDay {

    private int hour, mins;

    public TimeOfDay(int hour, int mins) {
        this.hour = hour;
        this.mins = mins;
    }

    public TimeOfDay(TimeOfDay other) {
        this(other.hour, other.mins);
    }

    public String toString() {
        return (this.hour + ":" + this.mins);
    }

    public static void main(String[] args) {
        TimeOfDay currentTime = new TimeOfDay(12, 30);
        TimeOfDay copyOfCurrentTime = new TimeOfDay(currentTime);
        System.out.println("Original: " + currentTime.toString());
        System.out.println("Copy: " + copyOfCurrentTime.toString());
    }
}