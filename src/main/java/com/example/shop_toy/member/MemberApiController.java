package com.example.shop_toy.member;


import com.example.shop_toy.application.member.MemberFacade;
import com.example.shop_toy.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = {"A. 회원 API"})
@RequestMapping("/api/v1/members")
public class MemberApiController {
    private final MemberFacade memberFacade;

    @PostMapping("/sign-up")
    @ApiOperation(value = "회원가입", notes = "전달 받은 정보로 회원가입을 진행 합니다.")
    @ApiResponse(code = 200, message = "성공 시 회원정보를 반환 합니다")
    public CommonResponse<?> registerMember(@RequestBody @Valid MemberDto.RegisterMemberRequest registerRequest) {
        var command = registerRequest.toCommand();
        var memberInfo = memberFacade.registerMember(command);
        var response = new MemberDto.RegisterMemberResponse(memberInfo);
        return CommonResponse.success(response, "회원 가입을 완료 했습니다.");
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "아이디와 비밀번호로 로그인을 진행 합니다.")
    @ApiResponse(code = 200, message = "성공 시 회원의 토큰 정보를 반환 합니다")
    public CommonResponse<?> loginMember(@RequestBody @Valid MemberDto.LoginMemberRequest loginRequest) {
        var command = loginRequest.toCommand();
        var tokenInfo = memberFacade.loginMember(command);
        var response = new MemberDto.TokenInfoResponse(tokenInfo);
        return CommonResponse.success(response, "로그인을 완료 했습니다.");
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 정보 갱신", notes = "refreshToken 으로 accessToken의 유효시간을 갱신 합니다.")
    @ApiResponse(code = 200, message = "성공 시 회원의 토큰정보를 반환 합니다")
    public CommonResponse<?> reissue(@RequestBody @Valid MemberDto.ReissueTokenRequest reissueRequest) {
        var tokenInfo = memberFacade.reissueToken(reissueRequest);
        var response = new MemberDto.TokenInfoResponse(tokenInfo);
        return CommonResponse.success(response, "토큰 정보가 갱신되었습니다.");
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "회원의 토큰 정보를 삭제 합니다")
    @ApiResponse(code = 200, message = "성공시 완료 메시지를 반환 합니다.")
    public CommonResponse<?> logoutMember(@RequestBody @Valid MemberDto.LogoutRequest logoutRequest) {
        memberFacade.logoutMember(logoutRequest);
        return CommonResponse.success("로그아웃이 완료되었습니다.");
    }

//    @DeleteMapping
//    @ApiOperation(value = "회원 일괄 삭제", notes = "등록된 회원을 일괄 삭제 합니다 (테스트용).")
//    @ApiResponse(code = 200, message = "성공시 완료 메시지를 반환 합니다.")
//    public CommonResponse<?> deleteMember() {
//        memberFacade.deleteAllMember();
//        return CommonResponse.success("OK");
//    }

}
