package gdsc.nanuming.member.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import gdsc.nanuming.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nickname;

	@Column(unique = true)
	private String providerId;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	private MemberRole role;

	@Builder
	private Member(String nickname, String providerId, MemberRole role) {
		this.nickname = nickname;
		this.providerId = providerId;
		this.role = role;
	}

	public static Member of(String nickname, String providerId) {
		return Member.builder()
			.nickname(nickname)
			.providerId(providerId)
			.role(MemberRole.USER)
			.build();
	}
}
