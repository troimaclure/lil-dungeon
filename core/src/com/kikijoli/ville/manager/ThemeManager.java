package com.kikijoli.ville.manager;

import com.kikijoli.ville.theme.AbstractTheme;
import com.kikijoli.ville.theme.SamouraiTheme;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ajosse
 */
public class ThemeManager {

    public static AbstractTheme currentTheme;

    public static List<AbstractTheme> themes() {
        return Arrays.asList(new SamouraiTheme());
    }

    static {
        currentTheme = new SamouraiTheme();
    }
    

}
