/**
 * @Date        : 2020-10-27 21:27:21
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Spring boot 文件上传实现
 *
 * Spring boot 默认单个文件上传的大小为 1MB，一次总的上传文件大小为 10MB。
 * 单个文件上传使用 MultipartFile 参数来接受，多个文件上传使用 MultipartFile[] 数组来接受，
 * 然后遍历数组当成单个文件来处理。
 *
 * 文件上传有2个问题需要解决：
 * 1.如何突破文件上传大小的限制？
 * 2.文件上传到物理服务器上后，如何通过 http 提供访问？
 *
 * 答案是使用自定义配置。
 *
 */
package me.shy.demo.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
public class FileUploadApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(FileUploadApplication.class);

    @Value("${file.upload.path}")
    private String fileUploadPath;

    public static void main(String[] args) {
        SpringApplication.run(FileUploadApplication.class, args);
    }

    // 文件上传 controller
    @PostMapping("/fileupload")
    public List<String> fileUpload(@RequestParam MultipartFile[] files) {
        // 用于存放已上传完成的文件名
        List<String> dones = new ArrayList<String>();
        // 如果没有选择文件
        if (0 == files.length) {
            dones.add("Please select at least one file to uploading!");
            return dones;
        }
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID() + suffix;
            File targetFile = new File(this.fileUploadPath + filename);
            if (!targetFile.getParentFile().isDirectory()) {
                targetFile.getParentFile().mkdirs();
            }

            try {
                file.transferTo(targetFile);
                dones.add(originalFilename);
                LOGGER.info("File {} uploaded success!");
            } catch (IllegalStateException | IOException e) {
                dones.add(String.format("An error occourded during upload file %s", originalFilename));
                LOGGER.error("An error occourded during upload file {}", originalFilename);
                e.printStackTrace();
            }
        }
        return dones;
    }

    @Configuration
    public class FileUploadConfig implements WebMvcConfigurer {
        // 突破单个文件上传大小和总的文件上传大小
        @Bean
        public MultipartConfigElement multipartConfigElement() {
            MultipartConfigFactory factory = new MultipartConfigFactory();
            // 单个文件大小
            factory.setMaxFileSize(DataSize.parse("10240MB"));
            // 上传的总文件大小
            factory.setMaxRequestSize(DataSize.parse("20480MB"));
            return factory.createMultipartConfig();
        }

        // 映射 http 访问路径
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            // 将物理路径映射为 http 请求路径 /static/**
            // 注意，物理路径必须带协议：file://，否则无法映射成功
            registry.addResourceHandler("/static/**").addResourceLocations("file://" + fileUploadPath);
        }

    }
}
