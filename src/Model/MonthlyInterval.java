package Model;

import java.util.Calendar;

public class MonthlyInterval extends EventInterval {
    @Override
    public boolean repeatsAtDate(Calendar startDate, Calendar query) {
        if (startDate.compareTo(query) > 0) return false;
        return startDate.get(Calendar.DAY_OF_MONTH) == query.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public String toString() {
        return EventInterval.MONTHLY_EVENT + "";
    }
}
