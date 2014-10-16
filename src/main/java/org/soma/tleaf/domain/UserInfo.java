package org.soma.tleaf.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true) 	
public class UserInfo {
	
	@JsonProperty("_id")
	private String hashId;
	
	@JsonProperty("nickname")
	private String nickname;
	
	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("age")
	private Integer age;

	public String getHashId() {
		return hashId;
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getGender() {
		return gender;
	}

	public Integer getAge() {
		return age;
	}

}
