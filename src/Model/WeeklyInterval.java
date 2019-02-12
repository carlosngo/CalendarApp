package Model;

import java.util.Calendar;

public class WeeklyInterval extends EventInterval {
    @Override
    public boolean repeatsAtDate(Calendar startDate, Calendar query) {
        if (startDate.compareTo(query) > 0) return false;
        return startDate.get(Calendar.DAY_OF_WEEK) == query.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public String toString() {
        return EventInterval.WEEKLY_EVENT + "";
    }
}
