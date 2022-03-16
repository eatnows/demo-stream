package me.eatnows.streamdemo.member.app;

import me.eatnows.streamdemo.member.domain.Member;
import me.eatnows.streamdemo.member.MemberRepository;
import me.eatnows.streamdemo.member.domain.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfirmMemberService {
    private final MemberRepository memberRepository;

    public ConfirmMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void confirm(String id) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            throw new MemberNotFoundException();
        }
        member.confirm();
    }

}
