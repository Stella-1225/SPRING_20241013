package com.example.demo.controller;

import com.example.demo.model.domain.Article;
import com.example.demo.model.service.AddArticleRequest;
//import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.BlogService;
//import com.example.demo.model.service.TestService; // 최상단 서비스 클래스 연동 추가

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // 컨트롤러 어노테이션 명시
public class BlogController{
    // 클래스 하단 작성
    @Autowired
    BlogService blogService; // DemoController 클래스 아래 객체 생성

    // @GetMapping("/article_list")
    // public String article_list() {
    //     return "article_list";
    // }

    @GetMapping("/article_list") // 게시판 링크 지정
    public String article_list(Model model) {
        List<Article> list = blogService.findAll(); // 게시판 리스트
        model.addAttribute("articles", list); // 모델에 추가
        return "article_list"; // .HTML 연결
    }

     @PostMapping("articles") // post 요청
     public String addArticle(@ModelAttribute AddArticleRequest request) { // 아직 없음(에러)
         blogService.save(request); // 게시글 저장
         return "redirect:/article_list"; // 저장 후 목록 페이지로 이동
     }
}