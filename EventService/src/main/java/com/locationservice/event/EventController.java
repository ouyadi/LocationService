package com.locationservice.event;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {
	@Autowired
	EventRepository eventRepository;
	Logger logger = LoggerFactory.getLogger(EventController.class);
	
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
	
	@RequestMapping(method=RequestMethod.GET, value="/type={type}&value={value}")
	public List<Event> get(@PathVariable String type, @PathVariable String value){
		logger.info("Request type:"+type+", Request value:"+value);
		List<Event> list = new ArrayList<Event>();
		if("id".equals(type)){
			Event event = eventRepository.findOne(value);
			list.add(event);
			
		}else if("eventType".equals(type)){
			list = eventRepository.findByEventType(type);
		}
		return list;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/id={eventId}" )
	public ResponseEntity<?> delete(@PathVariable String eventId){
		if(eventRepository.findOne(eventId)!=null){
			eventRepository.delete(eventId);
			return ResponseEntity.ok().build();
		}
		else{
			return ResponseEntity.noContent().build();
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/listAll")
	public List<Event> listAll(){
		return eventRepository.findAll();
	}
	
}
