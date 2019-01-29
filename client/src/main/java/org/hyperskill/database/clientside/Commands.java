package org.hyperskill.database.clientside;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class Commands implements IParameterValidator {
    public void validate(String name, String value)
            throws ParameterException {
        value = value.trim().toLowerCase();
        if (!(value.equals("set") || value.equals("get") || value.equals("delete"))) {
            throw new ParameterException("Parameter " + name + " should be set/get/delete (found " + value + ")");
        }
    }
}
