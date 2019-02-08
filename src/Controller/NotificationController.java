package Controller;

import Model.*;
import View.*;

import java.util.*;
import javax.swing.*;

public class NotificationController {

    private ArrayList<JFrame> subjects;
    private TreeSet<Event> notifiedEvents;
    private CalendarController cc;
    private Calendar state;

    public NotificationController(CalendarController cc) {
        this.cc = cc;
        subjects = new ArrayList<>();
        notifiedEvents = new TreeSet<>();
        new Thread(new DateChangedNotifier(this)).start();
    }

    public void setState(Calendar newState) {
        state = newState;
    }

    public void addSubject(JFrame subject) {
        subjects.add(subject);
        TreeSet<Event> events = cc.getEvents(state);
        if (events != null) {
            for (Event e : events) {
                if (subject instanceof FBView) {
                    ((FBView) subject).showNewEvent(e.getName(), e.getDate().get(Calendar.MONTH) + 1,
                            e.getDate().get(Calendar.DAY_OF_MONTH), e.getDate().get(Calendar.YEAR), e.getTextColor());
                } else if (subject instanceof SMSView) {
                    ((SMSView) subject).sendSMS(new SMS(e.getName(), e.getDate(), e.getTextColor()));
                }
            }
        }
    }

    public void update() {
        TreeSet<Event> events = cc.getEvents(state);
        if (events != null) {
            for (Event e : events) {
                if (!notifiedEvents.contains(e)) {
                    for (JFrame subject : subjects) {
                        if (subject instanceof FBView) {
                            ((FBView) subject).showNewEvent(e.getName(), state.get(Calendar.MONTH) + 1,
                                    state.get(Calendar.DAY_OF_MONTH), state.get(Calendar.YEAR), e.getTextColor());
                        } else if (subject instanceof SMSView) {
                            ((SMSView) subject).sendSMS(new SMS(e.getName(), state, e.getTextColor()));
                        }
                    }
                    notifiedEvents.add(e);
                }
            }
        }
    }
}
