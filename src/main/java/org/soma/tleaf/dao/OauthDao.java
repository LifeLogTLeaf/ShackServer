package org.soma.tleaf.dao;

import org.soma.tleaf.exception.DatabaseConnectionException;

public interface OauthDao {
	public boolean isAppIdValid( String appId ) throws DatabaseConnectionException;
}
