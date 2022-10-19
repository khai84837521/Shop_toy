package com.example.shop_toy.domain.member;


import com.example.shop_toy.domain.member.token.TokenInfo;
import com.example.shop_toy.interfaces.member.MemberDto;

public interface MemberService {
    MemberInfo registerMember(MemberCommand.RegisterMember command);
    TokenInfo loginMember(MemberCommand.LoginMember command);
    TokenInfo reissueToken(MemberDto.ReissueTokenRequest reissue);
    void logoutMember(MemberDto.LogoutRequest logout);

    void deleteAllMember();
}
