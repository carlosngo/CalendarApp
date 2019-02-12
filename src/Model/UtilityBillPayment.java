package Model;

import java.util.*;
import java.awt.*;

public class UtilityBillPayment extends Event {
    public static final Color COLOR_CODE = Color.GREEN;
    public UtilityBillPayment(Calendar date, String name, Color c) {
        super(date, name, c, new MonthlyInterval());
        setBackgroundColor(COLOR_CODE);
    }
}
