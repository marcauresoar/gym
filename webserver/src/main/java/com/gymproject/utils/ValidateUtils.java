package com.gymproject.utils;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidateUtils {
	public static boolean isEmailValid(String email){
		return EmailValidator.getInstance().isValid(email);
	}
}
