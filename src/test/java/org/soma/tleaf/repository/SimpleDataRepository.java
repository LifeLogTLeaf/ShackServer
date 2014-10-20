/**
 * 
 */
package org.soma.tleaf.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.Page;
import org.ektorp.PageRequest;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.soma.tleaf.domain.SimpleData;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 16, 2014 1:05:53 PM
 * Description :
 */

public class SimpleDataRepository extends CouchDbRepositorySupport<SimpleData> {
	public SimpleDataRepository(CouchDbConnector db) {
		super(SimpleData.class, db);
		initStandardDesignDocument();
	}

	public List<SimpleData> findByType(String type) {
		return queryView("by_type", type);
	}

	@Override
	@View(name = "all", map = "function(doc) { if (doc) emit( doc._id, doc )}")
	public List<SimpleData> getAll() {
		// TODO Auto-generated method stub
		ViewQuery q = createQuery("all");
		return db.queryView(q, SimpleData.class);
	}

	// it's a little bit dependency
	// 1. The method mush be named findBy[Property]
	// 2. The method may only have one parameter
	// 3. The property must exist in the target class.
	// 4. The generated view will be named by_[property].
	@GenerateView
	public List<SimpleData> findByAppAuthor(String appAuthor) {
		return queryView("by_appAuthor", appAuthor);
	}

}
