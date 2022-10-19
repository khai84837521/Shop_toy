package com.example.shop_toy.domain.member;

public interface MemberReader {
    Member getMemberById(String memberId);
    boolean checkMemberById(String memberId);

    Member getMemberByEmail(String memberEmail);
    boolean checkMemberByEmail(String memberEmail);

    boolean loginMember(String memberId, String memberPassword);
}
