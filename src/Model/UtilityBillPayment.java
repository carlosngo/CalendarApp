package Model;

import java.util.*;
import java.awt.*;

public class UtilityBillPayment extends Event {
    public static final Color COLOR_CODE = Color.GREEN;
    public UtilityBillPayment(Calendar date, String name, Color c) {
        super(date, name, c, Event.MONTHLY_EVENT);
        setBackgroundColor(COLOR_CODE);
    }
}
