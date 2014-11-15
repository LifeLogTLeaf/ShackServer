package org.soma.tleaf.dao;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.soma.tleaf.couchdb.CouchDbConn;
import org.soma.tleaf.exception.DatabaseConnectionException;

public class OauthDaoImpl implements OauthDao {

	@Inject
	private CouchDbConn couchDbConn;
	
	private CouchDbConnector couchDbConnector;
	private static final String APIKEY_DB_NAME = "tleaf_services";
	
	@Override
	public boolean isAppIdValid(String appId) throws DatabaseConnectionException {
		
		couchDbConnector = couchDbConn.getCouchDbConnetor(APIKEY_DB_NAME);
		
		if ( couchDbConnector.find(Object.class, appId) == null )
			return false;
		
		return true;
	}

}
