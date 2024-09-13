package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {
    private static final String REGEX = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$";

    public static boolean isValidISBN(String isbn) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(isbn);
        return matcher.matches();
    }

    public static String getRootDir() {
        return System.getProperty("user.dir");
    }

    public static String getResourceDir() {
        return getRootDir() + "/src/resource";
    }
}
