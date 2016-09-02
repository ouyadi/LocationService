package com.locationservice.event;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
	List<Event> findByEventType(String eventType);
}
