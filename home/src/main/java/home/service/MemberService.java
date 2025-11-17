package home.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import home.dao.MemberDAO;
import home.dto.MemberDTO;
import home.role.UserRole;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberDAO dao;

    @Autowired
    private PasswordEncoder passwordEncoder;  // 패스워드 암호화 의존성 주입

    // 로그인 security(UserDetails)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDTO member = dao.selectMember(username); // username으로 회원 조회

        if (member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (member.getPermit() == 1) { // permit이 1이면 관리자
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else { // 0이면 일반 사용자
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        // Spring Security User 객체 반환 (DB 비밀번호 그대로 사용)
        return new User(member.getUsername(), member.getPassword(), authorities);
    }

    // 회원가입 Service
    public void joinMember(MemberDTO member) {
        // 1. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        // 2. DAO 호출하여 DB에 저장
        dao.insertMember(member);
    }
}
