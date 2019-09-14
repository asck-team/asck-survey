package org.asck.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.asck.web.service.model.AnswerReport;
import org.asck.web.service.model.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AccessLevel;
import lombok.Getter;

@Controller
@Getter(value = AccessLevel.PROTECTED)
public class ChartsController extends AbstractController {
	
	@GetMapping("/chart")
	public String barGraph(@RequestParam("eventId") Long eventId, Model model) {
		List<AnswerReport> allAnswersToEventId = getFeedbackService().getAllAnswersToEventId(eventId);
		Event event = getFeedbackService().getEventById(eventId);
		
		Map<String, List<Integer>> surveyMap = getSurveyMap(allAnswersToEventId);
		List<Double> averageData = getAverageData(allAnswersToEventId);
		
		model.addAttribute("surveyMap", surveyMap);
		model.addAttribute("averageData", averageData);
		model.addAttribute("event", event);
		return "chart";
	}

	private List<Double> getAverageData(List<AnswerReport> allAnswersToEventId) {
		Map<String, List<Integer>> result = getChartData(allAnswersToEventId);
		List<Double> averageData = new ArrayList<>();
		
		for (Iterator<List<Integer>> iterator = result.values().iterator(); iterator.hasNext();) {
			List<Integer> list = iterator.next();
			List<Integer> listWithAverageData = new ArrayList<>();
			int count=0;
			for (int i = 0; i < list.size(); i++) {
				listWithAverageData.add(list.get(i) * (i + 1));
				count = count + list.get(i);
			}
			double average = calculateAverage(listWithAverageData, count);
			averageData.add(average);
		}
		return averageData;
	}

	private double calculateAverage(List<Integer> listWithAverageData, int count) {
		double sum = listWithAverageData.stream().mapToInt(val -> val).sum();
		if (sum != 0 && count != 0) {
			return sum / count;
		}
		return 0.0;
	}

	protected Map<String, List<Integer>> getSurveyMap(List<AnswerReport> allAnswersToEventId) {
		Map<String, List<Integer>> result = getChartData(allAnswersToEventId);
		if (!result.isEmpty()) {
			return convertValuesFromHorizontalToVertical(result);
		}
		return new HashMap<>();
	}

	private Map<String, List<Integer>> getChartData(List<AnswerReport> allAnswersToEventId) {
		Map<String, List<Integer>> result = new LinkedHashMap<>();
		
		for (AnswerReport answerReport : allAnswersToEventId) {
			if (answerReport.getQuestion().getQuestionType().contains("FIVE_SMILEYS")) {
				String key = "Question" + answerReport.getQuestion().getId();
				if (!result.containsKey(key)) {
					result.put(key, getAllAnswersToQuestionId(answerReport.getQuestion().getId(), allAnswersToEventId));
				}
			}
		}
		return result;
	}

	private Map<String, List<Integer>> convertValuesFromHorizontalToVertical(Map<String, List<Integer>> result) {
		Map<String, List<Integer>> newResult = new LinkedHashMap<>();
		
		do {
		List<Integer> newValues = new ArrayList<>();
		String key = "";
		for (Map.Entry<String, List<Integer>> mapEntry : result.entrySet()) {
			
			if (key == ""  && !newResult.keySet().contains(mapEntry.getKey())) {
				key = mapEntry.getKey();
			}
			
			List<Integer> value = mapEntry.getValue();
			
			for (Integer intValue : value) {
				newValues.add(intValue);
				value.remove(intValue);
				break;
				}
			}
		newResult.putIfAbsent(key, newValues);	
		
		}
		while (!result.values().iterator().next().isEmpty());
		return newResult;
	}

	private List<Integer> getAllAnswersToQuestionId(Long questionId, List<AnswerReport> allAnswersToEventId) {
		List<Integer> allAnswersToQuestionId = new ArrayList<>();
		int countRating1 = 0;
		int countRating2 = 0;
		int countRating3 = 0;
		int countRating4 = 0;
		int countRating5 = 0;
		for (AnswerReport answerReport : allAnswersToEventId) {
			if (answerReport.getQuestion().getId() == questionId) {
				switch (answerReport.getOption().getId().intValue()) {
				case 8:
					countRating5 = countRating5 + 1;
					break;
				case 7:
					countRating4 = countRating4 + 1;
					break;
				case 6:
					countRating3 = countRating3 + 1;
					break;
				case 5:
					countRating2 = countRating2 + 1;
					break;
				case 4:
					countRating1 = countRating1 + 1;
					break;	
				default:
					break;
				}
			}
		}
		allAnswersToQuestionId.add(countRating1);
		allAnswersToQuestionId.add(countRating2);
		allAnswersToQuestionId.add(countRating3);
		allAnswersToQuestionId.add(countRating4);
		allAnswersToQuestionId.add(countRating5);
		return allAnswersToQuestionId;
	}

	@GetMapping("/displayPieChart")
	public String pieChart(Model model) {
		model.addAttribute("pass", 50);
		model.addAttribute("fail", 50);
		return "pieChart";
	}

}
