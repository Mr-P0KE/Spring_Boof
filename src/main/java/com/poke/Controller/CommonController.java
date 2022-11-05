package com.poke.Controller;


import com.poke.Util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;


//上传下载文件
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${Demo.UpLoad}")
    private String path;

    @PostMapping("/upload")
    public R<String> UploadFile(MultipartFile file){
        log.info("上传文件名为={}",file.toString());

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString()+suffix;
        log.info("上传文件地址={}",filename);

        File f = new File(path);
        if(!f.exists())
            f.mkdirs();
        try {
            file.transferTo(new File(path+filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(filename);
    }

    @GetMapping("download")
    public void downLoad(String name, HttpServletResponse response){
        log.info("下载文件名={}",name);

        try (FileInputStream fis = new FileInputStream(new File(path+name));
             ServletOutputStream sos = response.getOutputStream()){

            response.setContentType("image/jpg");
            int length = 0;
            byte[] b = new byte[1024];
//            while (true){
//                int length = fis.read(bytes);
//                if(-1  == length)
//                    break;
//                fos.write(bytes,0,length);
//                fos.flush();
//            }
           while (true){
               length = fis.read(b);
               if(length == -1)
                   break;
               sos.write(b,0,length);
               sos.flush();
           }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
