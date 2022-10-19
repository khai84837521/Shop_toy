package com.example.shop_toy.interfaces.member;


import com.example.shop_toy.domain.member.MemberCommand;
import com.example.shop_toy.domain.member.MemberInfo;
import com.example.shop_toy.domain.member.token.TokenInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class MemberDto {

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "회원 가입 요청", description = "회원 가입에 필요한 정보 입니다.")
    public static class RegisterMemberRequest {
        @NotEmpty(message = "아이디는 필수 값 입니다.")
        @Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9_]{5,11}$", message = "아이디는 6~12자 영문, 숫자를 사용 하세요.")
        @ApiModelProperty(name = "memberId", example = "test12345", notes = "아이디는 6~12자 영문, 숫자를 입력 받습니다.", required = true)
        private final String memberId;

        @NotEmpty(message = "이메일은 필수 값 입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        @ApiModelProperty(name = "memberEmail", example = "test12345@example.com", notes = "이메일 형식에 맞게 입력 받습니다.", required = true)
        private final String memberEmail;

        @NotEmpty(message = "비밀번호는 필수 값 입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @ApiModelProperty(name = "memberPassword", example = "abcd1234!@", notes = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 입력 받습니다.", required = true)
        private final String memberPassword;

        public MemberCommand.RegisterMember toCommand() {
            return MemberCommand.RegisterMember.builder()
                    .memberId(memberId)
                    .memberEmail(memberEmail)
                    .memberPassword(memberPassword)
                    .build();
        }
    }

    @Getter
    @ToString
    @ApiModel(value = "회원 가입 응답", description = "회원 가입한 회원의 아이디와 이메일 입니다.")
    public static class RegisterMemberResponse {
        @ApiModelProperty(name = "memberId", example = "test1234", notes = "가입 처리된 아이디.")
        private final String memberId;
        @ApiModelProperty(name = "memberEmail", example = "test1234@example.com", notes = "가입 처리된 이메일.")
        private final String memberEmail;

        public RegisterMemberResponse(MemberInfo memberInfo) {
            this.memberId = memberInfo.getMemberId();
            this.memberEmail = memberInfo.getMemberEmail();
        }
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "로그인 요청", description = "로그인에 필요한 정보 입니다.")
    public static class LoginMemberRequest {
        @NotEmpty(message = "아이디는 필수 값 입니다.")
        @Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9_]{5,11}$", message = "아이디는 6~12자 영문, 숫자를 사용 하세요.")
        @ApiModelProperty(name = "memberId", example = "test1234", notes = "아이디는 6~12자 영문, 숫자를 입력 받습니다.", required = true)
        private final String memberId;

        @NotEmpty(message = "비밀번호는 필수 값 입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @ApiModelProperty(name = "memberPassword", example = "abcd1234!@", notes = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 입력 받습니다.", required = true)
        private final String memberPassword;

        public MemberCommand.LoginMember toCommand() {
            return MemberCommand.LoginMember.builder()
                    .memberId(memberId)
                    .memberPassword(memberPassword)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "토큰 갱신 요청 정보", description = "토큰 갱신에 필요한 정보 입니다.")
    public static class ReissueTokenRequest {
        @NotEmpty(message = "accessToken 은 필수 값 입니다.")
        @ApiModelProperty(name = "accessToken",
                example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzNDUiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjU5NDQ2NjU0fQ.WLmpRAaJ60LtLOrBmKkWNYOy1BOZw-hG8CuZEVlMKXw",
                notes = "회원의 엑세스 토큰 입니다.")
        private String accessToken;

        @NotEmpty(message = "refreshToken 은 필수 값 입니다.")
        @ApiModelProperty(name = "accessToken",
                example = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjAwNDk2NTR9.ExqgYevzLDn6Z2YPBpaA0mCFODlSjdRZgAokUe_zr98",
                notes = "회원의 리프레시 토큰 입니다.")
        private String refreshToken;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "로그아웃 요청 정보", description = "로그아웃에 필요한 정보 입니다.")
    public static class LogoutRequest {
        @NotEmpty(message = "잘못된 요청 입니다.")
        @ApiModelProperty(name = "accessToken",
                example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzNDUiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjU5NDQ2NjU0fQ.WLmpRAaJ60LtLOrBmKkWNYOy1BOZw-hG8CuZEVlMKXw",
                notes = "회원의 엑세스 토큰 입니다.")
        private String accessToken;

        @NotEmpty(message = "잘못된 요청 입니다.")
        @ApiModelProperty(name = "refreshToken",
                example = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjAwNDk2NTR9.ExqgYevzLDn6Z2YPBpaA0mCFODlSjdRZgAokUe_zr98",
                notes = "회원의 리프레시 토큰 입니다.")
        private String refreshToken;
    }

    @Getter
    @ToString
    public static class TokenInfoResponse {
        @ApiModelProperty(name = "grantType",
                example = "Bearer",
                notes = "회원의 토큰 유형 입니다.")
        private final String grantType;

        @ApiModelProperty(name = "accessToken",
                example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzNDUiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjU5NDQ2NjU0fQ.WLmpRAaJ60LtLOrBmKkWNYOy1BOZw-hG8CuZEVlMKXw",
                notes = "회원의 엑세스 토큰 입니다.")
        private final String accessToken;

        @ApiModelProperty(name = "accessToken",
                example = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjAwNDk2NTR9.ExqgYevzLDn6Z2YPBpaA0mCFODlSjdRZgAokUe_zr98",
                notes = "회원의 리프레시 토큰 입니다.")
        private final String refreshToken;

        @ApiModelProperty(name = "refreshTokenExpirationTime",
                example = "604800000",
                notes = "회원의 리프레시 토큰 유효시간 입니다.")
        private final Long refreshTokenExpirationTime;

        public TokenInfoResponse(TokenInfo tokenInfo) {
            this.grantType = tokenInfo.getGrantType();
            this.accessToken = tokenInfo.getAccessToken();
            this.refreshToken = tokenInfo.getRefreshToken();
            this.refreshTokenExpirationTime = tokenInfo.getRefreshTokenExpirationTime();
        }
    }

}
