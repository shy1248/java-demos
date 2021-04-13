/**
 * @Date        : 2021-04-10 20:50:35
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Some utils of paramers validator.
 */

package me.shy.rt.dataware.demo.datamocker.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.NonNull;

public class ParameterValidator {

    public static LocalDate dateValidate(@NonNull String date) {
        DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, datePattern);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            throw new ValidateFailedException("Date formatter must be yyyy-MM-dd.");
        }
    }

    public static Boolean boolValidate(@NonNull String bool) {
        bool = bool.trim();
        if ("1".equals(bool) || "true".equals(bool.toLowerCase())) {
            return true;
        } else if ("0".equals(bool) || "false".equals(bool.toLowerCase())) {
            return false;
        } else {
            throw new ValidateFailedException("A bool parameter must be one of [0|1|true|false].");
        }
    }

    public static Integer ratioNumberValidate(@NonNull String ratio) {
        Integer ratioNumber = null;
        try {
            ratioNumber = Integer.parseInt(ratio);
            if (ratioNumber < 0 || ratioNumber > 100) {
                throw new ValidateFailedException(
                        String.format("Illege range of a ratio number: %s, must be in [0-100].", ratio));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new ValidateFailedException("A ratio number must be a integer.");
        }
        return ratioNumber;
    }

    public static Integer[] ratioStringVolidate(@NonNull String ratioString, int ratiosCount) {
        if (ratiosCount <= 0) {
            throw new ValidateFailedException("The ratio count must not be a nonnegative number.");
        }

        Integer[] ratios = new Integer[ratiosCount];
        String[] splited = ratioString.split(":");
        if (splited.length != ratiosCount) {
            throw new ValidateFailedException(String.format("Incomplete percentage expression: %s with ratio count %s.",
                    ratioString, ratiosCount));
        }

        for (int i = 0; i < splited.length; i++) {
            ratios[i] = ratioNumberValidate(splited[i]);
        }

        if (Arrays.stream(ratios).collect(Collectors.summingInt(x -> x)) > 100) {
            throw new ValidateFailedException(
                    String.format("Percentage expression: %s is over flow 100.", ratioString));
        }

        return ratios;
    }

    public static String[] arrayStringValidte(@NonNull String arrayString) {
        return arrayString.split(",");
    }

    public static Integer intValidate(String counter) {
        if (null == counter || "".equals(counter.trim())) {
            return 0;
        }
        try {
            return Integer.parseInt(counter.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new ValidateFailedException("A counter must be a integer.");
        }
    }

    private static class ValidateFailedException extends RuntimeException {
        private static final long serialVersionUID = 8180927078019907995L;

        public ValidateFailedException(String message) {
            super(message);
        }
    }
}
