package Model;

import java.util.*;
import java.awt.*;

public class Anniversary extends Event {
    public static final Color COLOR_CODE = Color.MAGENTA;
    public Anniversary(Calendar date, String name, Color c) {
        super(date, name, c, new YearlyInterval());
        setBackgroundColor(COLOR_CODE);
    }
}
