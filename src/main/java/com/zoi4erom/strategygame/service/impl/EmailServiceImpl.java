package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.exception.EmailSendingException;
import com.zoi4erom.strategygame.exception.EmailTemplateLoadException;
import com.zoi4erom.strategygame.service.contract.EmailService;
import com.zoi4erom.strategygame.service.contract.VerificationCodeGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender emailSender;
	private final VerificationCodeGenerator verificationCodeGenerator;

	@Override
	public String sendRegistrationConfirmation(String to) {
		try {
			var verificationCode = verificationCodeGenerator.generateVerificationCode();

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			String emailContent = loadEmailTemplate();
			emailContent = emailContent.replace("[user email]", to);
			emailContent = emailContent.replace("[Верифікаційний код]", verificationCode);

			helper.setTo(to);
			helper.setSubject("Підтвердження реєстрації");
			helper.setText(emailContent, true);

			emailSender.send(message);
			return verificationCode;
		} catch (MessagingException e) {
			throw new EmailSendingException(
			    "Помилка при надсиланні листа підтвердження реєстрації на адресу: " + to);
		}
	}

	private String loadEmailTemplate() {
		try (InputStream inputStream = getClass().getResourceAsStream(
		    "/templates/registration-email-template.html")) {
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
		} catch (IOException e) {
			throw new EmailTemplateLoadException(
			    "Failed to load email template: registration-email-template.html");
		}
	}
}