package me.eatnows.streamdemo.member;

import me.eatnows.streamdemo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, String> {

    Member findById(String id);

    Member save(Member member);
}
