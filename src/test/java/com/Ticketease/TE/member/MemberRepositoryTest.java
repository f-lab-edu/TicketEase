package com.Ticketease.TE.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@DisplayName("멤버 레포지토리")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("DB에 멤버 추가")
    void givenMember_whenSaveMember_thenSuccess(){
        //given
        Member member = Member.of("helloworld", "1234qwer");
        Long currentCount =  memberRepository.count();

        //when
        memberRepository.save(member);

        //then
        assertThat(memberRepository.count()).isEqualTo(currentCount + 1);
    }

}