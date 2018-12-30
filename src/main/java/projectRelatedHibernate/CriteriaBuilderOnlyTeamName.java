package projectRelatedHibernate;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class CriteriaBuilderOnlyTeamName {

	public static void main(String[] args) {

		Date myDate = new Date();
		System.out.println(myDate);
		Configuration con = new Configuration().configure().addAnnotatedClass(Team.class)
				.addAnnotatedClass(Associate.class).addAnnotatedClass(Star.class);
		ServiceRegistry reg = con.getStandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf = con.buildSessionFactory(reg);
		Session session = sf.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Star> criteriaQuery = builder.createQuery(Star.class);
		CriteriaQuery<Team> criteriaQueryTeam = builder.createQuery(Team.class);
		Root<Star> rootStar = criteriaQuery.from(Star.class);
		Root<Team> rootTeam = criteriaQueryTeam.from(Team.class);
		Join<Star, Associate> join = rootStar.join("assignedTo").join("team");
		Join<Star, Associate> join1 = rootStar.join("assignedTo");
		criteriaQuery.where(builder.equal(join.get("team_id"), "CM0593"));
		criteriaQueryTeam.where(builder.equal(rootTeam.get("team_id"), "CM0593"));
		criteriaQuery.orderBy(builder.desc(join1.get("id")));
		criteriaQuery.select(rootStar);
		Query<Star> query = session.createQuery(criteriaQuery);
		Query<Team> queryTeam = session.createQuery(criteriaQueryTeam);
		Team listTeam = queryTeam.uniqueResult();
		List<Star> list = query.list();
		HashMap<String, Integer> CountofAssignedTo = new HashMap<String, Integer>();
		for (Star s : list) {
			if (CountofAssignedTo.get(s.getAssignedTo().getId()) != null) {
				int tt = CountofAssignedTo.get(s.getAssignedTo().getId());
				CountofAssignedTo.put(s.getAssignedTo().getId(), ++tt);
			} else {
				CountofAssignedTo.put(s.getAssignedTo().getId(), 1);
			}
		}
		final HashMap<String, Integer> temp = CountofAssignedTo;

		Comparator<Star> compareByCount = new Comparator<Star>() {
			public int compare(Star first, Star second) {
				return temp.get(second.getAssignedTo().getId()) - temp.get(first.getAssignedTo().getId());
			}
		};
		Collections.sort(list);
		Collections.sort(list, compareByCount);
		System.out.println("Team" + "    " + "AssignedTo" + "    " + "AssignedBy" + "   " + "Comment");
		String team = listTeam.getTeam_name();
		System.out.print(team);
		for (Star star : list) {
			System.out.print(star.getAssignedTo().getId() + "     ");
			System.out.print(star.getAssignedBy().getId() + "     ");
			System.out.print(star.getComment());
			System.out.println();
		}
	}
}