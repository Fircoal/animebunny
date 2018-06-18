package fluffybunny.malbunny.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class BunnyControllerAdvice {
	
	@ModelAttribute
	public void addAdvice(Model model) {
		model.addAttribute("username",getUsername());
	}
	
	public String getUsername() {
		Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(u instanceof User) {
			User user = (User) u;
			return user.getUsername();
		} else {
			return "Guest";
		}
	}	

}

