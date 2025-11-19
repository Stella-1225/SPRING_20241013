package com.example.demo.model.service;
import lombok.*; // 어노테이션 자동 생성
import com.example.demo.model.domain.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Data
public class AddMemberRequest {
    // 이름: 공백 X, 메시지 추가
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    // 이메일: 공백 X, 이메일 형식, 메시지 추가
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    // 비밀번호: 공백 X, 8자 이상, 메시지 추가
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    // 나이: Integer로 변경 필요(Min/Max 적용 위해), 19세 이상 90세 이하, 메시지 추가
    @NotNull(message = "나이는 필수 입력 항목입니다.")
    @Min(value = 19, message = "나이는 19세 이상이어야 합니다.")
    @Max(value = 90, message = "나이는 90세 이하여야 합니다.")
    private Integer age;

    private String mobile;
    private String address;
    public Member toEntity(){ // Member 생성자를 통해 객체 생성
        return Member.builder()
        .name(name)
        .email(email)
        .password(password)
        .age(String.valueOf(age))
        .mobile(mobile)
        .address(address)
        .build();
    }
}
