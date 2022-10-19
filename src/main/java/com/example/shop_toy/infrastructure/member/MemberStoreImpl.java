package com.example.shop_toy.infrastructure.member;


import com.example.shop_toy.domain.member.Member;
import com.example.shop_toy.domain.member.MemberStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {
    private final MemberRepository memberRepository;

    @Override
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public void deleteAll() {
        memberRepository.deleteAll();
    }
}
