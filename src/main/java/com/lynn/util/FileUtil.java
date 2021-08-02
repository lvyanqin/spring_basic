package com.lynn.util;


public class FileUtil {


    private String getFileSuffix(MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        int i = -1;
        if(StringUtils.isNotBlank(originalFilename) && (i = originalFilename.lastIndexOf(".")) != -1) {
            return originalFilename.substring(i);
        }
        return ".jpg";
    }

}
