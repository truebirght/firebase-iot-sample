package kr.ac.hoseo.fbsample;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	FirebaseAuth auth = null;
	Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(auth == null) {
			auth = FirebaseAuth.getInstance(FirebaseApp.getInstance());
		}
		
		if(request.getRequestURI().equals("/login")) {
			return true;
		}
		
		if(request.getCookies() != null) {
			for(Cookie c : request.getCookies()) {
				if(c.getName().equals("jwtToken")) {
					try {
						FirebaseToken token = auth.verifySessionCookie(c.getValue());
						request.setAttribute("auth", token);
						return true;
					}catch(FirebaseAuthException authException) {
						log.error("토큰 검증 실패!",authException);
						Cookie rmCookie = new Cookie("jwtToken", "-1");
						rmCookie.setMaxAge(0);
						response.addCookie(rmCookie);
					}
				}
			}
			response.sendRedirect("/login");
			return true;
		}
		return false;
	}

}
