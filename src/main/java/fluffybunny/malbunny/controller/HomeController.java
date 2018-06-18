package fluffybunny.malbunny.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fluffybunny.malbunny.entity.Profile;
import fluffybunny.malbunny.entity.Entry;
import fluffybunny.malbunny.entity.Grouping;
import fluffybunny.malbunny.entity.MalBunnyUser;
import fluffybunny.malbunny.entity.OutputEntry;
import fluffybunny.malbunny.service.BunnyService;
import fluffybunny.malbunny.service.BunnyUserDetailsService;
import fluffybunny.malbunny.utils.DraftComporator;
import fluffybunny.malbunny.utils.RankComporator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {
	
	static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	ServletContext context; 
	@Autowired
	@Qualifier("BunnyService")
	BunnyService service;

	@Autowired
	@Qualifier("bunnyUserDetailsService")
	UserDetailsService authService;

	public void setServletContext(ServletContext servletContext) {
	    this.context = servletContext;
	}
	
	public void setService(BunnyService service) {
		this.service = service;
	}
	
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.authService = userDetailsService;
	}
	
	
	@RequestMapping(value="/")
	public ModelAndView home() {
		Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(u instanceof User) {
			User user = (User) u;
			service.setActiveUser(user.getUsername());
		}
		ModelAndView ret = new ModelAndView("index");
		return ret;
	}
	
	@RequestMapping(value="/loginfailure")
	public ModelAndView loginfailure() {
		BunnyUserDetailsService s = (BunnyUserDetailsService) authService;
		s.eraseLoadedUser();
		ModelAndView ret = new ModelAndView("redirect:" + "/login?error");
		return ret;
	}
	
	@RequestMapping(value="/loginsuccess")
	public ModelAndView loginsuccess() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		service.setActiveUser(user.getUsername());
		ModelAndView ret = new ModelAndView("redirect:" + "/");
		return ret;
	}

	@RequestMapping(value="user/prevReset", method=RequestMethod.GET)
	public ModelAndView prevReset() {
		String username = service.prevReset();
		ModelAndView ret = new ModelAndView("redirect:" + "/bunny/" + username);
		return ret;
	}
	
	@RequestMapping(value="xmlgen/{username}", method=RequestMethod.GET)
	public ModelAndView genxml(@PathVariable String username) {
		service.xmlCreate(username);
		System.out.println(username);
		ModelAndView ret = new ModelAndView("home");
		// Adds an objet to be used in home.jsp
		return ret;
	}
	
	@RequestMapping(value="/hello")
	public ModelAndView hello(@RequestParam(required=false, defaultValue="World") String name) {
		ModelAndView ret = new ModelAndView("home");
		// Adds an objet to be used in home.jsp
		ret.addObject("name", name);
		return ret;
	}
	
	@RequestMapping(value="/bunny/{username}", method=RequestMethod.GET)
	public ModelAndView profile(@PathVariable String username) {
		System.out.println(username);
		Profile user = service.loadDetails(username);
		ModelAndView ret = new ModelAndView("profile");
		ret.getModel().put("profileData", user);
		return ret;
	}
	
	@RequestMapping(value="/bunny/ranking/{username}", method=RequestMethod.GET)
	public ModelAndView ranking(@PathVariable String username) {
		System.out.println(username);
		Profile user = service.loadDetails(username);
		user.getEntries().removeIf(x -> x.getRank() == 0);
		user.getEntries().sort(new RankComporator());
		ModelAndView ret = new ModelAndView("profileRanking");
		ret.getModel().put("profileData", user);
		return ret;
	}
	
	@RequestMapping(value="/bunny/favorites/{username}", method=RequestMethod.GET)
	public ModelAndView favorites(@PathVariable String username) {
		System.out.println(username);
		Profile user = service.loadDetails(username);
		ModelAndView ret = new ModelAndView("profileFav");
		ret.getModel().put("profileData", user);
		return ret;
	}
	
	@RequestMapping(value="/bunny/popularity/{username}", method=RequestMethod.GET)
	public ModelAndView popularity(@PathVariable String username) {
		System.out.println(username);
		Profile user = service.loadDetails(username);
		ModelAndView ret = new ModelAndView("profilePop");
		ret.getModel().put("profileData", user);
		return ret;
	}
	
	@RequestMapping(value="/bunny/score/{username}", method=RequestMethod.GET)
	public ModelAndView score(@PathVariable String username) {
		System.out.println(username);
		Profile user = service.loadDetails(username);
		ModelAndView ret = new ModelAndView("profileScore");
		ret.getModel().put("profileData", user);
		return ret;
	}
	
	@RequestMapping(value="/bunny/cat/{username}/{grouping}", method=RequestMethod.GET)
	public ModelAndView source(@PathVariable String username, @PathVariable String grouping) {
		System.out.println(username);
		grouping = grouping.toLowerCase();
		Profile user = service.loadDetails(username);
		List<Grouping> groups = service.setCatergory(grouping, username);
		ModelAndView ret = new ModelAndView("profileGroups");
		ret.getModel().put("groupName", grouping);
		ret.getModel().put("profileData", user);
		ret.getModel().put("groupData", groups);
		return ret;
	}
	
	@RequestMapping(value="/load/{username}", method=RequestMethod.GET)
	public ModelAndView profileload(@PathVariable String username) {
		service.getUserDetails(username);
		ModelAndView ret = new ModelAndView("redirect:" + "/bunny/" + username);
		return ret;
	}
	
	@RequestMapping(value="user/reorder", method=RequestMethod.GET)
	public ModelAndView reorder() {
		Profile user = service.loadDetails();
		ModelAndView ret = new ModelAndView("rank");
		Collections.sort(user.getEntries());
		Collections.sort(user.getEntries(),new DraftComporator());
		user.getEntries().removeIf(i -> i.getScore() == 0);
		ret.getModel().put("profileData", user);
		// Adds an objet to be used in home.jsp
		return ret;
	}
	
	@RequestMapping(value="user/combine", method=RequestMethod.GET)
	public ModelAndView toCombine(String username) {
		ModelAndView ret = new ModelAndView("batchInput");
		return ret;
	}
	
	@RequestMapping(value="user/home", method=RequestMethod.GET)
	public ModelAndView userhome() {
		Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(u instanceof User) {
			User user = (User) u;
			service.setActiveUser(user.getUsername());
		}
		ModelAndView ret = new ModelAndView("userhome");
		return ret;
	}
	
	@RequestMapping(value="/addViaXml", method=RequestMethod.POST)
	public ModelAndView parseUserXml(@RequestParam("data") String data) {
		String username = service.parseUserXml(data);
		ModelAndView ret = new ModelAndView("redirect:" + "/bunny/" + username);
		return ret;
	}
	
	@RequestMapping(value="/insertyourshittastehere", method=RequestMethod.GET)
	public ModelAndView parseUserXml() {
		ModelAndView ret = new ModelAndView("xmlInput");
		return ret;
	}
	
	@RequestMapping(value="user/combining", method=RequestMethod.POST)
	public ModelAndView combine(@RequestParam("data") String data) {
		service.combine(data);
		ModelAndView ret = new ModelAndView("index");
		return ret;
	}
	
	@RequestMapping(value="user/draft", method=RequestMethod.POST)
	public ModelAndView draft(@RequestParam("data") String data) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<?> dataList = objectMapper.readValue(data, List.class);
        service.saveDraft(dataList.stream().map(i -> Integer.parseInt(i.toString())).collect(Collectors.toList()));
        ModelAndView ret = new ModelAndView("redirect:/user/reorder");
		return ret;
	}
	
	@RequestMapping(value="user/finalize", method=RequestMethod.POST)
	public ModelAndView finalize(@RequestParam("data") String data) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<?> dataList = objectMapper.readValue(data, List.class);
        service.saveRanking(dataList.stream().map(i -> Integer.parseInt(i.toString())).collect(Collectors.toList()));
        ModelAndView ret = new ModelAndView("redirect:/bunny/");
		return ret;
	}
	
	@RequestMapping(value="json/draft", method=RequestMethod.POST)
	public void jsonDraft(@RequestBody String content) {
		System.out.println(content);
	}
	
	
	
	@RequestMapping(value="json/entries/{id}", method=RequestMethod.GET, produces= {"application/json"})
	@ResponseBody
	public List<Entry> sendEntries(@PathVariable int id){
		List<Entry> entries = service.getEntries(id);
		System.out.println("Called");
		return entries;
	}
	
	@RequestMapping(value="json/entryIds/{uid}", method=RequestMethod.GET, produces= {"application/json"})
	@ResponseBody
	public List<Integer> sendEntryIds(@PathVariable int id){
		List<Integer> entIds = new ArrayList<>();
		List<Entry> entries = service.getEntries(id);
		for(Entry ent : entries) {
			entIds.add(ent.getId());
		}
		System.out.println("Called");
		return entIds;
	}
	
	@RequestMapping(value="json/entriesOutput/{id}", method=RequestMethod.GET, produces= {"application/json"})
	@ResponseBody
	public List<OutputEntry> observe(@PathVariable int id){
		List<Entry> entries = service.getEntries(id);
		List<OutputEntry> outents = new ArrayList<>();
		for(Entry ent : entries) {
			OutputEntry oent = new OutputEntry(ent);
			outents.add(oent);
		}
		System.out.println("Called");
		return outents;
	}
	
	
	@RequestMapping(value="json/groupingData/{username}/{grouping}", method=RequestMethod.GET, produces= {"application/json"})
	@ResponseBody
	public List<Grouping> observe(@PathVariable String username, @PathVariable String grouping){
		List<Grouping> groups = service.setCatergory(grouping, username);
		System.out.println("Called");
		return groups;
	}
	
	
