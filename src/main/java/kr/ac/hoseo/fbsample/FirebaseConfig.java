package kr.ac.hoseo.fbsample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

	@PostConstruct
	public void init() throws FileNotFoundException, IOException {
		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials
							.fromStream(new FileInputStream(new ClassPathResource("firebase-auth.json").getFile())))
					.setDatabaseUrl("https://pi-iot-7f7c3.firebaseio.com").build();

			FirebaseApp.initializeApp(options);
		}

	}
}
