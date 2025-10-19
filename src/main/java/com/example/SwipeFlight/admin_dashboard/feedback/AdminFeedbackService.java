package com.example.SwipeFlight.admin_dashboard.feedback;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminFeedbackService {

	@Autowired // dependency injection
	private AdminFeedbackRepository adminFeedbackRepository;

	public List<Double> getAverageRatingsForRouteNum(Long routeNumber) {
		return adminFeedbackRepository.getAverageRatingsForRouteNum(routeNumber);
	}

}
