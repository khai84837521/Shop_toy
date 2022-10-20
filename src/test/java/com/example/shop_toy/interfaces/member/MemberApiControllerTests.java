package com.example.shop_toy.interfaces.member;

import com.example.shop_toy.ShopToyApplication;
import com.example.shop_toy.domain.member.MemberService;
import com.example.shop_toy.interfaces.member.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ShopToyApplication.class)
@ExtendWith({SpringExtension.class })
public class MemberApiControllerTests {
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    private MemberDto.RegisterMemberRequest newMemberDto() {
        return MemberDto.RegisterMemberRequest.builder()
                .memberId("test12345")
                .memberEmail("test12345@testEmail.com")
                .memberPassword("abcdf1234!@#$")
                .build();
    }

    private MemberDto.LoginMemberRequest loginMemberDto(MemberDto.RegisterMemberRequest member) {
        return MemberDto.LoginMemberRequest.builder()
                .memberId(member.getMemberId())
                .memberPassword(member.getMemberPassword())
                .build();
    }

    @Test
    @DisplayName("회원가입 - 정상적으로 데이터를 입력")
    @Transactional
    public void registerMember() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var memberDto = newMemberDto();

        this.mockMvc.perform(post("/api/v1/members/sign-up")
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(memberDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("회원가입 - 잘못된 데이터를 입력")
    public void registerMemberInvalid() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var memberDto = MemberDto.RegisterMemberRequest.builder()
                .memberId("")
                .memberEmail("")
                .memberPassword("")
                .build();

        this.mockMvc.perform(post("/api/v1/members/sign-up")
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(memberDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect((status().is4xxClientError()))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인 - 정상적으로 데이터를 입력")
    public void loginMember() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var memberDto = newMemberDto();
        memberService.registerMember(memberDto.toCommand());
        var request = loginMemberDto(memberDto);

        this.mockMvc.perform(post("/api/v1/members/login")
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인 - 잘못된 데이터를 입력")
    public void loginMemberInvalid() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        var request = MemberDto.LoginMemberRequest.builder()
                .memberId("")
                .memberPassword("")
                .build();

        this.mockMvc.perform(post("/api/v1/members/login")
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect((status().is4xxClientError()))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인 - 잘못된 패스워드를 입력")
    public void loginMemberBadPassword() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        var request = MemberDto.LoginMemberRequest.builder()
                .memberId("test12345")
                .memberPassword("BadPassword!q12!")
                .build();

        this.mockMvc.perform(post("/api/v1/members/login")
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect((status().isOk()))
                .andDo(print());
    }


    @Test
    @Transactional
    @DisplayName("로그아웃 - 정상적으로 데이터를 입력")
    public void logoutMember() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        var memberDto = newMemberDto();
        memberService.registerMember(memberDto.toCommand());
        var loginDto = loginMemberDto(memberDto);
        var tokenInfo = memberService.loginMember(loginDto.toCommand());

        var request = MemberDto.LogoutRequest.builder()
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
                .build();

        this.mockMvc.perform(post("/api/v1/members/logout")
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그아웃 - 비로그인 상태에서 접근")
    public void logoutMemberNonLogin() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        var memberDto = newMemberDto();
        memberService.registerMember(memberDto.toCommand());

        var request = MemberDto.LogoutRequest.builder()
                .accessToken("")
                .refreshToken("")
                .build();

        this.mockMvc.perform(post("/api/v1/members/logout")
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }


    @Test
    @Transactional
    @DisplayName("토큰 정보 갱신 - 정상적으로 데이터를 입력")
    public void reissue() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var memberDto = newMemberDto();
        memberService.registerMember(memberDto.toCommand());
        var loginDto = loginMemberDto(memberDto);
        var tokenInfo = memberService.loginMember(loginDto.toCommand());

        var request = MemberDto.ReissueTokenRequest.builder()
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
                .build();


        this.mockMvc.perform(post("/api/v1/members/reissue")
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
