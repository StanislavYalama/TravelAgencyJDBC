package com.coursework.oneWay.services;

import com.coursework.oneWay.models.ClientDocumentView;
import com.coursework.oneWay.models.Passport;
import com.coursework.oneWay.models.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${upload.path}")
    private String filePath;

    public void sendMailToTourOperator(String emailTo, List<Passport> passportList, Tour tour) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setTo(emailTo);
        helper.setFrom(emailFrom);
        helper.setSubject("Замовлення тура");
        MimeMultipart multipart = new MimeMultipart("related");
        MimeBodyPart textBodyPart = new MimeBodyPart();

        String textHtml = "<html lang=\"uk\"><head><title>Client Documents</title></head><body><h1>Замовлення тура</h1><br><br>"
                .concat("<h3>Інформація по туру</h3><br>")
                .concat("Дата початку: ").concat(tour.getDateStart().toString()).concat("<br>")
                .concat("Дата кінця: ").concat(tour.getDateEnd().toString()).concat("<br>")
                .concat("Опис: ").concat(tour.getDescription()).concat("<br><br>")
                .concat("<h3>Нижче наведені необхідні документи:<h3><br>");

        for(int i = 0; i < passportList.size(); i++) {
            textHtml = textHtml.concat("<div>")
                    .concat("Учасник ").concat(Integer.toString(i + 1)).concat("<br>")
                    .concat("ФІО: ").concat(passportList.get(i).getName()).concat("<br>")
                    .concat("Номер документа: ").concat(passportList.get(i).getDocumentNumber()).concat("<br>")
                    .concat("Дійсний до: ").concat(passportList.get(i).getDateOfExpiry().toString()).concat("<br>")
                    .concat("Дата випуску: ").concat(passportList.get(i).getDateOfIssue().toString()).concat("<br>")
                    .concat("</div>").concat("<br>");
        }
        textHtml = textHtml.concat("</body></html>");
        textBodyPart.setText(textHtml, "UTF-8", "html");    //"US-ASCII",
        multipart.addBodyPart(textBodyPart);

        mimeMessage.setContent(multipart);
        mailSender.send(mimeMessage);
    }
}