/**
 * 
 */
package org.soma.tleaf.httptest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 31, 2014 12:52:49 AM
 * Description :
 */
public class FilePost {
	static Logger logger = LoggerFactory.getLogger(FilePost.class);
	private static final String FILEPATH = "/Users/jangyoungjin/Downloads/1.png";

	@Test
	public void sendFile() {
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpPost httpPost = new HttpPost("http://localhost:8080/api/hello/file");

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("/Users/jangyoungjin/Downloads/1.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputStreamBody inputStreamBody = new InputStreamBody(inputStream, "*.png");

		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		multipartEntityBuilder.addPart("file", inputStreamBody);
		httpPost.setEntity(multipartEntityBuilder.build());

		int resultCode;
		StringBuilder builder = new StringBuilder();
		try {
			// Execute
			HttpResponse response = client.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			resultCode = statusLine.getStatusCode();
			if (resultCode == 200) {
				HttpEntity resultEntity = response.getEntity();
				InputStream result = resultEntity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(result));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info(builder.toString());
		
	}
}
