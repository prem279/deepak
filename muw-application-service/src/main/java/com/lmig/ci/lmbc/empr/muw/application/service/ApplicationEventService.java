package com.lmig.ci.lmbc.empr.muw.application.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationEventResource;

public interface ApplicationEventService {

	public void createEvent(Eventable before, Eventable after, String eventType, String businessEventType);

	public ResponseEntity<List<ApplicationEventResource>> getApplicationEvents(Integer eventId);

}
