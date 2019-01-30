package Controller;

import java.util.*;

public class DateChangedNotifier implements Runnable {
    private Calendar state;
    private NotificationController observer;

    public DateChangedNotifier(NotificationController observer) {
        state = Calendar.getInstance();
        state.setTime(new Date());
        state.set(Calendar.HOUR_OF_DAY, 0);
        state.set(Calendar.MINUTE, 0);
        state.set(Calendar.SECOND, 0);
        state.set(Calendar.MILLISECOND, 0);
        this.observer = observer;
        notifyObserver();
    }

    public void run() {
        while (true) {
            Calendar newState = Calendar.getInstance();
            newState.setTime(new Date());
            newState.set(Calendar.HOUR_OF_DAY, 0);
            newState.set(Calendar.MINUTE, 0);
            newState.set(Calendar.SECOND, 0);
            newState.set(Calendar.MILLISECOND, 0);
            if (state.compareTo(newState) != 0) { // state has changed
                notifyObserver();
            }
        }
    }

    public void notifyObserver() {
        observer.setState(state);
        observer.update();
    }

}
