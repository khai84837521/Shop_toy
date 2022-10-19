package com.example.shop_toy.domain.member;

import lombok.Getter;

@Getter
public class MemberInfo {
    private final String memberId;
    private final String memberEmail;

    public MemberInfo(Member member) {
        this.memberId = member.getMemberId();
        this.memberEmail = member.getMemberEmail();
    }
}
