package com.ticketease.te.account;

import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.ExceptionHandler;
import com.ticketease.te.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long amount;

	@OneToOne
	@JoinColumn(name = "memberId")
	private Member member;

	@Column(name = "member_ids")
	private Long memberId;

	private Account(final Long amount, final Long memberId) {
		this.amount = amount;
		this.memberId = memberId;
	}

	private Account(final Long amount, final Member member) {
		this.amount = amount;
		this.member = member;
	}

	public static Account of(final Long amount, final Long memberId) {
		return new Account(amount, memberId);
	}

	public static Account of(final Long amount, final Member member) {
		return new Account(amount, member);
	}

	public void deductAmount(Integer deductionAmount) {
		if (this.amount < deductionAmount) {
			throw new RuntimeException("잔액이 부족합니다.");
		}
		Long remainderAmount = this.amount - deductionAmount;
		this.amount = remainderAmount;
	}

	public void deductAmounts(Integer deductionAmount) {
		if (this.amount < deductionAmount) {
			throw new ExceptionHandler(ExceptionCode.LACK_OF_MONEY,ExceptionCode.LACK_OF_MONEY.getDescription());
		}
		this.amount = this.amount - deductionAmount;
	}
}
