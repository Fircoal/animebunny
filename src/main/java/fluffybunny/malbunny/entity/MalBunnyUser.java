package fluffybunny.malbunny.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="MalBunnyUser")
public class MalBunnyUser implements Serializable{
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
		int id;
		String username;
		String password;
		int malid;
		boolean enabled;

		@Transient
		private Set<UserRole> userRole = new HashSet<UserRole>(0);
		

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		

		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		

		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		

		public boolean isEnabled() {
			return enabled;
		}
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}


		public Set<UserRole> getUserRole() {
			return userRole;
		}
		public void setUserRole(Set<UserRole> userRole) {
			this.userRole = userRole;
		}

		
		public int getMalid() {
			return malid;
		}
		public void setMalid(int malid) {
			this.malid = malid;
		}
		

		@Override
		public String toString() {
			return "MalBunnyUser [id=" + id + ", username=" + username + ", password=" + password + ", malid=" + malid
					+ ", enabled=" + enabled + ", userRole=" + userRole + "]";
		}

	}