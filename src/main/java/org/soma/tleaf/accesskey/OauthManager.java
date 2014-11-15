package org.soma.tleaf.accesskey;

import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.DatabaseConnectionException;

public interface OauthManager {
	
	public boolean isAppIdValid ( String appId ) throws DatabaseConnectionException;
	public String createLoginAccessCode ( String appId );
	public boolean checkLoginAccessCode ( String accessCode) throws CustomException;

}
