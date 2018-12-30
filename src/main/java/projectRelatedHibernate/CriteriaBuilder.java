package projectRelatedHibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class CriteriaBuilder {

	public static void main(String[] args) {
		Configuration con = new Configuration().configure().addAnnotatedClass(Team.class)
				.addAnnotatedClass(Associate.class).addAnnotatedClass(Star.class);
		ServiceRegistry reg = con.getStandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf = con.buildSessionFactory(reg);
		Session session = sf.openSession();
		javax.persistence.criteria.CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Star> criteriaQuery = builder.createQuery(Star.class);
		Root<Star> root = criteriaQuery.from(Star.class);
		criteriaQuery.select(root);
		// criteriaQuery.where(builder.equal(root.get("id"), 1));
		// criteriaQuery.where(builder.equal(root.get("comment"), "Three12"));
		Query<Star> query = session.createQuery(criteriaQuery);
		List<Star> list = query.list();
		for (Star star : list) {
			System.out.println(star);
		}
	}
}
/*
 * Join<Star, Associate> item = root.join("assignedTo",
 * JoinType.INNER).join("team",JoinType.INNER);
 * criteriaQuery.where(builder.equal(item.get("team_id"), "CM0593"));
 * criteriaQuery.select(root);
 */