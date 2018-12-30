package projectRelatedHibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

public class App {

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static void main(String[] args) {		
		Configuration con=new Configuration().configure().addAnnotatedClass(Team.class)
				.addAnnotatedClass(Associate.class).addAnnotatedClass(Star.class);
		ServiceRegistry reg=con.getStandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf=con.buildSessionFactory(reg);
		Session session=sf.openSession();
		Criteria criteria = session.createCriteria(Star.class, "star");
				criteria.createAlias("star.assignedTo", "assigned");
				criteria.createAlias("assigned.team", "team");
				  //.setProjection(Projections.projectionList()
				//.add(Projections.property("assignedBy.name"), "TkjsdhfNfksdh"));
		//criteria.add(Restrictions.eq("assignedBy.id", "TN034588"));
		criteria.add(Restrictions.eq("team.team_id", "CM0593"));
		//criteria.setFetchMode("id", FetchMode.SELECT);
		List list=criteria.list();
		for(Object o : list)
		{
			System.out.println(o);
		}
		//Star str = session.get(Star.class, 1);
		//System.out.println(str);
		//Star star = session.find(Star.class, 2);
		//System.out.println(star.getAssignedBy());
	}
}