import java.util.*;

public class TimeLimit {
    private float currentTime;
    private int timeLimit;
    private float startTime;

    // all times are in seconds
    public TimeLimit(int timeLimit, float startTime) {
        this.currentTime = 0;
        this.timeLimit = timeLimit;
        this.startTime = startTime;
    }

    public int getTimeLimit() { return timeLimit; }

    public void setTimeLimit(int time) {
        timeLimit = time;
    }

    public float getCurrentTime() {
        currentTime = System.currentTimeMillis()/1000 - startTime;
        return currentTime;
    }

}
