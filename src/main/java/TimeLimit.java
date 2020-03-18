import java.util.*;

public class TimeLimit {
    private long currentTime;
    private int timeLimit;
    private long startTime;

    // all times are in seconds
    public TimeLimit(int timeLimit, long startTime) {
        this.currentTime = 0;
        this.timeLimit = timeLimit;
        this.startTime = startTime;
    }

    public int getTimeLimit() { return this.timeLimit; }

    public void setTimeLimit(int time) {
        this.timeLimit = time;
    }

    public long getCurrentTime() {
        currentTime = System.currentTimeMillis()/1000 - startTime;
        return currentTime;
    }

}
