package home.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;
import home.dto.MemberDTO;

@Mapper
public interface MemberDAO {
	
	@Select("select * from member where username=#{username}")
	 public MemberDTO selectMember(String username) throws DataAccessException;
	
	 // 회원가입 DAO 일반 회원은 permit을 0으로 설정
    @Insert("insert into member(username, password, realname, email, tel, addr, birth, permit) " +
            "values(#{username}, #{password}, #{realname}, #{email}, #{tel}, #{addr}, #{birth}, 0)")
    public void insertMember(MemberDTO member) throws DataAccessException;
}
