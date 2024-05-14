package com.zoi4erom.strategygame;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zoi4erom.strategygame.exception.EmailSendingException;
import com.zoi4erom.strategygame.exception.EmailTemplateLoadException;
import com.zoi4erom.strategygame.service.contract.VerificationCodeGenerator;
import com.zoi4erom.strategygame.service.impl.EmailServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailServiceImplTest {
	@Mock
	private JavaMailSender emailSenderMock;

	@Mock
	private VerificationCodeGenerator verificationCodeGeneratorMock;

	private EmailServiceImpl emailService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		emailService = new EmailServiceImpl(emailSenderMock, verificationCodeGeneratorMock);
	}

	@Test
	void sendRegistrationConfirmation_Successful() throws Exception {
		String email = "test@example.com";
		String verificationCode = "123456";
		when(verificationCodeGeneratorMock.generateVerificationCode()).thenReturn(verificationCode);

		MimeMessage mimeMessageMock = mock(MimeMessage.class);
		when(emailSenderMock.createMimeMessage()).thenReturn(mimeMessageMock);

		MimeMessageHelper mimeMessageHelperMock = mock(MimeMessageHelper.class);
		doNothing().when(mimeMessageHelperMock).setTo(anyString());
		doNothing().when(mimeMessageHelperMock).setSubject(anyString());
		doNothing().when(mimeMessageHelperMock).setText(anyString(), eq(true));

		emailService.sendRegistrationConfirmation(email);

		verify(verificationCodeGeneratorMock).generateVerificationCode();
		verify(emailSenderMock).send(any(MimeMessage.class));
	}
}
