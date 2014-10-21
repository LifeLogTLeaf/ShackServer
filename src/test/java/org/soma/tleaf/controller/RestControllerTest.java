/**
 * 
 */
package org.soma.tleaf.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.configuration.WebAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 16, 2014 2:07:15 PM
 * Description :
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebAppConfig.class })
public class RestControllerTest {
	Logger logger = LoggerFactory.getLogger(RestControllerTest.class);

	private MockMvc mockMvc;

	@Autowired
	RestApiController restController;

	// Create MockMvc
	@Before
	public void initMockMvc() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		mockMvc = MockMvcBuilders.standaloneSetup(restController).addFilter(filter).build();
	}

	@Test
	public void testSayHello() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/hello/13").accept(
				MediaType.valueOf("text/plain;charset=UTF-8"));
		this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void testPostUserLog() throws Exception {
		String content[] = {
				"{ \"data\": {\"content\":\"오늘은 기쁨\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"65\", \"mornig\":\"김치\", \"lunch\":\"김치\", \"evening\":\"김치\" } } }",
				"{ \"data\": {\"content\":\"오늘은 나쁨\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"64\", \"mornig\":\"물\", \"lunch\":\"물\", \"evening\":\"물\" } } }",
				"{ \"data\": {\"content\":\"오늘은 슬픔\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"63\", \"mornig\":\"냉면\", \"lunch\":\"냉면\" , \"evening\":\"냉면\"  } } }",
				"{ \"data\": {\"content\":\"오늘은 쵝오\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"62\", \"mornig\":\"갈비\", \"lunch\":\"갈비\" , \"evening\":\"갈비\"  } } }",
				"{ \"data\": {\"content\":\"오늘은 해피\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"61\", \"mornig\":\"오리고기\", \"lunch\":\"오리고기\", \"evening\":\"오리고기\" } } }",
				"{ \"data\": {\"content\":\"오늘은 우울\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"60\", \"mornig\":\"삼겹살\", \"lunch\":\"삼겹살\", \"evening\":\"삼겹살\" } } }",
				"{ \"data\": {\"content\":\"오늘은 행복\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"61\", \"mornig\":\"곰국\", \"lunch\":\"곰국\", \"evening\":\"곰국\" } } }",
				"{ \"data\": {\"title\":\"자바 공부\", \"state\":\"진행중\"} } }",
				"{ \"data\": {\"title\":\"씨언어 공부\", \"state\":\"진행중\"} } }",
				"{ \"data\": {\"title\":\"자바스크립트 공부\", \"state\":\"진행중\"} } }",
				"{ \"data\": {\"title\":\"씨플플 공부\", \"state\":\"진행중\"} } }",
				"{ \"data\": {\"title\":\"파이썬 공부\", \"state\":\"진행중\"} } }",
				"{ \"data\": {\"content\":\"오늘은 날아갈것같음\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"62\", \"mornig\":\"짜장면\", \"lunch\":\"짜장면\", \"evening\":\"짜장면\" } } }" };

		for (int i = 0; i < 13; i++) {
			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/user/log")
					.contentType(MediaType.APPLICATION_JSON).content(content[i]).accept(MediaType.APPLICATION_JSON);
			this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
		}
	}

	// @Test
	// public void testGetUserLog() throws Exception {
	// MockHttpServletRequestBuilder requestBuilder =
	// MockMvcRequestBuilders.get(
	// "/api/log?documentId=e309674c935107822fc5b15b8e0b82ec").accept(MediaType.APPLICATION_JSON);
	// this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk())
	// .andExpect(jsonPath("$.version", is("1.0.0")));
	// }
}
