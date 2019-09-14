package org.asck.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asck.web.service.model.Event;
import org.asck.web.ui.model.UIEventTM;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewEventController extends AbstractController {

	private static final Logger LOGGER = LogManager.getLogger(NewEventController.class);
	
	
	@GetMapping(path = {"/newEvent"})
	public String get(Model model) {
		model.addAttribute("eventTM", new UIEventTM());
		return "newEvent";
	}
	
	@GetMapping(path = {"/newEvent"}, params = "eventId")
	public String get(Model model, @RequestParam(required=false ,name = "eventId") Long eventId) {
		UIEventTM eventTM = new UIEventTM();
		// Wurde Param questionId in dem URL mitgegeben, dann ist das ein Update ->
		// Daten weiter geben
		if (eventId != null && eventId > 0) {
			Event event = getFeedbackService().getEventById(eventId);
			eventTM.setEventName(event.getName());
			eventTM.setId(event.getId());
		}

		model.addAttribute("eventTM", eventTM);
		return "newEvent";
	}

	@PostMapping("/newEvent")
	public String post(@ModelAttribute UIEventTM eventTM) {
		LOGGER.info("Save Obj from Ui {}", eventTM);
		LOGGER.info("Save Event for User {}", getLoggedUser());
		org.asck.web.service.model.User userByEmail = getFeedbackService().getUserByEmail(getLoggedUser());
		Event event = new Event(eventTM.getId(), eventTM.getEventName(), userByEmail.getId());
		getFeedbackService().saveEvent(event);
		return "redirect:/events";
	}

	private String getLoggedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object pricipal = auth.getPrincipal();
		String loggedUser="";
		if (pricipal instanceof User) {
		       loggedUser = ((User) pricipal).getUsername();
		}
		return loggedUser;
	}
}
