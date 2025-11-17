package home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import home.dto.MemberDTO;
import home.service.MemberService;

@RestController
public class MemberController {
	
	@Autowired
    private MemberService memberService;
	
   @PostMapping("/join")
    public String joinMember(@RequestBody MemberDTO member) {
        memberService.joinMember(member);
        return "success";
    }
}
