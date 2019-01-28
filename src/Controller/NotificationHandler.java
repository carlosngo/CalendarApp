package Controller;

import View.*;
import Model.*;

import java.util.*;
import javax.swing.*;

public class NotificationHandler implements Runnable {

    private CalendarController cc;
    TreeSet<Event> eventsNotified;
    private JFrame client;


    public NotificationHandler(CalendarController cc, JFrame client) {
        this.cc = cc;
        this.client = client;
        eventsNotified = new TreeSet<>();
    }

    @Override
    public void run() {
        while (true) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            TreeSet<Event> events = cc.getEvents(c);
            if (events != null) {
                for (Event e : events) {
                    if (!eventsNotified.contains(e)) {
//                        System.out.println("Pushing a notif: " + e.getDate());
                        if (client instanceof FBWindow) {
                            ((FBWindow) client).showNewEvent(e.getName(),e.getDate().get(Calendar.MONTH) + 1,
                                    e.getDate().get(Calendar.DAY_OF_MONTH), e.getDate().get(Calendar.YEAR), e.getColor());
                        } else if (client instanceof SMSWindow) {
                            ((SMSWindow) client).sendSMS(new SMS(e.getName(), e.getDate(), e.getColor()));
                        }
                        eventsNotified.add(e);
                    }
                }
            } else {

            }
        }
    }

}
