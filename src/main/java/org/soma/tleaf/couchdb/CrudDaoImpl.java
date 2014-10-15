package org.soma.tleaf.couchdb;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;

public class CrudDaoImpl implements CrudDao {

	@Inject
    private CouchDbConn couchDbConn;
	
	private CouchDbConnector couchDbConnector;
	private CouchDbInstance couchDbInstance;
	
	public CrudDaoImpl ( String dbName ) throws Exception {
		initCrud( dbName );
	}
	
	private void initCrud( String dbName ) throws Exception {
		couchDbConnector = couchDbConn.getCouchDbConnetor(dbName);
		couchDbInstance = couchDbConn.getCouchDbInstance();
	}
	
	@Override
	public String createDocument(Object document) throws Exception {
		// TODO Auto-generated method stub
		
		return null;
	}

}
