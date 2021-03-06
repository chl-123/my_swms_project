package com.fs.swms.common.util;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.entity.MyFile;
import com.google.common.io.Files;
import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    @Value("${file.baseStoreDir:''}")
    private static String baseStoreDir;
    private static Path fileBasePath;
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
    public static String uploadFile(String dataPath,MyFile myFile)  {
        MultipartFile file=myFile.getFile();
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String s = UUID.randomUUID().toString();
        String newName = s + suffix;
        String parentPath = getParentPath();
        File dest = new File(parentPath+"/"+dataPath, newName);
        try {
            //?????????????????????????????????google???guava?????????
            Files.createParentDirs(new File(""));
            Files.createParentDirs(dest);
            file.transferTo(dest);
            return newName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("??????????????????");
        }
    }
    public static boolean delFile(String fileName){
        String parentPath = getParentPath();
        File dest = new File(parentPath, fileName);
        Boolean isDelete = Boolean.FALSE;
        if (dest.exists()) {

            isDelete = FileSystemUtils.deleteRecursively(dest);
        }

        return isDelete;
    }
    public static void downFile(String openStyle, String name, HttpServletRequest request, HttpServletResponse response) throws IOException {

        //????????????(inline,attachment)
        openStyle=openStyle==null?"attachment":openStyle;
        String parentPath = getParentPath();
        FileInputStream fis = new FileInputStream(new File(parentPath, name));
        ServletOutputStream os=response.getOutputStream();
        response.setHeader("Content-disposition",openStyle+";filename"+ URLEncoder.encode(name,"UTF-8"));
        IOUtils.copy(fis,os);
        IOUtils.closeQuietly(fis);
        IOUtils.closeQuietly(os);

    }
    public static void downloadFile(String fileName,HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            ClassPathResource classPathResource = new ClassPathResource("upload/"+fileName);
            File file = classPathResource.getFile();
            InputStream inputStream = classPathResource.getInputStream();
            //????????????
            InputStream fis = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();

            //?????????????????????????????????????????????
            String name = file.getName();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes(), "iso-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream out = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            out.write(buffer);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("???????????????");
        }catch (Exception e) {
            throw e;
        }

    }





    private static String getParentPath() {
        String parentPath = "";
        //????????????classpath??????????????????????????????
        if (StringUtils.isBlank(baseStoreDir)) {
            String rootUri = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            parentPath = rootUri + File.separator + "upload";
        } else {
            parentPath = baseStoreDir;
        }
        return parentPath;
    }
    public static boolean isValidDate(String sDate) {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(sDate);
                return match.matches();
            }
            else {
                return false;
            }
        }
        return false;
    }
    public static Date parse(String dateInfo) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sf.parse(dateInfo);
        return parse;
    }
    public static String uploadFiles(List<MultipartFile> files, String dataPath){
        String fileNames="";
        for(MultipartFile file:files){
            if (!file.getOriginalFilename().equals("")&&file!=null&&file.getOriginalFilename()!=null) {
                MyFile myFile=new MyFile();
                myFile.setFile(file);
                String fileName = Utils.uploadFile(dataPath,myFile);
                fileNames=dataPath+fileName+";"+fileNames;
            }
        }
        return fileNames;
    }


}
