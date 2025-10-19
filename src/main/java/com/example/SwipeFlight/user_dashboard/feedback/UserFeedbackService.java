package com.example.SwipeFlight.user_dashboard.feedback;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SwipeFlight.entities.feedback.Feedback;
import com.example.SwipeFlight.entities.route.Route;
import com.example.SwipeFlight.entities.route.RouteService;

@Service
public class UserFeedbackService {

	// dependency injections
	@Autowired
	private UserFeedbackRepository userFeedbackRepository;
	@Autowired
	private RouteService routeService;

	public void insertFeedback(Feedback ratingForm, Long userID, Long routeNumber) throws IllegalArgumentException {
		userFeedbackRepository.insertFeedback(ratingForm, userID, routeNumber);
	}

	public List<Long> getAllRouteIDsAvilableForFeedbackFromUser(Long userID) throws IllegalArgumentException {
		return userFeedbackRepository.getAllRouteNumbersAvilableForFeedbackFromUser(userID);
	}

	public List<Long> getAllRouteNumbersForUser(Long userID) throws IllegalArgumentException {
		return userFeedbackRepository.getAllRouteNumbersForUser(userID);
	}

	public Map<Route, Boolean> mapUserRoutesAndFeedbackAvailability(Long userID) {

		List<Long> userRoutes_all = getAllRouteNumbersForUser(userID);

		List<Long> userRoutes_withFeedbackAvailable = getAllRouteIDsAvilableForFeedbackFromUser(userID);

		Map<Route, Boolean> routeFeedbackMap = new LinkedHashMap<>();

		for (Long routeID : userRoutes_all) {
			boolean openForFeedback = userRoutes_withFeedbackAvailable.contains(routeID);
			Route route = routeService.getRouteByRouteNumber(routeID);
			routeFeedbackMap.put(route, openForFeedback);
		}
		return routeFeedbackMap;
	}

}
