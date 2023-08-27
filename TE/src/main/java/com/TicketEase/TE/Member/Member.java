package com.TicketEase.TE.Member;

import com.TicketEase.TE.Account.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @OneToOne(mappedBy = "member")
    private Account account;

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
