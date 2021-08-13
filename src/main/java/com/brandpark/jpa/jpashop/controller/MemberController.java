package com.brandpark.jpa.jpashop.controller;

import com.brandpark.jpa.jpashop.controller.form.MemberForm;
import com.brandpark.jpa.jpashop.domain.Address;
import com.brandpark.jpa.jpashop.domain.Member;
import com.brandpark.jpa.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/members")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/new")
    public String createForm(Model model) {
        MemberForm memberForm = new MemberForm();
        model.addAttribute("memberForm", memberForm);

        return "members/createMemberForm";
    }

    @PostMapping("/new")
    public String create(@Valid MemberForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = Member.builder()
                .name(form.getName())
                .address(new Address(form.getCity(), form.getStreet(), form.getZipcode()))
                .build();

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);

        return "members/memberList";
    }
}
