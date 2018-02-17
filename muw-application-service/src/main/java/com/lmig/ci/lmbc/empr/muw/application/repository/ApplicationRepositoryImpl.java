package com.lmig.ci.lmbc.empr.muw.application.repository;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.lmig.ci.lmbc.empr.muw.application.domain.Application;

public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom{

	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<Application> findByEmployerIdAndApplicationStatus(int employerId, String applicationStatus) {
          
		
		Query query = null;
	   
		if(applicationStatus.equals("closed"))
		{
			query = entityManager.createQuery("select distinct appln "
					//	+ "appcnt.applicantProducts, appduts.statusTypeCode "
					 		+ "from Application appln join fetch appln.applicants appcnt "
					 		+ " join fetch appcnt.applicantProducts appduts "
			+ " where appln.employerId = :emplrId "
			 		+ "and appduts.statusTypeCode in ('ATOAPP', 'ATODEN', 'APPRVE', 'DENIED', 'CLOSED', 'ATOCLS') ");
			 query.setParameter("emplrId", employerId);
			// query.setParameter("statusTypeCode", applicationStatus);
		}
		else if(applicationStatus.equals("open"))
		{
			query = entityManager.createQuery("select distinct appln "
					//	+ "appcnt.applicantProducts, appduts.statusTypeCode "
					 		+ "from Application appln join fetch appln.applicants appcnt "
					 		+ " join fetch appcnt.applicantProducts appduts "
			+ " where appln.employerId = :emplrId "
			 		+ "and appduts.statusTypeCode in ( 'MANREV', 'REQINF', 'APPEAL') ");
		     query.setParameter("emplrId", employerId);
		    // query.setParameter("sttype", applicationStatus);
		}
		else
		{
			query = entityManager.createQuery("select distinct appln "
					//	+ "appcnt.applicantProducts, appduts.statusTypeCode "
					 		+ "from Application appln join fetch appln.applicants appcnt "
					 		+ " join fetch appcnt.applicantProducts appduts "
			+ " where appln.employerId = :emplrId "
			 		+ "and appduts.statusTypeCode in () ");
		     query.setParameter("emplrId", employerId);
		
		}
		
	   //query.setParameter("applicationId", applicationId);
       //   query.setParameter("statusTypeCode", statusTypeCode);
        setEntityGraphAsHint(query);
        List<Application> found = query.getResultList();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(found.size());
        System.out.println("----------------------------------------------------------------------------");
        
        return found;
    }
	
	private void setEntityGraphAsHint(Query query) {
        EntityGraph<?> graph = entityManager.getEntityGraph("graph.Application");
        query.setHint("javax.persistence.loadgraph", graph);
    }
	}

	
	

