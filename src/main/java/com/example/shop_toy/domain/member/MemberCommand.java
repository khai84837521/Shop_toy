package com.example.shop_toy.domain.member;


import com.example.shop_toy.common.util.HashGenerator;
import com.example.shop_toy.domain.member.role.Authority;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;

public class MemberCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterMember {
        private final String memberId;
        private final String memberEmail;
        private final String memberPassword;

        public Member toEntity() {
            return Member.builder()
                    .memberId(memberId)
                    .memberEmail(memberEmail)
                    .memberPassword(HashGenerator.passwordEncoder(memberPassword))
                    .roles(Collections.singletonList(Authority.ROLE_USER.name()))
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class LoginMember {
        private final String memberId;
        private final String memberPassword;

        public LoginMember(String memberId, String memberPassword) {
            this.memberId = memberId;
            this.memberPassword = memberPassword;
        }

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(memberId, memberPassword);
        }
    }


}
