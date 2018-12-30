package projectRelatedHibernate;

import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class CriteriaBuilderMultiValues {

	public static void main(String[] args) {
		Configuration con=new Configuration().configure().addAnnotatedClass(Team.class)
				.addAnnotatedClass(Associate.class).addAnnotatedClass(Star.class);
		ServiceRegistry reg=con.getStandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf=con.buildSessionFactory(reg);
		Session session=sf.openSession();
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
		Root<Associate> associate = criteria.from(Associate.class);
		Root<Team> team = criteria.from(Team.class);
		criteria.multiselect(associate,team);
		//Predicate assigned = builder.and(builder.equal("a", "a"));

		Query<Tuple> query = session.createQuery(criteria);
		List<Tuple> list = query.list();
		for(Tuple star: list)
		{
			System.out.println(star);
		}
	}
}
