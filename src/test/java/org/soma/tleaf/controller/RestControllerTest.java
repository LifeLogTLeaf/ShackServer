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
//		this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void testPostUserLog() throws Exception {
		String content = "{ \"data\": {\"content\":\"오늘..\", \"dietData\":{ \"weigh\":\"65\", \"mornig\":\"kimchi\" } } }";

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/log")
				.contentType(MediaType.APPLICATION_JSON).content(content).accept(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void testGetUserLog() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/log?documentId=e309674c935107822fc5b15b8e0b0682").accept(
				MediaType.APPLICATION_JSON);
//		this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk())
//				//.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.version", is("1.0.0")));
	}
}
