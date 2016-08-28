package com.locationservice.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {
	@Autowired
	EventRepository eventRepository;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Event event, BindingResult errors){
		EventValidator eventValidator = new EventValidator();
		eventValidator.validate(event, errors);
		StringBuilder errorStr = new StringBuilder();
		if(errors.hasErrors()){
			for(ObjectError i:errors.getAllErrors()){
				errorStr.append(i.getCode()+", ");
			}
			errorStr.delete(errorStr.length()-2, errorStr.length());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorStr.toString());
		}
		else{
			return ResponseEntity.status(HttpStatus.CREATED).body(eventRepository.save(event));
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{eventId}")
	public ResponseEntity<Event> get(@PathVariable String eventId){
		return ResponseEntity.status(HttpStatus.OK).body(eventRepository.findOne(eventId));
	}
	
	@RequestMapping(method=RequestMethod.DELETE ,value="/{eventId}" )
	public ResponseEntity<?> delete(@PathVariable String eventId){
		if(eventRepository.findOne(eventId)!=null){
			eventRepository.delete(eventId);
			return ResponseEntity.ok().build();
		}
		else{
			return ResponseEntity.noContent().build();
		}
	}
}
