package com.ticketease.te.member.Login;

import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByNickName(username).orElseThrow(() -> new UsernameNotFoundException("계정정보를 확인해주세요"));
        return User.builder()
                .username(member.getNickName())
                .password(member.getPassword())
                .roles("USER")
                .build();
    }
}