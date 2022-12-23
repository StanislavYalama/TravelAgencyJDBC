package com.coursework.oneWay.services;

import com.coursework.oneWay.models.ClientDocumentView;
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

    public void sendMailToTourOperator(String emailTo, String clientName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setTo(emailTo);
        helper.setFrom(emailFrom);
        helper.setSubject("Замовлення тура");
        MimeMultipart multipart = new MimeMultipart("related");
        MimeBodyPart textBodyPart = new MimeBodyPart();

        String textHtml = "<html lang=\"uk\"><head><title>Client Documents</title></head><body><h1>Замовлення тура для клієнта "
                .concat(clientName)
                .concat("</h1><h3>Нижче наведені необхідні документи:<h3><br>");
//        for(int i = 0; i < listClientDocumentView.size(); i++) {
//
//            textHtml = textHtml.concat("<div>")
//                    .concat(listClientDocumentView.get(i).getName()).concat("<br>")
//                    .concat("<img src=\"cid:").concat(Integer.toString(i + 1))
//                    .concat("\"><br>") //width="500" height="500"
//                    .concat("</div>");
//        }
        textHtml = textHtml.concat("</body></html>");
        textBodyPart.setText(textHtml, "UTF-8", "html");    //"US-ASCII",
        multipart.addBodyPart(textBodyPart);

//        for(int i = 0; i < listClientDocumentView.size(); i++) {
//
//            MimeBodyPart imagePart = new MimeBodyPart();
////            imagePart.setHeader("Content-ID", Integer.toString(i + 1));
//            try {
//                imagePart.attachFile(filePath.concat("/")
//                        .concat(listClientDocumentView.get(i).getPhotoPath()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            imagePart.setContentID("<".concat(Integer.toString(i+1)).concat(">"));
//            imagePart.setDisposition(MimeBodyPart.INLINE);
//            multipart.addBodyPart(imagePart);
//        }

        mimeMessage.setContent(multipart);
        mailSender.send(mimeMessage);
    }
}