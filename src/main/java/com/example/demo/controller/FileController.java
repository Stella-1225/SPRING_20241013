package com.example.demo.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID; // [추가] UUID 생성을 위해 추가

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // 컨트롤러 어노테이션 명시
public class FileController{
    // 클래스 하단 작성
    @Autowired

    @Value("${spring.servlet.multipart.location}") // properties 등록된 설정(경로) 주입  //서버에 업로드할 경로
    private String uploadFolder;
    
    @PostMapping("/upload-email")
    public String uploadEmail( // 이메일, 제목, 메시지를 전달받음
        @RequestParam("email") String email,
        @RequestParam("subject") String subject,
        @RequestParam("message") String message,
        RedirectAttributes redirectAttributes) {
            try {
                Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
                String fileName = sanitizedEmail + ".txt"; // 기본 파일 이름
                Path filePath = uploadPath.resolve(fileName); // 업로드 폴더에 .txt 이름 설정

                // [수정됨] 동일 파일 업로드 시 다른 이름으로 업로드 (중복 검사)
                if (Files.exists(filePath)) {
                    // 파일이 존재하면 랜덤 UUID를 붙여서 이름 변경
                    String uuid = UUID.randomUUID().toString();
                    String newFileName = sanitizedEmail + "_" + uuid + ".txt";
                    filePath = uploadPath.resolve(newFileName);
                }

                System.out.println("File path: " + filePath); // 디버깅용 출력
                
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                    writer.write("메일 제목: " + subject); // 쓰기
                    writer.newLine(); // 줄 바꿈
                    writer.write("요청 메시지:");
                    writer.newLine();
                    writer.write(message);
                }

                redirectAttributes.addFlashAttribute("message", "메일 내용이 성공적으로 업로드되었습니다!");
                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("message", "업로드 중 오류가 발생했습니다.");
                    return "/error_page/article_error"; // 오류 처리 페이지로 연결
                }
                return "upload_end"; // .html 파일 연동
        }
}