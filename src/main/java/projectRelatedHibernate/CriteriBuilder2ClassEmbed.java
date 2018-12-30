package projectRelatedHibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class CriteriBuilder2ClassEmbed {

	public static void main(String[] args) {
		Configuration con = new Configuration().configure().addAnnotatedClass(Team.class)
				.addAnnotatedClass(Associate.class).addAnnotatedClass(Star.class);
		ServiceRegistry reg = con.getStandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf = con.buildSessionFactory(reg);
		Session session = sf.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Associate> criteriaQuery = builder.createQuery(Associate.class);
		Root<Associate> rootStar = criteriaQuery.from(Associate.class);
		Join<Associate, Team> join = rootStar.join("team");
		criteriaQuery.where(builder.equal(join.get("team_id"), "CM0593"));
		criteriaQuery.select(rootStar);
		Query<Associate> query = session.createQuery(criteriaQuery);
		List<Associate> list = query.list();
		System.out.println("AssignedTo" + "    " + "AssignedBy" + "   " + "Comment");
		for (Associate star : list) {
			System.out.print(star.getId() + "      ");
			System.out.print(star.getName() + "      ");
			System.out.print(star.getTeam());
			System.out.println();
		}
	}
}