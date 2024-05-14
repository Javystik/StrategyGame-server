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
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Implementation of the service for sending emails, including registration confirmation emails.
 * This service utilizes an email sender and a verification code generator.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender emailSender; // Email sender component
	private final VerificationCodeGenerator verificationCodeGenerator; // Verification code generator component

	/**
	 * Sends a registration confirmation email to the specified email address.
	 *
	 * @param to The email address to send the registration confirmation email to
	 * @return The generated verification code sent in the email
	 * @throws EmailSendingException If an error occurs while sending the email
	 */
	@Override
	public String sendRegistrationConfirmation(String to) {
		log.info("Sending registration confirmation email to: '{}'", to);
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
			log.info("Registration confirmation email sent successfully to: '{}'", to);
			return verificationCode;
		} catch (MessagingException e) {
			log.error("Error sending registration confirmation email to: '{}'", to, e);
			throw new EmailSendingException(
			    "Помилка при надсиланні листа підтвердження реєстрації на адресу: " + to);
		}
	}

	/**
	 * Loads the email template for registration confirmation.
	 *
	 * @return The content of the email template as a string
	 * @throws EmailTemplateLoadException If an error occurs while loading the email template
	 */
	protected String loadEmailTemplate() {
		log.info("Loading email template for registration confirmation");
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
			log.error("Failed to load email template: registration-email-template.html", e);
			throw new EmailTemplateLoadException(
			    "Failed to load email template: registration-email-template.html");
		}
	}
}