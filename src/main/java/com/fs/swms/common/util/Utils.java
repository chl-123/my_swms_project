package com.fs.swms.common.util;

import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class Utils {
    public static MultipartFile getMulFileByPath(String picPath) throws IOException {
        File file = new File(picPath);
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
        return multipartFile;
    }
    public static String getKeyByValue(Map map, Integer value) {

        String keys="";

        Iterator it = map.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry entry = (Map.Entry) it.next();

            Object obj = entry.getValue();

            if (obj != null && obj.equals(value)) {

                keys=(String) entry.getKey();

            }
        }

        return keys;

    }

}
