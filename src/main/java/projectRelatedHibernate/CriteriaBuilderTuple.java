package projectRelatedHibernate;

import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class CriteriaBuilderTuple {

	public static void main(String[] args) {
		Configuration con=new Configuration().configure().addAnnotatedClass(Team.class)
				.addAnnotatedClass(Associate.class).addAnnotatedClass(Star.class);
		ServiceRegistry reg=con.getStandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf=con.buildSessionFactory(reg);
		Session session=sf.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
		Root<Star> root = criteriaQuery.from(Star.class);
		Root<Team> team = criteriaQuery.from(Team.class);
		Path<Object> assignedBy = root.get("assignedBy");
		Path<Object> assignedTo = root.get("assignedTo");
		//Path<Object> team = root.get("team");
		criteriaQuery.multiselect( assignedBy, assignedTo);
		List<Tuple> tuples = session.createQuery(criteriaQuery).getResultList();
		for(Tuple tuple : tuples)
		{
			Associate assignedby = (Associate) tuple.get(assignedBy);
			Associate assignedto = (Associate) tuple.get(assignedTo);
			//Team teaminfo = (Team) tuple.get(team);
			System.out.println(assignedby+"   "+assignedto);
		}
	}
}
