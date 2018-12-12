/**
 * 
 */
package com.badenia.feedback.feedbacksystem.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.badenia.feedback.feedbacksystem.service.IFeedbackService;
import com.badenia.feedback.feedbacksystem.service.model.Answer;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureJsonTesters
public class AnswerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	IFeedbackService feedbackServiceMock;

	@Autowired
	JacksonTester<Answer> json;

	/**
	 * Test method for
	 * {@link com.badenia.feedback.feedbacksystem.controller.AnswerController#saveAnswer(com.badenia.feedback.feedbacksystem.service.model.Answer)}.
	 */
	@Test
	@Ignore
	public void testSaveAnswer_DateNotSpecified_ReturnBadRequest() throws Exception {

		mockMvc.perform(post("/v1/feedback/answers").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json.write(Answer.builder().optionId(2L).questionId(3L).build()).getJson())).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.apierror.status", is("NOT_FOUND")))
				.andExpect(jsonPath("$.apierror.message", is("Event was not found for parameters {id=20}")))
				.andExpect(jsonPath("$.apierror.debugMessage", isEmptyOrNullString()))
				.andExpect(jsonPath("$.apierror.subErrors", nullValue()));

	}

}
