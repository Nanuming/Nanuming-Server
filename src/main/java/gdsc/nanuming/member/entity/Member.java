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

	private String email;

	private String nickname;

	@Column(unique = true)
	private String providerId;

	private String picture;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	private MemberRole role;

	@Builder
	private Member(String email, String nickname, String providerId, String picture, MemberRole role) {
		this.email = email;
		this.nickname = nickname;
		this.providerId = providerId;
		this.picture = picture;
		this.role = role;
	}

	public static Member of(String email, String nickname, String providerId, String picture) {
		return Member.builder()
			.email(email)
			.nickname(nickname)
			.providerId(providerId)
			.picture(picture)
			.role(MemberRole.USER)
			.build();
	}
}
