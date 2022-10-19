package com.example.shop_toy.domain.member;

public interface MemberStore {
    Member saveMember(Member member);

    void deleteAll();
}
