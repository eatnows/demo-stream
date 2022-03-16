package me.eatnows.streamdemo.member.domain;

import me.eatnows.streamdemo.member.MemberRepository;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository {

    private Map<String, Member> members = new HashMap<>();

    @Override
    public Member save(Member member) {
        members.put(member.getId(), member);
        return member;
    }

    @Override
    public Member findById(String id) {
        return members.get(id);
    }
}
