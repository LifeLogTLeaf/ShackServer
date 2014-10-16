package org.soma.tleaf.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HashId {
	
	/*public HashId ( String email ) {
		this.email = email;
		hashId = (int) (Math.random() * 1000000000);
	}*/
	
	@JsonProperty("_id")
	private String email;
	
	@JsonProperty("hash_id")
	private String hashId;

	public String getEmail() {
		return email;
	}

	public String getHashId() {
		return hashId;
	}

}