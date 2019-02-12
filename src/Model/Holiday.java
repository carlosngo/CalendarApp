package Model;

import java.util.*;
import java.awt.*;

public class Holiday extends Event {
    public static final Color COLOR_CODE = Color.YELLOW;
    public Holiday(Calendar date, String name, Color color) {
        super(date, name, color, new YearlyInterval());
        setBackgroundColor(COLOR_CODE);
    }
}
