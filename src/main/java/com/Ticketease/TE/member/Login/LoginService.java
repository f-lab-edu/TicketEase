package com.Ticketease.TE.member.Login;

import com.Ticketease.TE.member.Member;
import com.Ticketease.TE.member.MemberRepository;
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
        Optional<Member> findMember = memberRepository.findByNickName(username);
        if (findMember.isEmpty()){
            throw new UsernameNotFoundException("계정정보를 확인해주세요");
        }

        return User.builder()
                .username(findMember.get().getNickName())
                .password(findMember.get().getPassword())
                .roles("USER")
                .build();
    }
}