/*	@RequestMapping(value="user/combining", method = RequestMethod.POST)
	public ModelAndView takeTheString(HttpServletRequest request, HttpServletResponse response) {               
	          String temp_input =request.getParameter("data"); 
	           System.out.println(temp_input );
	           ModelAndView ret = new ModelAndView("index");
	   		return ret;  
	    }
	    
	    */
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

	  ModelAndView model = new ModelAndView();
	  if (error != null) {
		model.addObject("error", "Invalid username and password!");
	  }

	  if (logout != null) {
		model.addObject("msg", "You've been logged out successfully.");
	  }
	  model.setViewName("loginJSP");

	  return model;

	}
	

	/*
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String authUserPage(Model model){
		return "login";
	}*/
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registerPage(Model model){
		return "register";
	}
	
	@RequestMapping(value="/registeruser", method=RequestMethod.POST)
	public String register(@RequestParam String username, @RequestParam CharSequence password, @RequestParam String malId){
		MalBunnyUser user = new MalBunnyUser();
		user.setUsername(username);
		user.setPassword((new BCryptPasswordEncoder()).encode(password));
		user.setMalid(Integer.parseInt(malId));
		user.setEnabled(true);
		service.registerUser(user);
		return "redirect:/login";
		//return "redirect:/profile/register";
	}

	@RequestMapping(value="invalidLogin", method=RequestMethod.GET)
	public String invalidLogin(Model model){
		model.addAttribute("message","Incorrect Login Info");
		return "login";
	}
	
	@RequestMapping(value="denied", method=RequestMethod.GET)
	public String accessDenied(Model model){
		model.addAttribute("message", "You are not allowed.");
		return "login";
	}
/*	@RequestMapping(value="auth-user", method=RequestMethod.POST)
	public String authUser(String username, String password, HttpSession session, Model model){
		String role = authService.authUser(username, password);	
		//String nextPage="homePage";
		if(role.length() > 0){
			System.out.println("$$$$$$$%%%%%%-_______This is my auth-user method");
			session.setAttribute("username", username);
			session.setAttribute("role", role);
		}else{
			return "login";
		}
		return "redirect:/home";
	}*/
	
	@RequestMapping(value="redirectHome", method=RequestMethod.GET)
	public String redirectHome(){
		String nextPage="index";
		return nextPage;
	}
}
