package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Document;
import com.coursework.oneWay.models.RequestPassport;
import com.coursework.oneWay.models.RequestPassportDocument;
import com.coursework.oneWay.repositories.DocumentRepositoryImpl;
import com.coursework.oneWay.repositories.RequestPassportDocumentRepositoryImpl;
import com.coursework.oneWay.repositories.RequestPassportRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class RequestPassportService {

    @Autowired
    private RequestPassportRepositoryImpl requestPassportRepository;
    @Autowired
    private RequestPassportDocumentRepositoryImpl requestPassportDocumentRepository;
    @Autowired
    private DocumentRepositoryImpl documentRepository;
    @Autowired
    private MultipartFileUtils multipartFileUtils;

    @Value("${travelDocuments.path}")
    String uploadPackage;

    public List<RequestPassportDocument> findByRequestId(int requestId, Connection connection){
        return requestPassportDocumentRepository.findByRequestId(requestId, connection);
    }

    public int getIdByPassportAndRequest(int passportId, int requestId, Connection connection){
        return requestPassportRepository.getIdByPassportAndRequest(passportId, requestId, connection);
    }

    public void saveClientDocuments(int requestId,
                                    int passportId,
                                    MultipartFile[] tickets,
                                    MultipartFile[] vouchers,
                                    MultipartFile insurance,
                                    Connection connection){

        String filePath = "request" + requestId + "/passport" + passportId + "/";

        List<Document> documentList = documentRepository.findAll(Document.class, connection);

        int requestPassportId =
                requestPassportRepository.getIdByPassportAndRequest(passportId, requestId, connection);

        saveDocuments(tickets, filePath, "ticket", requestPassportId,
                documentList.stream().filter(x -> x.getNameEng().equals("ticket")).toList().get(0).getId(), connection);
        saveDocuments(vouchers, filePath, "voucher", requestPassportId,
                documentList.stream().filter(x -> x.getNameEng().equals("voucher")).toList().get(0).getId(), connection);
        saveDocument(insurance, filePath, requestPassportId,
                documentList.stream().filter(x -> x.getNameEng().equals("insurance")).toList().get(0).getId(), connection);


    }

    public void save(RequestPassport requestPassport, Connection connection){
        requestPassportRepository.save(requestPassport, connection);
    }
    public void delete(Integer id, Connection connection){
        requestPassportRepository.deleteById(RequestPassport.class, id, connection);
    }
    private void saveRequestPassportDocument(RequestPassportDocument requestPassportDocument, Connection connection){
        requestPassportDocumentRepository.save(requestPassportDocument, connection);
    }
    public void deleteRequestPassportDocument(Integer id, Connection connection){
        requestPassportDocumentRepository.deleteById(RequestPassportDocument.class, id, connection);
    }

    private void saveDocuments(MultipartFile[] files, String filePath, String type,
                               int requestPassportId, int documentId, Connection connection){
        String inPackagePath = filePath + type + "/";
        String fullFilePath = uploadPackage + inPackagePath;

        Arrays.stream(files).forEach(el -> {
            try {
                String newFileName = multipartFileUtils.uploadFile(el, fullFilePath, "");

                saveRequestPassportDocument(new RequestPassportDocument(0, requestPassportId,
                        documentId,inPackagePath + newFileName), connection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    private void saveDocument(MultipartFile file, String filePath, int requestPassportId,
                              int documentId, Connection connection){
        String inPackagePath = filePath + "insurance/";
        String fullFilePath = uploadPackage + inPackagePath;

        try {
            String newFileName = multipartFileUtils.uploadFile(file, fullFilePath, "");

            saveRequestPassportDocument(new RequestPassportDocument(0, requestPassportId, documentId,
                    inPackagePath + newFileName), connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    private void upload(byte[] resource, String keyName) throws IOException {
//        Path path = Paths.get(uploadPackage, keyName);
//        Path file = Files.createFile(path);
//        try (FileOutputStream stream = new FileOutputStream(file.toString())) {
//            stream.write(resource);
//        }
//    }
//
//    private String generateKey(String name) {
//        return DigestUtils.md5DigestAsHex((name + LocalDateTime.now()).getBytes());
//    }
}
