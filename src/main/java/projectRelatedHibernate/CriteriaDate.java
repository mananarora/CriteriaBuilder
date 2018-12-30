package projectRelatedHibernate;


import java.util.Calendar;
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

public class CriteriaDate {

	public static void main(String[] args) {
		Configuration con = new Configuration().configure().addAnnotatedClass(Team.class)
				.addAnnotatedClass(Associate.class).addAnnotatedClass(Star.class);
		ServiceRegistry reg = con.getStandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf = con.buildSessionFactory(reg);
		Session session = sf.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Star> criteriaQuery = builder.createQuery(Star.class);
		Root<Star> root = criteriaQuery.from(Star.class);
		Join<Star, Associate> join = root.join("assignedTo").join("team");
		//Join<Star, Associate> join1 = root.join("assignedTo");
		criteriaQuery.select(root).where(builder.equal(join.get("team_id"), "CM0593"),//builder.equal(join1.get("id"), "FG093845"),
				builder.greaterThanOrEqualTo(root.<Date>get("assignedDate"), getMondayOfTheWeek()),
				builder.lessThanOrEqualTo(root.<Date>get("assignedDate"), getFridayOfTheWeek()));
		Query<Star> query = session.createQuery(criteriaQuery);
		List<Star> list = query.list();
		HashMap<String,Integer> CountofAssignedTo= new HashMap<String, Integer>();
		for(Star s:list)
		{
			if(CountofAssignedTo.get(s.getAssignedTo().getId())!=null)
			{
				int tt=CountofAssignedTo.get(s.getAssignedTo().getId());
				CountofAssignedTo.put(s.getAssignedTo().getId(),++tt);
			}
			else
			{
				CountofAssignedTo.put(s.getAssignedTo().getId(), 1);
			}
		}
		final HashMap<String, Integer> temp=CountofAssignedTo;
		
		Comparator<Star> compareByCount=new Comparator<Star>() {
			public int compare(Star first, Star second) {
			return temp.get(second.getAssignedTo().getId())-temp.get(first.getAssignedTo().getId());
			}
		};
		Collections.sort(list);		
		Collections.sort(list,compareByCount);
		for(Star s:list)
		{
			System.out.print(s.getAssignedTo().getId() + "      ");
			System.out.print(s.getAssignedBy().getId() + "      ");
			System.out.print(s.getComment());
			System.out.println();
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
	//Join<Star, Associate> join = root.join("assignedTo");
	//criteriaQuery.where(builder.equal(join.get("id"), "FG093845"));
	//criteriaQuery.select(root).where(builder.equal(root.get("id"), 1));
			//criteriaQuery.select(root).where(builder.greaterThanOrEqualTo(root.<Date>get("assignedDate"), getMondayOfTheWeek()));
			//criteriaQuery.select(root).where(builder.lessThanOrEqualTo(root.<Date>get("assignedDate"), getFridayOfTheWeek()));
}
//criteriaQuery.where(builder.greaterThanOrEqualTo();
		/*Date mon = getMondayOfTheWeek();
		Date fri = getFridayOfTheWeek();*/
		/*List<Predicate> predicates = new ArrayList<Predicate>(); 
		ParameterExpression<Date> param = builder.parameter(Date.class, "dateLimit");
		Path<Date> checkDatePath = root.<Date> get("checkDate");
		Predicate startDatePredicate = builder.greaterThanOrEqualTo(checkDatePath, getMondayOfTheWeek());
		Predicate endDatePredicate = builder.lessThanOrEqualTo(checkDatePath, getFridayOfTheWeek());*/
		//predicates.add(builder.lessThanOrEqualTo(root.<Date>get("fri"), param));
		/*Join<Star, Associate> join2 = root.join("team");
		criteriaQuery.multiselect(root.get("assignedDate"),
				root.get("comment"));
		criteriaQuery.where(builder.equal(join.get("team_id"), "CM0593"));*/
		//criteriaQuery.where(builder.equal(join.get("team_name"), "Capacity Management"));
		/*Join<Star, Associate> join2 = root.join("assignedTo").join("team");
		criteriaQuery.where(builder.equal(join2.get("id"), "FG093845"));*/
		//criteriaQuery.where(builder.greatest(getMondayOfTheWeek()));
		//criteriaQuery.where(builder.between(root.get("assignedDate"), getMondayOfTheWeek(), getFridayOfTheWeek()));
      //criteriaQuery.groupBy(root.get("associd"));
      //criteriaQuery.having(builder.ge(root.get("assignedDate"), getMondayOfTheWeek()));
		//criteriaQuery.where(builder.ge(getMondayOfTheWeek(), getFridayOfTheWeek());(getMondayOfTheWeek(), getFridayOfTheWeek()));
		/*Predicate date =  criteriaQuery.where(builder.gt(root.get(("assignedDate"), getFridayOfTheWeek(),("assignedDate", getMondayOfTheWeek()));
	    predicate.add(date);*/
		//ParameterExpression<Date> d = builder.parameter(Date.class);
		//builder.between(builder.literal(date), root.<Date>get("from"), root.<Date>get("to")); 
		/*builder.between(d, root.get(getMondayOfTheWeek()), root.get(getFridayOfTheWeek()));
		builder.between(date, root.<Date>get("startDate"), root.<Date>get("endDate")); */
		//criteriaQuery.where(builder.between(builder.function("week", Integer.class, root.get("assignedDate")), getMondayOfTheWeek(), getFridayOfTheWeek()));
		/*List<Predicate> predicates = new ArrayList<Predicate>(); 
		ParameterExpression<Date> param = builder.parameter(Date.class, "dateLimit");
		predicates.add(builder.lessThanOrEqualTo(root.<Date>get("dateCreated"), param));*/
//criteriaQuery.where(builder.between(root.get("assignedDate")), getMondayOfTheWeek(), getFridayOfTheWeek());
		/*.add(Expression.ge("date",getMondayOfTheWeek())
				.add(Expression.le("date",getFridayOfTheWeek());*/
		/*ParameterExpression<Date> currentDate = builder.parameter(Date.class, "currentDate");
		List<Predicate> predicates = new ArrayList<Predicate>(); 
		predicates.add(builder.greaterThan(currentDate, root.<Date>get(getMondayOfTheWeek())));*/