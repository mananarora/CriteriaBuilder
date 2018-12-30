package projectRelatedHibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="associate")

/**
 * Hibernate entity class for table Associate
 * @author MA065202
 * @version 1.0, 10 October 2018 
 */
public class Associate {
	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="name")
	private String name;

	@ManyToOne
	@JoinTable(name="team_associate",
				joinColumns=@JoinColumn(name="assoc_id"),
				inverseJoinColumns=@JoinColumn(name="team_id")
	)
	private Team team;
	
	/**
	 * @return the team that belongs to the associate
	 */
	public Team getTeam() {
		return team;
	}
	/**
	 * @param team to set team for which the associate belongs to.
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
	/**
	 * @return the Id of an associate
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param Id to set Id of an associate.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name of the associate
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name to set the name of a
	 */
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Associate [id=" + id + ", name=" + name + ", team=" + team + "]";
	}
	
}