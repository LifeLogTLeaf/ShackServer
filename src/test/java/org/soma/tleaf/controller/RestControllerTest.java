/**
 * 
 */
package org.soma.tleaf.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.configuration.WebAppConfig;
import org.soma.tleaf.exception.CommonExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

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

	// MockMvc를 생성합니다.
	@Before
	public void initMockMvc() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		mockMvc = MockMvcBuilders.standaloneSetup(restController).setHandlerExceptionResolvers(createExceptionResolver()).addFilter(filter).build();
	}

	// @ControllerAdvice 어노테이션이된 전역 예외처리 클래스를 사용하기 위해서입니다.
	// 최범균씨의 소스코드를 인용했습니다.
	public static ExceptionHandlerExceptionResolver createExceptionResolver() {
		ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
			@Override
			protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
				// 익셉션을 CommonExceptionHandler가 처리하도록 설정
				Method method = new ExceptionHandlerMethodResolver(CommonExceptionHandler.class).resolveMethod(exception);
				return new ServletInvocableHandlerMethod(new CommonExceptionHandler(), method);
			}
		};
		exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		exceptionResolver.afterPropertiesSet();
		return exceptionResolver;
	}

	// @Test
	// 많은 데이터를 저장하는 테스트 입니다.
	public void testPostUserLog() throws Exception {
		String content[] = {
				"{ \"data\": {\"content\":\"오늘은 기쁨\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"65\", \"mornig\":\"김치\", \"lunch\":\"김치\", \"dinner\":\"김치\" } } }",
				"{ \"data\": {\"content\":\"오늘은 나쁨\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"64\", \"mornig\":\"물\", \"lunch\":\"물\", \"dinner\":\"물\" } } }",
				"{ \"data\": {\"content\":\"오늘은 슬픔\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"63\", \"mornig\":\"냉면\", \"lunch\":\"냉면\" , \"dinner\":\"냉면\"  } } }",
				"{ \"data\": {\"content\":\"오늘은 쵝오\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"62\", \"mornig\":\"갈비\", \"lunch\":\"갈비\" , \"dinner\":\"갈비\"  } } }",
				"{ \"data\": {\"content\":\"오늘은 해피\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"61\", \"mornig\":\"오리고기\", \"lunch\":\"오리고기\", \"dinner\":\"오리고기\" } } }",
				"{ \"data\": {\"content\":\"오늘은 우울\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"60\", \"mornig\":\"삼겹살\", \"lunch\":\"삼겹살\", \"dinner\":\"삼겹살\" } } }",
				"{ \"data\": {\"content\":\"오늘은 행복\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"61\", \"mornig\":\"곰국\", \"lunch\":\"곰국\", \"dinner\":\"곰국\" } } }",
				"{ \"data\": {\"title\":\"자바 공부\", \"state\":\"진행중\"} } }", "{ \"data\": {\"title\":\"씨언어 공부\", \"state\":\"진행중\"} } }",
				"{ \"data\": {\"title\":\"자바스크립트 공부\", \"state\":\"진행중\"} } }", "{ \"data\": {\"title\":\"씨플플 공부\", \"state\":\"진행중\"} } }",
				"{ \"data\": {\"title\":\"파이썬 공부\", \"state\":\"진행중\"} } }" };

		for (int i = 0; i < 10; i++) {
			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
					.post("api/user/app/log?accessKey=a869d8c5662f5b16660068660600e6cf&appId=19891011&userId=a869d8c5662f5b16660068660600db2d")
					.contentType(MediaType.APPLICATION_JSON).content(content[i]).accept(MediaType.APPLICATION_JSON);
			this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
		}
	}

	@Test
	// 하나의 데이터를 저장하는 테스트 입니다.
	public void testPostOndeUserLog() throws Exception {
		String content = "{ \"data\": {\"content\":\"오늘은 기쁨\", \"template\":\"다이어트\", \"tag\":{ \"weigh\":\"65\", \"mornig\":\"김치\", \"lunch\":\"김치\", \"dinner\":\"김치\" } } }";
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("api/user/logs?accessKey=a869d8c5662f5b16660068660600e6cf&appId=19891011&userId=a869d8c5662f5b16660068660600db2d")
				.contentType(MediaType.APPLICATION_JSON).content(content).accept(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
	}

	// @Test
	// 엑세스키 예외처리 상태를 확인하기 위한 테스트 입니다.
	public void testPostUserLogWithAccessKey() throws Exception {
		String content = "{ \"data\": {\"title\":\"자바스크립트 공부\", \"state\":\"진행중\"} } }";

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/user/log").contentType(MediaType.APPLICATION_JSON)
				.content(content).accept(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isUnauthorized());
	}

	// @Test
	// 사용자의 전체 로그를 가져옵니다.
	public void testGetAllUserLogWithAccessKey() throws Exception {
	}

}
