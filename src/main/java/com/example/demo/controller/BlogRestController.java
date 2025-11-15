package com.example.demo.controller;

import com.example.demo.model.domain.Article;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController // @Controller + @ResponseBody
public class BlogRestController {
private final BlogService blogService;

    // @PostMapping("/api/articles") // post 요청
    // public ResponseEntity<Article> addArticle(@ModelAttribute AddArticleRequest request) { // 아직 없음(에러)
    //     Article saveArticle = blogService.save(request); // 게시글 저장
    //     return ResponseEntity.status(HttpStatus.CREATED) // 상태 코드 및 게시글 정보 반환
    //     .body(saveArticle);
    // }
}