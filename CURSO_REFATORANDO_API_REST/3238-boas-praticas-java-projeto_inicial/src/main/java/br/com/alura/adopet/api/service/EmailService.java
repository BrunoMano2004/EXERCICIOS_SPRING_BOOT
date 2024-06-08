package br.com.alura.adopet.api.service;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void enviar(String to, String subject, String message) throws MessagingException {
        var mail = emailSender.createMimeMessage();
        var mensagem = new MimeMessageHelper(mail);
        mensagem.setTo(to);
        mensagem.setSubject(subject);
        mensagem.setText(message);
        emailSender.send(mail);
    }
}
