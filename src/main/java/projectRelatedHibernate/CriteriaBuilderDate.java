package projectRelatedHibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class CriteriaBuilderDate {
	public static void main(String[] args) {
		Configuration con=new Configuration().configure().addAnnotatedClass(Team.class)
				.addAnnotatedClass(Associate.class).addAnnotatedClass(Star.class);
		ServiceRegistry reg=con.getStandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf=con.buildSessionFactory(reg);
		Session session=sf.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
		Root<Star> root = criteriaQuery.from(Star.class);
		Join<Star, Associate> join = root.join("assignedTo").join("team");
		criteriaQuery.where(builder.equal(join.get("team_id"), "CM0593"));
		//criteriaQuery.where(builder.equal(root.<Date>get("assignedDate"), "2018-12-19 00:00:00.0"));
		Path<Object> assignedBy = root.get("assignedBy");
		Path<Object> assignedTo = root.get("assignedTo");
		Path<Object> assignedDate = root.get("assignedDate");
		Path<Object> comment = root.get("comment");
		//criteriaQuery.where(builder.and(builder.greaterThan(assignedDate, getMondayOfTheWeek()), 
		criteriaQuery.multiselect(root,assignedTo,assignedBy, assignedDate,comment);
		List<Tuple> tuples = session.createQuery(criteriaQuery).getResultList();
		for(Tuple tuple : tuples)
		{
			Date assigneddate = (Date)tuple.get(assignedDate);
			String comment1 = (String)tuple.get(comment);
			System.out.println(comment1+"  "+assigneddate);
		}
	}
	private static Date getMondayOfTheWeek() {
		Date monday = null;
		Calendar today = Calendar.getInstance();
		int difference;
		int day = today.get(Calendar.DAY_OF_WEEK);
		difference = day - Calendar.MONDAY;
		today.add(Calendar.DAY_OF_WEEK, -difference);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.MILLISECOND, 0);
		today.set(Calendar.SECOND, 0);
		monday = today.getTime();
		return monday;
	}

	private static Date getFridayOfTheWeek() {
		Date friday = null;
		Calendar today = Calendar.getInstance();
		int difference;
		int day = today.get(Calendar.DAY_OF_WEEK);
		difference = Calendar.FRIDAY - day;
		today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_WEEK, difference);
		friday = today.getTime();
		return friday;
	}
}