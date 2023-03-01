package com.coursework.oneWay.services;

import com.coursework.oneWay.models.*;
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
import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private com.coursework.oneWay.services.RequestPassportService requestPassportService;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${upload.path}")
    private String filePath;

    @Value("${travelDocuments.path}")
    private String travelDocumentPath;

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

        for (int i = 0; i < passportList.size(); i++) {
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

    public void sendMailToClientWithTravelDocuments(String emailTo, int requestId, Connection connection) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setTo(emailTo);
        helper.setFrom(emailFrom);
        helper.setSubject("Документи для участі в турі");
        MimeMultipart multipart = new MimeMultipart("related");
        MimeBodyPart textBodyPart = new MimeBodyPart();

        String textHtml = "<html lang=\"uk\"><head><title>[Request#" + requestId + "] Документи для участі в турі</title></head><body><h1 style=\"text-align: center\">Ваші документи для участі в турі прикріплені до листа</h1></body></html>";

        textBodyPart.setText(textHtml, "UTF-8", "html");
        multipart.addBodyPart(textBodyPart);

        try {
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();

            attachmentBodyPart.attachFile(zipDir(requestId));
            multipart.addBodyPart(attachmentBodyPart);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mimeMessage.setContent(multipart);
        mailSender.send(mimeMessage);

    }

    private File zipDir(int requestId) {
        String sourceFile = travelDocumentPath + "request" + requestId + "/";
        String sourceZipFile = travelDocumentPath + "zip/request" + requestId + ".zip";

//        File newFIle = new File(sourceZipFile);

        try {
//            if(!newFIle.exists()){
//                newFIle.mkdirs();
//                newFIle.createNewFile();
//            }

            FileOutputStream fos = new FileOutputStream(sourceZipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);


            File fileToZip = new File(sourceFile);
            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(sourceZipFile);
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {

        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}