package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.service.contract.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender emailSender;
	@Override
	public String sendRegistrationConfirmation(String to) {
		try {
			var verificationCode = generateVerificationCode();

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			String emailContent = loadEmailTemplate("registration-email-template.html");
			emailContent = emailContent.replace("[user email]", to);
			emailContent = emailContent.replace("[Верифікаційний код]", verificationCode);

			helper.setTo(to);
			helper.setSubject("Підтвердження реєстрації");
			helper.setText(emailContent, true);

			emailSender.send(message);
			return verificationCode;
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String generateVerificationCode() {
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i <= 10; i++) {
			stringBuilder.append(random.nextInt(10 ));
		}
		return stringBuilder.toString();
	}

	private String loadEmailTemplate(String path) throws IOException {
		try (InputStream inputStream = getClass().getResourceAsStream(
		    "/templates/" + path)) {
			assert inputStream != null;
			try (InputStreamReader reader = new InputStreamReader(inputStream,
			    StandardCharsets.UTF_8)) {
				StringBuilder content = new StringBuilder();
				char[] buffer = new char[1024];
				int bytesRead;
				while ((bytesRead = reader.read(buffer)) != -1) {
					content.append(buffer, 0, bytesRead);
				}
				return content.toString();
			}
		}
	}
}