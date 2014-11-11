/**
 * 
 */
package org.soma.tleaf.domain;

import java.util.ArrayList;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Nov 11, 2014 4:33:47 PM
 * Description : 
 */
@JsonIgnoreProperties(ignoreUnknown=true) 	
public class SimpleUserInfo {
	
	@JsonProperty("_id")
	private String hashId;
	
	@JsonProperty("_rev")
	private String rev;
	
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
	
	@JsonProperty("services")
	private ArrayList<Map> services;

	public String getHashId() {
		return hashId;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getGender() {
		return gender;
	}

	public Integer getAge() {
		return age;
	}

	public String getRev() {
		return rev;
	}

	public void setHashId(String hashId) {
		this.hashId = hashId;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public ArrayList<Map> getServices() {
		return services;
	}

	public void setServices(ArrayList<Map> services) {
		this.services = services;
	}

}
