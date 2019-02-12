package Model;

import java.util.Calendar;

public class NoInterval extends EventInterval {
    @Override
    public boolean repeatsAtDate(Calendar startDate, Calendar query) {
        if (startDate.compareTo(query) > 0) return false;
        return startDate.compareTo(query) == 0;
    }

    @Override
    public String toString() {
        return EventInterval.ONE_TIME_EVENT + "";
    }
}
