package org.asck.web.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {

	private Long id;
	
	private String questionName;
	
	private String questionType;
	
	private int order;
	
	private boolean answerRequired;
	
}
