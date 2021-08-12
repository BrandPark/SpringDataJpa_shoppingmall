package com.brandpark.jpa.jpashop.service;

import com.brandpark.jpa.jpashop.domain.Address;
import com.brandpark.jpa.jpashop.domain.Member;
import com.brandpark.jpa.jpashop.domain.Order;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @Test
    public void 회원가입이_된다() throws Exception {
        // given
        Member member = createMember();

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = em.find(Member.class, saveId);
        assertThat(findMember.getName()).as("이름").isEqualTo(member.getName());
        assertThat(findMember.getAddress()).as("주소").isEqualTo(member.getAddress());
    }

    @Test
    public void 회원을_모두_조회한다() throws Exception {
        // given
        Member member1 = createMember("mingon", "111-111");
        em.persist(member1);
        Member member2 = createMember("gildong", "222-222");
        em.persist(member2);

        List<Member> members = new ArrayList<>(List.of(member1, member2));

        // when
        List<Member> findMembers = memberService.findAll();

        // then
        assertThat(findMembers.size()).as("저장한 member와 조회된 member수가 같아야 한다").isEqualTo(members.size());
        for (int i = 0; i < findMembers.size(); i++) {
            assertThat(findMembers.get(i).getName()).as("이름이 같아야한다.").isEqualTo(members.get(i).getName());
            assertThat(findMembers.get(i).getAddress()).as("주소가 같아야한다.").isEqualTo(members.get(i).getAddress());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void 중복이름_회원가입은_예외가_발생한다() throws Exception {
        // given
        Member member = createMember("mingon","111-111");
        Member member2 = createMember("mingon", "222-222");

        // when
        memberService.join(member);
        memberService.join(member2);

        // then
        fail("예외가 발생해야 한다.");
    }

    private Member createMember() {
        return Member.builder()
                .name("mingon")
                .address(new Address("서울", "서대문구", "111-111"))
                .build();
    }
    private Member createMember(String name, String zipCode){
        return Member.builder()
                .name(name)
                .address(new Address("서울", "서대문구", zipCode))
                .build();
    }
}