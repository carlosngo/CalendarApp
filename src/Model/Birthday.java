package Model;

import java.util.*;
import java.awt.*;

public class Birthday extends Event {
    public static final Color COLOR_CODE = Color.RED;
    public Birthday(Calendar date, String name, Color c) {
        super(date, name, c, new YearlyInterval());
        setBackgroundColor(COLOR_CODE);
    }
}
