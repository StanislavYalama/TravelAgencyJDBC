package com.coursework.oneWay.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class MultipartFileUtils {

    @Value("${path.length}")
    private int pathLength;

    public String uploadFile(MultipartFile multipartFile, String fullFilePath, String newName) throws IOException {
        String newFileName = newName + multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        if(newName.equals("")){
            newFileName = multipartFile.getOriginalFilename();

            if(newFileName != null && newFileName.length() > pathLength){
                newFileName = newFileName.substring(newFileName.length() - pathLength);
            }
        }

        File newFile = new File(fullFilePath + newFileName);
        if(!newFile.exists()){
            newFile.mkdirs();
        }
        multipartFile.transferTo(newFile);
        return newFileName;
    }
}
