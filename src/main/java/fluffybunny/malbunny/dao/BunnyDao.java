package fluffybunny.malbunny.dao;

import java.util.ArrayList;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fluffybunny.malbunny.entity.Anime;
import fluffybunny.malbunny.entity.Entry;
import fluffybunny.malbunny.entity.Profile;
import fluffybunny.malbunny.entity.UserRole;
import fluffybunny.malbunny.entity.MalBunnyUser;
import fluffybunny.malbunny.entity.Previous;
import fluffybunny.malbunny.entity.PreviousIndex;

@Repository("BunnyDao")
// @Scope("singleton")
@Transactional
public class BunnyDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void setEntries(List<Entry> entries) {
		// sessionFactory.getCurrentSession().beginTransaction();
		for (Entry entry : entries) {
			System.out.println(entry);
			sessionFactory.getCurrentSession().saveOrUpdate(entry);
		}
		// sessionFactory.getCurrentSession().
	}

	public ArrayList<Entry> getEntries(int uid, boolean ptw) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from Entry where uid=" + uid);
		ArrayList<Entry> entries = (ArrayList<Entry>) query.getResultList();
		if(ptw) {
			return entries;
		} else {
			entries.removeIf(x -> x.getStatus().equals("Plan to Watch"));
			return entries;
		}
		
	}

	public void setProfile(Profile user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	public Profile getProfile(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from Profile where username='" + username + "'");
		ArrayList<Profile> entries = (ArrayList<Profile>) query.getResultList();
		if(entries.size() == 0) {
			return null;
		}
		return entries.get(0);
	}

	@SuppressWarnings("unchecked")
	public MalBunnyUser findByUserName(String username) {

		List<MalBunnyUser> users = new ArrayList<MalBunnyUser>();

		users = sessionFactory.getCurrentSession().createQuery("from MalBunnyUser where username='" + username + "'")
				.getResultList();
		System.out.println(users);
		if (users.size() > 0) {
			getUserRoles(users.get(0));
			return users.get(0);
		} else {
			return null;
		}

	}

	public void getUserRoles(MalBunnyUser user) {
		List<UserRole> roles = new ArrayList<UserRole>();
		roles = sessionFactory.getCurrentSession().createQuery("from UserRole where userId="+user.getId()).getResultList();
		user.setUserRole(new HashSet(roles));
	}

	public String register(MalBunnyUser entity) {
		System.out.println("dao");
		System.out.println(entity);
		try {
			sessionFactory.getCurrentSession().save(entity);
			UserRole role = new UserRole();
			role.setRole("USER");
			sessionFactory.getCurrentSession().save(role);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			return "failure";
		}
		return "success";
	}

	public MalBunnyUser loadUser(String username) {
		System.out.println(username);
		List<MalBunnyUser> list = (List<MalBunnyUser>) sessionFactory.getCurrentSession()
				.createQuery("from MalBunnyUser where username='" + username + "'").getResultList();
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public Profile getProfile(int malid) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from Profile where id=" + malid);
		ArrayList<Profile> entries = (ArrayList<Profile>) query.getResultList();
		return entries.get(0);
	}

	public void savePrevious(PreviousIndex prevdex) {
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(prevdex);
		for(Previous prev : prevdex.getPrevList()) {
			prev.setRid(prevdex.getRid());
			sess.saveOrUpdate(prev);
		}
		
	}

	public void updateAnime(Anime anime) {
		sessionFactory.getCurrentSession().saveOrUpdate(anime);
	}
	
	public Anime getAnimeByName(String name) {
		System.out.println(name);
		Query query = sessionFactory.getCurrentSession().createQuery(" from Anime where name=:name");
		List<Anime> entries = query.setParameter("name", name).getResultList();
		if(entries.size() == 0) {
			return null;
		}
		return entries.get(0);
	}

	public Anime getAnimeById(int id) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from Anime where id=" + id);
		List<Anime> entries = query.getResultList();
		if(entries.size() == 0) {
			Anime ani = new Anime();
			ani.setName("Not currently loaded.");
			ani.setId(id);
			return ani;
		}
		Anime ani = entries.get(0);
		System.out.println(ani.getGenres());
		System.out.println(ani.getProducers());
		System.out.println(ani.getStudio());
		System.out.println(ani.getScorePers());
		//sessionFactory.getCurrentSession().merge(entries.get(0));
		return ani;
	}

	public PreviousIndex getLastRanking(int uid) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from PreviousIndex where uid=" + uid + " order by datesaved");
		List<PreviousIndex> prevDex = query.getResultList();
		if(prevDex.size() == 0) {
			return null;
		}
		query = sessionFactory.getCurrentSession().createQuery(" from Previous where rid=" + prevDex.get(0).getRid());
		prevDex.get(0).setPrevList(query.getResultList());
		return prevDex.get(0);
	}

	public Entry getEntry(int uid, int id) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from Entry where uid=" + uid + " and id=" + id);
		ArrayList<Entry> entries = (ArrayList<Entry>) query.getResultList();
		if(entries.size() == 0){
			Entry ent = new Entry();
			ent.setUid(uid);
			ent.setId(id);
			return ent;
		} else {
			return entries.get(0);
		}
	}


	/*
	 * @Autowired
	 * 
	 * @Qualifier("malbunnySessionFactory") public void
	 * setmalbunnySessionFactory(SessionFactory sessionFactory){ this.sessionFactory
	 * = sessionFactory; }
	 */

	/*
	 * public String authUser(String username, String password) { String role = "";
	 * List<User> list = (List<User>) super.getHibernateTemplate().
	 * find("from User where username=? and password=? ", username, password);
	 * 
	 * if(list.isEmpty()){ role=""; }else{ role="user"; } return role; }
	 * 
	 * public String register(User entity) { System.out.println("dao");
	 * System.out.println(entity); try{ super.getHibernateTemplate().save(entity); }
	 * catch(Exception e) { System.out.println(e.getMessage());
	 * System.out.println(e.getStackTrace()); return "failure"; } return "success";
	 * }
	 * 
	 * public User loadUser(String username) { System.out.println(username);
	 * List<User> list = (List<User>)
	 * super.getHibernateTemplate().find("from User where username=?", username);
	 * if(list.isEmpty()){ return null; }else{ return list.get(0); } }
	 * 
	 * public int createBattle(BattleEntity entity) { System.out.println(entity);
	 * try{ super.getHibernateTemplate().save(entity); } catch(Exception e) {
	 * System.out.println(e.getMessage()); System.out.println(e.getStackTrace());
	 * return 0; } return entity.getId(); }
	 * 
	 * public BattleEntity getBattle(int bid) { List<BattleEntity> list =
	 * (List<BattleEntity>)
	 * super.getHibernateTemplate().find("from BattleEntity where id=?", bid);
	 * return list.get(0); }
	 * 
	 * public List<ScoreEntity> getScores(int bid) { return (List<ScoreEntity>)
	 * super.getHibernateTemplate().find("from ScoreEntity where bid=?", bid); }
	 * 
	 * public void saveRelation(ScoreEntity score) {
	 * super.getHibernateTemplate().save(score); }
	 * 
	 * public List<BattleEntity> getUnfinishedBattles() { return
	 * (List<BattleEntity>)
	 * super.getHibernateTemplate().find("from BattleEntity where ended=false"); }
	 * 
	 * public void updateBattle(int bid, String user) { List<BattleEntity> battles =
	 * (List<BattleEntity>)
	 * super.getHibernateTemplate().find("from BattleEntity where id=?",bid);
	 * BattleEntity battle = battles.get(0); battle.setWinner(user);
	 * battle.setEnded(true); super.getHibernateTemplate().merge(battle);
	 * 
	 * }
	 * 
	 * public void saveScore(int bid, String player, Integer integer) {
	 * System.out.println(bid + " " + player + " " + integer); List<ScoreEntity>
	 * scores = (List<ScoreEntity>) super.getHibernateTemplate().
	 * find("from ScoreEntity where bid=? and writerName=?", bid, player);
	 * ScoreEntity score = scores.get(0); score.setWords(integer);
	 * super.getHibernateTemplate().merge(score); }
	 */

}
