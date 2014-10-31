/**
 * 
 */
package org.soma.tleaf.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.SimpleData;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Oct 30, 2014 7:47:23 PM
 * Description : 
 */
public class RawDataRepository extends CouchDbRepositorySupport<RawData> {
	public RawDataRepository(CouchDbConnector db) {
		super(RawData.class, db);
		initStandardDesignDocument();
	}

	@Override
	@View(name = "all", map = "function(doc) { if (doc.time) emit( doc.time, doc )}")
	public List<RawData> getAll() {
		// TODO Auto-generated method stub
		ViewQuery q = createQuery("all");
		return db.queryView(q, RawData.class);
	}

}
