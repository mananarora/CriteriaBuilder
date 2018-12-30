package projectRelatedHibernate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Hibernate entity class for table Team
 * @author MA065202
 * @version 1.0, 10 October 2018
 */
@Entity
@Table(name="team")
public class Team {
	
	@Id
	@Column(name="id")
	private String team_id;
	
	@Column(name="name")
	private String team_name;
	
	/**
	 * @return teamId
	 */
	public String getTeam_id() {
		return team_id;
	}
	/**
	 * @param teamId to set id of the team
	 */
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	/**
	 * @return teamName
	 */
	public String getTeam_name() {
		return team_name;
	}
	/**
	 * @param teamName to set name of the team.
	 */
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	@Override
	public String toString() {
		return "Team [team_id=" + team_id + ", team_name=" + team_name + "]";
	}
}
