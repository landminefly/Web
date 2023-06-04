package com.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@WebServlet("/com/admin/upload_file")
//这个注解不能缺
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10, // 10MB
                 maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadFile extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取上传的文件
        request.setCharacterEncoding("UTF-8");
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();

        // 指定上传目录（没有就创建）
        String uploadDir = "C:\\Users\\leili\\Desktop";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // 将文件保存到指定目录
        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, new File(uploadDir, fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        // 返回上传成功的响应
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write("文件上传成功");
    }
}
