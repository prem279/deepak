package com.lmig.ci.lmbc.empr.muw.application.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lmig.ci.lmbc.empr.muw.application.ApplicationServiceEventConstants;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationEventResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationEventResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationEvent;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicationEventRepository;
import com.lmig.ci.lmbc.empr.muw.application.service.processor.ApplicationServiceChangeProcessor;

@Service
public class ApplicationEventServiceImpl implements ApplicationEventService {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationEventServiceImpl.class);

	@Autowired
	private ApplicationServiceChangeProcessor applicationServiceChangeProcessor;

	@Autowired
	private ApplicationEventRepository applicationEventRepository;

	@Autowired
	private ApplicationEventResourceAssembler applicationEventResourceAssembler;

	@Override
	public void createEvent(Eventable before, Eventable after, String eventType, String businessEventType) {
		ApplicationEvent event = new ApplicationEvent();
		if (eventType.equals(ApplicationServiceEventConstants.UPDATE)) {
			try {
				event = publishChange(before, after);
				if (event == null) {
					LOG.error("Issue while publishing the UPDATE change set.  Subject Area ID: "
							+ after.getSubjectAreaId());
				}
			} catch (Exception e) {
				LOG.error("Error Building Change Set for Application Request.  Subject Area ID: "
						+ after.getSubjectAreaId());
				LOG.error(e.toString());
			}
		} else {
			try {
				event = publishChange(null, after);
				if (event == null) {
					LOG.error(
							"Issue while publishing INSERT change set.  Subject Area ID: " + after.getSubjectAreaId());
				}
			} catch (Exception e) {
				LOG.error("Error Building Change Set for Application Request.  Subject Area ID: "
						+ after.getSubjectAreaId());
				LOG.error(e.toString());
			}

		}

		try {
			if ((event != null) && (event.getBeforeEventData() != null) && !event.getBeforeEventData().equals("")
					&& (event.getAfterEventData() != null) && !event.getAfterEventData().equals("")) {
				event.setChangeEventCode(eventType);
				event.setBusinessEventCode(businessEventType);
				applicationEventRepository.saveAndFlush(event);
			}
		} catch (Exception e) {
			LOG.error("Error saving the Applicaton event to the Event table.", e);
		}

	}

	private ApplicationEvent publishChange(Eventable before, Eventable after) throws Exception {
		ApplicationEvent ApplicationEvent = new ApplicationEvent();
		try {
			ApplicationEvent = applicationServiceChangeProcessor.processChange(before, after);
			// TODO is there a reason we are catching Throwable? Typically this
			// would just be Exception, unless we are at the higest level or
			// developing some sort of framework
			// Error (which Throwable can catch) is unrecoverable so we are
			// screwed anyway :)
		} catch (Throwable e) {
			LOG.error("Unable to process event changes -- " + e.getMessage());
			return null;
		}

		return ApplicationEvent;
	}

	@Override
	public ResponseEntity<List<ApplicationEventResource>> getApplicationEvents(Integer eventId) {
		List<ApplicationEvent> applicationEvents = applicationEventRepository
				.findTop25ByEventIdGreaterThanOrderByEventIdAsc(eventId);
		List<ApplicationEventResource> applicationEventResources = new ArrayList<>();
		for (ApplicationEvent applicationEvent : applicationEvents) {
			applicationEventResources.add(applicationEventResourceAssembler.toResource(applicationEvent));
		}
		return new ResponseEntity<List<ApplicationEventResource>>(applicationEventResources, HttpStatus.OK);
	}

}
