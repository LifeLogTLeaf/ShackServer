package org.soma.tleaf.couchdb;

import org.ektorp.AttachmentInputStream;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.springframework.stereotype.Repository;

@Repository
public class CouchDbDaoImpl implements CouchDbDao {

	@Override
	public void createDatabaseIfNotExists() {
		// TODO Auto-generated method stub

	}

	@Override
	public void create(Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void create(String id, Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public String delete(String id, String rev) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T get(Class<T> c, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T find(Class<T> c, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String createAttachment(String docId, AttachmentInputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createAttachment(String docId, String rev,
			AttachmentInputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult queryView(ViewQuery viewQuery) {
		// TODO Auto-generated method stub
		return null;
	}

}
