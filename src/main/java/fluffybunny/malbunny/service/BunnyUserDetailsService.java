package fluffybunny.malbunny.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fluffybunny.malbunny.dao.BunnyDao;
import fluffybunny.malbunny.entity.MalBunnyUser;
import fluffybunny.malbunny.entity.UserRole;

@Service("bunnyUserDetailsService")
public class BunnyUserDetailsService implements UserDetailsService {

	//get user from the database, via Hibernate
	@Autowired
	private BunnyDao userDao;
	
	private MalBunnyUser loadedUser;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) 
		throws UsernameNotFoundException {
	
		MalBunnyUser user = userDao.findByUserName(username);
		loadedUser = user;
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

		return buildUserForAuthentication(user, authorities);
		
	}

	// Converts fluffybunny.malbunny.entityUser user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(MalBunnyUser user, 
		List<GrantedAuthority> authorities) {
		System.out.println("Build User Auth");
		return new User(user.getUsername(), user.getPassword(), 
			user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		System.out.println("Build User Authority");
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			System.out.println(userRole.getRole());
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		System.out.println(Result);
		return Result;
	}
	
	public void eraseLoadedUser() {
		loadedUser = null;
	}

	@Override
	public String toString() {
		return "BunnyUserDetailsService";
	}

	public MalBunnyUser getLoadedUser() {
		MalBunnyUser user = loadedUser;
		loadedUser = null;
		return user;
	}
	
}