package cz.upce.frontend;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class FieldValidator {


    public static boolean validateEmptyField(TextField textField) {
        if (textField.isEmpty()) {
            textField.setInvalid(true);
        }
        return textField.isEmpty();
    }

    public static boolean validateEmptyField(NumberField textField) {
        if (textField.isEmpty()) {
            textField.setInvalid(true);
        }
        return textField.isEmpty();
    }

    public static boolean validateEmptyField(EmailField textField) {
        if (textField.isEmpty()) {
            textField.setInvalid(true);
        }
        return textField.isEmpty();
    }

    public static boolean validateEmptyField(DatePicker textField) {
        if (textField.getValue() == null || textField.isEmpty()) {
            textField.setInvalid(true);
        }
        return textField.isEmpty();
    }
}
