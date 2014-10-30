/**
 * 
 */
package org.soma.tleaf.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.configuration.WebAppConfig;
import org.soma.tleaf.domain.SimpleRawData;
import org.soma.tleaf.exception.CommonExceptionHandler;
import org.soma.tleaf.util.ISO8601;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
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
	
	private final String USERID_HEADER_NAME = "x-tleaf-user-id";
	private final String APPID_HEADER_NAME = "x-tleaf-application-id"; 
	private final String ACCESSKEY_HEADER_NAME = "x-tleaf-access-token";
	private String accessKey = "e0da8f9f1fe2b3ca4d8872de6f01df5c";
	private String userId = "e0da8f9f1fe2b3ca4d8872de6f01d29f";
	private String appId = "tiary";
	

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


	//@Test
	// 하나의 데이터를 저장하는 테스트 입니다.
	public void testPostUserLog() throws Exception {
		String URL = "/api/user/app/log";
		String content = buildDummy();
		logger.info(content);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URL)
				.header(ACCESSKEY_HEADER_NAME, accessKey)
				.header(APPID_HEADER_NAME, appId)
				.header(USERID_HEADER_NAME, userId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content)
				.accept(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
	}

	
	//@Test
	public void accessKeyCehck() {
		String URL = "/api/hello/world";
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(URL)
				.header(ACCESSKEY_HEADER_NAME, accessKey)
				.header(APPID_HEADER_NAME, appId)
				.header(USERID_HEADER_NAME, userId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);		
		try {
			this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//@Test
	public void fileSave() {
		String URL = "/api/hello/file";
		InputStream inputStream;
		
		MockMultipartFile file = null;
		try {
			inputStream = new FileInputStream("/Users/jangyoungjin/Downloads/1.png");
			file = new MockMultipartFile("file", inputStream);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.fileUpload(URL)
				.file(file);
		try {
			this.mockMvc.perform(requestBuilder)
						.andDo(print())
						.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private String buildDummy(){
		Map<String, Object>data = new HashMap<String, Object>();
		ArrayList<String> array = new ArrayList<String>();
		data.put("type", "normal");
		data.put("title", "오늘은 너무 행복한 하루였다.");
		data.put("content", "난 오늘 11시에 태평역에서 가산디지털단지역으로 지하철을 타고 갔다. 도착하고 나서 짜장범벅을 먹었고 일하고 일하고 일하고 일하고 전화하고 하다가 오후 10시 37분인데 일하고 서류 만들고 이러고 있다. 배가 고파서 뭘 먹을까 배달의 민족을 10분전에 찾아보다가 별로 땡기는게 없어서 편의점에 갈까 고민중이다.");
		data.put("emotion", "happy");
		array.add("날씨");
		array.add("장소");
		array.add("만남사람이름");
		data.put("tag", array);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			json = ow.writeValueAsString(data);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return json;
	}
	


}
