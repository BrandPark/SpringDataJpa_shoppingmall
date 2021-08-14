package com.brandpark.jpa.jpashop.api;

import com.brandpark.jpa.jpashop.domain.Member;
import com.brandpark.jpa.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    /**
     * Entity 를 직접 사용한 API
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody Member member) {

        Long saveId = memberService.join(member);
        return new CreateMemberResponse(saveId);
    }

    /**
     * Entity 대신 DTO 를 사용한 API
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody CreateMemberRequest request) {
        Member member = Member.builder().name(request.name).build();
        Long saveId = memberService.join(member);
        return new CreateMemberResponse(saveId);
    }

    @Data
    private static class CreateMemberResponse {
        private final Long id;
    }

    @Data
    private static class CreateMemberRequest {
        private String name;
    }
}
