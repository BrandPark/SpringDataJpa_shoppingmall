package com.brandpark.jpa.jpashop.repo;

import com.brandpark.jpa.jpashop.domain.Address;
import com.brandpark.jpa.jpashop.domain.Member;
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

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void member가_저장된다() throws Exception {
        // given
        Member member = createMember("mingon", "111-111");

        // when
        Long savedId = memberRepository.save(member);

        // then
        Member findMember = em.find(Member.class, savedId);
        assertThat(findMember.getName()).isEqualTo(member.getName()).as("이름이 저장된다.");
        assertThat(findMember.getAddress()).isEqualTo(member.getAddress()).as("주소가 저장된다.");
    }
    
    @Test
    public void member가_단일조회_된다() throws Exception {
        // given
        Member member = createMember("mingon", "111-111");
        em.persist(member);
        
        // when
        Member findMember = memberRepository.findOne(member.getId());
        
        // then
        assertThat(findMember.getName()).isEqualTo(member.getName()).as("이름이 저장된다.");
        assertThat(findMember.getAddress()).isEqualTo(member.getAddress()).as("주소가 저장된다.");
    }
    
    @Test
    public void member가_전체조회_된다() throws Exception {
        // given
        Member member1 = createMember("mingon", "111-111");
        em.persist(member1);
        Member member2 = createMember("gildong", "222-222");
        em.persist(member2);

        List<Member> members = new ArrayList<>(List.of(member1, member2));

        // when
        List<Member> findMembers = memberRepository.findAll();

        // then
        assertThat(findMembers.size()).as("저장한 member와 조회된 member수가 같아야 한다").isEqualTo(members.size());
        for (int i = 0; i < findMembers.size(); i++) {
            assertThat(findMembers.get(i).getName()).as("이름이 같아야한다.").isEqualTo(members.get(i).getName());
            assertThat(findMembers.get(i).getAddress()).as("주소가 같아야한다.").isEqualTo(members.get(i).getAddress());
        }
    }

    @Test
    public void member가_이름으로_조회된다() throws Exception {
        // given
        Member member = createMember("mingon", "111-111");
        em.persist(member);

        // when
        Member findMember = memberRepository.findByName("mingon").get(0);

        // then
        assertThat(findMember.getName()).isEqualTo(member.getName()).as("이름이 저장된다.");
        assertThat(findMember.getAddress()).isEqualTo(member.getAddress()).as("주소가 저장된다.");
    }

    private Member createMember(String name, String zipCode){
        return Member.builder()
                .name(name)
                .address(new Address("서울", "서대문구", zipCode))
                .build();
    }
}