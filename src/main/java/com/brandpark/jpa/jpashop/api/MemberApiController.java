package com.brandpark.jpa.jpashop.api;

import com.brandpark.jpa.jpashop.domain.Member;
import com.brandpark.jpa.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    /**
     * Entity 를 직접 사용한 회원 저장 API
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody Member member) {

        Long saveId = memberService.join(member);
        return new CreateMemberResponse(saveId);
    }

    /**
     * Entity 대신 DTO 를 사용한 회원 저장 API
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody CreateMemberRequest request) {
        Member member = Member.builder().name(request.name).build();
        Long saveId = memberService.join(member);
        return new CreateMemberResponse(saveId);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(id, findMember.getName());
    }

    /**
     * Entity 를 직접 사용한 모든 회원 조회 API
     */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findAll();
    }

    /**
     * DTO 를 사용한 모든 회원 조회 API
     */
    @GetMapping("/api/v2/members")
    public Result<List<MemberDto>> membersV2() {

        List<MemberDto> members = memberService.findAll()
                .stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result<List<MemberDto>>(members);
    }

    @Data
    private static class CreateMemberResponse {
        private final Long id;
    }

    @Data
    private static class CreateMemberRequest {
        private String name;
    }

    @Data
    private static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    private static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    private static class MemberDto {
        private String name;
    }

    @Data
    @AllArgsConstructor
    private static class Result<T> {
        private T data;
    }
}
