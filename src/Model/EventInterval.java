package Model;

import java.util.Calendar;

public abstract class EventInterval {
    public static final int ONE_TIME_EVENT = 0;
    public static final int DAILY_EVENT = 1;
    public static final int WEEKLY_EVENT = 2;
    public static final int MONTHLY_EVENT = 3;
    public static final int YEARLY_EVENT = 4;

    public static EventInterval parseInterval(String s) {
        int interval = Integer.parseInt(s);
        switch (interval) {
            case DAILY_EVENT:
                return new DailyInterval();
            case WEEKLY_EVENT:
                return new WeeklyInterval();
            case MONTHLY_EVENT:
                return new MonthlyInterval();
            case YEARLY_EVENT:
                return new YearlyInterval();
        }
        return new NoInterval();
    }
    public abstract boolean repeatsAtDate(Calendar startDate, Calendar query);
}
