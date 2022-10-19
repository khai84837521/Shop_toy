package com.example.shop_toy.domain.member;


import com.example.shop_toy.common.exception.InvalidParamException;
import com.example.shop_toy.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="member")
public class Member extends AbstractEntity implements UserDetails {
    @Id
    private String memberId;

    @Column
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Builder
    public Member(String memberId, String memberEmail, String memberPassword, List<String> roles) {
        if (StringUtils.isEmpty(memberId)) throw new InvalidParamException("empty memberId");
        if (StringUtils.isEmpty(memberEmail)) throw new InvalidParamException("empty memberEmail");
        if (StringUtils.isEmpty(memberPassword)) throw new InvalidParamException("empty memberPassword");

        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.roles = roles;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return memberPassword;
    }

    @Override
    public String getUsername() {
        return memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
