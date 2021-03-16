package enumexample;

import java.util.regex.Pattern;

public class RomanNumerals {

    private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

    static boolean isRomanNumberal(String s) {
        return ROMAN.matcher(s).matches();
    }

    public void runExample() {
        boolean isRoman = isRomanNumberal("VI");
        System.out.println(isRoman);
    }
}
