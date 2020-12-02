package org.lomakin.ui.utils;

import javafx.scene.control.TextField;

import static java.lang.Character.isDigit;

public class ListenerUtils {
    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((o, oldValue, newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }

    public static void addDigitChecker(final TextField tf) {
        tf.textProperty().addListener((o, oldValue, newValue) -> {
            if (newValue.equals("")) return;
            if (isDigit(newValue.charAt(newValue.length() - 1))) return;

            tf.setText(oldValue);
        });
    }
}
