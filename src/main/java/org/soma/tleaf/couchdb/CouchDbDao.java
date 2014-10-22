package org.soma.tleaf.couchdb;

import org.ektorp.AttachmentInputStream;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;

public interface CouchDbDao {
	
	public void createDatabaseIfNotExists();
	
	public void create ( Object obj );
	public void create ( String id, Object obj );
	
	public String delete ( String id, String rev );
	public String delete ( Object obj );
	// returns the revision of deleted document
	
	public <T> T get ( Class<T> c, String id );
	// throws exception if not found
	
	public <T> T find ( Class<T> c, String id );
	// throws null if not found
	
	public boolean contains ( String id );
	
	public String createAttachment ( String docId, AttachmentInputStream data );
	// Creates both the document and the attachment
	public String createAttachment ( String docId, String rev, AttachmentInputStream data );
	// Adds an attachment to the specified document id
	// returns revision of the created attachment document
	
	public ViewResult queryView( ViewQuery viewQuery );

}
