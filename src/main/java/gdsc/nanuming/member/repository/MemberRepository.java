package gdsc.nanuming.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.nanuming.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
