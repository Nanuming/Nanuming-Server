package gdsc.nanuming.member.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import gdsc.nanuming.common.BaseEntity;
import gdsc.nanuming.member.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseEntity {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;

	private String nickname;

	@Column(unique = true)
	private String providerId;

	private String picture;

	@Enumerated(EnumType.STRING)
	private MemberRole role;

	@Builder
	private Member(String email, String providerId, String picture, MemberRole role) {
		this.email = email;
		this.providerId = providerId;
		this.picture = picture;
		this.role = role;
	}

}
