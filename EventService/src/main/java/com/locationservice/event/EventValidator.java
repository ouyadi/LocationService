package com.locationservice.event;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class EventValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Event.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creator", "creator is empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventMessage", "eventMessage is empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventTitle", "eventTitle is empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventType", "eventType is empty");
		Event event = (Event)obj;
		if(event.getEventMessage().length()<=10){
			errors.rejectValue("eventMessage", "Event message need to be more than 10 chars");
		}
	}

}
