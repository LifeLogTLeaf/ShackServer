package org.soma.tleaf.accesskey;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AccessKey {
	
	@JsonProperty("_id")
	private String accessKey;
	
	@JsonProperty("_id")
	private String rev;
	
	// Both field will be in ISO 8601 format.
	private String vaildFrom;
	private String vaildTo;
	
	// TODO add methods that checks expire dates

}
