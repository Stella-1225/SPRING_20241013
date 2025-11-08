package com.example.demo.controller;

import com.example.demo.model.domain.Article;
import com.example.demo.model.service.AddArticleRequest;
//import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.BlogService;
//import com.example.demo.model.service.TestService; // 최상단 서비스 클래스 연동 추가

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.example.demo.model.domain.Board;

@Controller // 컨트롤러 어노테이션 명시
public class BlogController{
    // 클래스 하단 작성
    @Autowired
    BlogService blogService; // DemoController 클래스 아래 객체 생성

    // @GetMapping("/article_list")
    // public String article_list() {
    //     return "article_list";
    // }

    // @GetMapping("/article_list") // 게시판 링크 지정
    // public String article_list(Model model) {
    //     List<Article> list = blogService.findAll(); // 게시판 리스트
    //     model.addAttribute("articles", list); // 모델에 추가
    //     return "article_list"; // .HTML 연결
    // }

    @GetMapping("/board_list") // 새로운 게시판 링크 지정
    public String board_list(Model model) {
        List<Board> list = blogService.findAll(); // 게시판 전체 리스트, 기존 Article에서 Board로 변경됨
        model.addAttribute("boards", list); // 모델에 추가
        return "board_list"; // .HTML 연결
    }

    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("boards", list.get()); // 존재할 경우 실제 Board 객체를 모델에 추가
            } else {
                // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
                return "/error_page/article_error"; // 오류 처리 페이지로 연결
            }
        return "board_view"; // .HTML 연결
    }

    @PostMapping("articles") // post 요청
    public String addArticle(@ModelAttribute AddArticleRequest request) { // 아직 없음(에러)
        blogService.save(request); // 게시글 저장
        return "redirect:/article_list"; // 저장 후 목록 페이지로 이동
    }

    // @GetMapping("/article_edit/{id}") // 게시판 링크 지정
    // public String article_edit(Model model, @PathVariable Long id) {
    // Optional<Article> list = blogService.findById(id); // 선택한 게시판 글
    // if (list.isPresent()) {
    //     model.addAttribute("article", list.get()); // 존재하면 Article 객체를 모델에 추가
    // } else {
    //         // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
    //         return "/error_page/article_error"; //오류 처리 페이지로 연결(이름 수정됨)
    //     }
    //     return "article_edit"; // .HTML 연결
    // }

    // @GetMapping("/article_edit/{id}")
    // public String article_edit(Model model, @PathVariable Long id) {
    //     Article article = blogService.findById(id)
    //                              .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    //     model.addAttribute("article", article);
    //     return "article_edit";
    // }

    @GetMapping("/board_edit/{id}") // 게시판 링크 지정
    public String board_edit(Model model, @PathVariable Long id) {
    Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("board", list.get());
        } else {
                // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
                //return "/error_page/article_error"; //오류 처리 페이지로 연결(이름 수정됨)
            }
        return "board_edit"; // .HTML 연결
    }

    @PutMapping("/api/board_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
         blogService.update(id, request);
         return "redirect:/board_list"; // 글 수정 이후 .html 연결
    }

    // @PutMapping("/api/article_edit/{id}")
    // public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
    //     blogService.update(id, request);
    //     return "redirect:/article_list"; // 글 수정 이후 .html 연결
    // }

    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/article_list";
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {

        // NumberFormatException 처리
        @ExceptionHandler(NumberFormatException.class)
        public String handleNumberFormatException(NumberFormatException ex, Model model) {
            model.addAttribute("errorMessage", "잘못된 요청입니다. ID는 숫자여야 합니다.");
            return "/error_page/new_error"; // Thymeleaf 에러 페이지 경로
        }

        // 기타 예외 처리
        @ExceptionHandler(Exception.class)
        public String handleException(Exception ex, Model model) {
            model.addAttribute("errorMessage", "알 수 없는 오류가 발생했습니다.");
             return "/error_page/article_error"; // 일반 에러 페이지
        }
    }
}