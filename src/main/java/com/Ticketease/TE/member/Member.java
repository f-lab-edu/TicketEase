package com.Ticketease.TE.member;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nickName;
    @CreatedDate
    private LocalDateTime createdDate;
    @Column(nullable = false)
    private String password;

    protected Member(){
    }

    private Member(final String nickName, final String password) {
        this.nickName = nickName;
        this.password = password;
    }

    public static Member of(final String nickName, final String password) {
        return new Member(nickName, password);
    }


}
