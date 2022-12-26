package pl.kurs.geometricfigures.service;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final ShapeManagementService service;
    private final Environment env;

    @Scheduled(cron = "05 0 * * * *", zone = "Europe/Warsaw")
    @Async
    public String sendReportMailOnSchedule() throws MessagingException {

        Context context = service.createReportContext();
        String htmlContent = templateEngine.process("email", context);

        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setSubject("Raport o stworzonych figurach z dnia " + LocalDate.now().minusDays(1));
        helper.setText(htmlContent, true);
        helper.setTo(env.getProperty("adminemail"));
        javaMailSender.send(mimeMessage);

        return "Sent";
    }
}