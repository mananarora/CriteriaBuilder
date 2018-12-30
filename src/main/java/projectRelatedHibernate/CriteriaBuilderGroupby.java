package projectRelatedHibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class CriteriaBuilderGroupby {

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
		Join<Star, Associate> join1 = root.join("assignedTo");
		criteriaQuery.where(builder.equal(join.get("team_id"), "CM0593"));
		criteriaQuery.groupBy(join1.get("id"), root.get("comment"), root.get("id"));
		Expression<String> groupById = join1.get("id");
	    Expression<Long> count1 = builder.count(groupById);
		criteriaQuery.orderBy(builder.asc(groupById), builder.desc(count1));
		/*Expression<String> groupById = join1.get("id");
	    Expression<Long> count = builder.count(groupById);
	    //CriteriaQuery<Star> select =criteriaQuery.multiselect(groupById, groupByComment, count);
	    criteriaQuery.groupBy(groupById);
	    criteriaQuery.orderBy(builder.desc(count));*/
		criteriaQuery.select(root);
		Query<Star> query = session.createQuery(criteriaQuery);
		List<Star> list = query.list();
		System.out.println("AssignedTo"+"    "+"AssignedBy"+"   "+"Comment");
		for (Star star : list) {
			System.out.print(star.getAssignedTo().getId()+"     ");
			System.out.print(star.getAssignedBy().getId()+"     ");
			System.out.print(star.getComment());
			System.out.println();
		}
	}
}
/*
 * Join<Star, Associate> item = root.join("assignedTo",
 * JoinType.INNER).join("team",JoinType.INNER);
 * criteriaQuery.where(builder.equal(item.get("team_id"), "CM0593"));
 * criteriaQuery.select(root);
 * Expression<String> groupById = join1.get("id");
		Expression<String> groupByComment = root.get("comment");
	    Expression<Long> count = builder.count(groupById);
	    //CriteriaQuery<Star> select =criteriaQuery.multiselect(groupById, groupByComment, count);
	    criteriaQuery.groupBy(groupById).groupBy(groupByComment);
	    criteriaQuery.orderBy(builder.desc(count));
 */