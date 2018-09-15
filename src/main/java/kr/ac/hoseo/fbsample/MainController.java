package kr.ac.hoseo.fbsample;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.SessionCookieOptions;

@Controller
public class MainController {
	Logger log = LoggerFactory.getLogger(MainController.class);
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@PostMapping("/")
	public ModelAndView home(@RequestParam String token,  HttpServletResponse rep) throws FirebaseAuthException {
		FirebaseAuth auth = FirebaseAuth.getInstance(FirebaseApp.getInstance());
		FirebaseToken decodedToken = auth.verifyIdToken(token);
		String cookie = auth.createSessionCookie(token, SessionCookieOptions.builder().setExpiresIn(1000 * 60 * 60 * 2).build());
		rep.addCookie(new Cookie("jwtToken", cookie));
		
		return new ModelAndView("index",ImmutableMap.of("mail",decodedToken.getEmail()));
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
