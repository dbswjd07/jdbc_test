package jdbc_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import common.DBConnection;

class MemberDTO{
	private int age;
	private String name, id;
	public int getAge() {return age;}
	public void setAge(int age) {this.age = age;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	
}
class DB{
	//데이터베이스 연결 객체
	Connection con;
	//DB 명령어 전송 객체
	PreparedStatement ps;
	//DB 명령어 후 결과 얻어오는 객체
	ResultSet rs;
	public DB() {
		try {
			con = DBConnection.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public DB() {
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			System.out.println("드라이브 로드 성공");
//			//오라클 연결된 연결 객체를 얻어오는 기능
//			String id = "java", pwd = "1234";
//			String url = "jdbc:oracle:thin:@localhost:1521/xe";
//			con = DriverManager.getConnection(url, id, pwd);
//			System.out.println("연결 성공");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//	}
	
	public void select() {
		String sql = "select * from newst";
		try {
			ps = con.prepareStatement(sql);
			//select는 무조건 executeQuery 사용
			rs = ps.executeQuery();
			while(rs.next()) {
				//System.out.println(rs.next());
				System.out.println(rs.getString("id"));
				System.out.println(rs.getString("name"));
				System.out.println(rs.getInt("age"));
			}
			
			/*
			System.out.println(rs.next());
			System.out.println(rs.getString("id"));
			System.out.println(rs.getString("name"));
			System.out.println(rs.getInt("age"));
			
			System.out.println(rs.next());
			System.out.println(rs.getString("id"));
			System.out.println(rs.getString("name"));
			System.out.println(rs.getInt("age"));
			System.out.println(rs.next());
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<MemberDTO> select_2() {
		String sql = "select * from newst";
		ArrayList<MemberDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				
				list.add(dto);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	
	}
	public MemberDTO search(String id) {
		String sql = "select*from newst where id = '"+id+"'";
		MemberDTO dto = null;
		try {
			ps= con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				/*
					System.out.println(rs.getString("id"));
				System.out.println(rs.getString("name"));
					System.out.println(rs.getString("age"));
				*/
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public int register(MemberDTO dd) {
		int result = 0;
		String sql = "insert into newst(id, name,age) values(?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dd.getId());
			ps.setString(2, dd.getName());
			ps.setInt(3, dd.getAge());
			//ps.executeQuery();
			result = ps.executeUpdate();
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return result;
	}
	public int delete(String id) {
		String sql = "delete from newst where id=?";
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			result = ps.executeUpdate();
		} catch (Exception e) {
			
		}
		return result;
	}
}
public class MainClass01 {
public static void main(String[] args) {
	DB db = new DB();
	//db.select();
	Scanner input = new Scanner(System.in);
	int num, age = 0;
	String id = null, name = null;
	while(true) {
		System.out.println("1.모든 사용자 보기");
		System.out.println("2.검색");
		System.out.println("3.회원 가입");
		System.out.println("4.회원 삭제");
		num = input.nextInt();
		switch(num) {
		case 1: 
			ArrayList<MemberDTO> list = db.select_2();
			System.out.println("id\tname\tage");
			System.out.println("=========================");
			for(MemberDTO m : list) {
				System.out.print(m.getId()+"\t");
				System.out.print(m.getName()+"\t");
				System.out.println(m.getAge());
				System.out.println("------------------------");
			}
//			for(int i =0; i<list.size();i++) {
//			System.out.println(list.get(i).getId());
//			System.out.println(list.get(i).getName());
//			System.out.println(list.get(i).getAge());
//			System.out.println("------------------------");
//		}
			break;
			
		case 2:
			System.out.println("검색 id 입력");
			id = input.next();
			db.search(id);
			MemberDTO d  = db.search(id);
			if(d== null) {
				System.out.println("존재하지 않는 아이디입니다");
			}else {
				System.out.println("id: "+d.getId());
				System.out.println("name: "+d.getName());
				System.out.println("age: "+d.getAge());
				
			}
			
			break;
		
		case 3:
			System.out.println("아이디 입력");
			id = input.next();
			System.out.println("이름 입력");
			name = input.next();
			System.out.println("나이 입력");
			age = input.nextInt();
			MemberDTO dd = new MemberDTO();
			dd.setId(id); dd.setName(name); dd.setAge(age);
			int result = db.register(dd);
			if(result ==0) {
				System.out.println("동일한 아이디가 존재합니다");
			}else {
				System.out.println("회원 가입을 축하합니다");
			}
			
			break;
			
		case 4:
			System.out.println("아이디 입력");
			id = input.next();
			int result1 = db.delete(id);
			if(result1 ==0) {
				System.out.println("존재하지 않는 회원");
			}else {
				System.out.println(id+"님은 삭제!!!");
			}
			break;
		
			
		}
		
	}
	
	
	
}
}
