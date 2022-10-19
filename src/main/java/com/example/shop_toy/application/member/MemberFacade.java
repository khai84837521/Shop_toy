package com.example.shop_toy.application.member;


import com.example.shop_toy.domain.member.MemberCommand;
import com.example.shop_toy.domain.member.MemberInfo;
import com.example.shop_toy.domain.member.MemberService;
import com.example.shop_toy.domain.member.token.TokenInfo;
import com.example.shop_toy.member.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberService memberService;

    public MemberInfo registerMember(MemberCommand.RegisterMember command) {
        return memberService.registerMember(command);
    }

    public TokenInfo loginMember(MemberCommand.LoginMember command) {
        return memberService.loginMember(command);
    }

    public TokenInfo reissueToken(MemberDto.ReissueTokenRequest reissueToken) {
        return memberService.reissueToken(reissueToken);
    }

    public void logoutMember(MemberDto.LogoutRequest logout) {
        memberService.logoutMember(logout);
    }

    public void deleteAllMember() {
        memberService.deleteAllMember();
    }
}
