/* 설명 : user 등록을 관리하는 GUI를 만들기 위한 class입니다.
user 정보 : 아이디, 비밀번호, 이름, 성별, 이메일, 생년월일로 구성되어 있습니다.
아이디, 비밀번호, 이름, 성별, 생일은 반드시 값을 받아야 합니다.
이메일은 null이어도 괜찮습니다.
추가해야 하는 것 (전체) : 주석, join쿼리 활용한 기능, view 2개 이상, index 4개 이상
추가하고 싶은 것 : 에러메세지를 창으로 표시, 검색기능...? */

package DB2021Team01;

import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class DB2021Team01_GUIuser extends JFrame implements ActionListener {

	JPanel p, p1, p2, p3, p4, p5;
	// button | id & password | name | gender | email | birthday | user count
	JTextField tfID, tfNAME, tfEMAIL1, tfEMAIL2, tfByear, tfBmonth, tfBdate;
	//아이디, 이름은 하나의 입력으로 받고, 이메일은 @를 사이에 두고 두 개의 입력값,
	// 생년월일은 -를 사이에 두고 3개의 입력값으로 받습니다.
	JPasswordField pfPASSWORD;			//입력값이 보이지 않게 처리합니다.
	JRadioButton rbMan, rbWoman, rbN;	//man = 'M', woman = 'W', N = 'N'
	JButton btnInsert, btnCancel, btnUpdate, btnDelete;	
	// 유저 정보를 삽입하는 버튼, 유저 정보 관리를 취소하는 버튼, 유저 정보를 갱신하는 버튼, 유저 정보를 삭제하는 버튼

	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public DB2021Team01_GUIuser() 
	{

		setTitle("유저 관리");	//창 이름
		newGUI();
		toDB();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);	//정중앙에 창 뜨게 하기
	}
	
	private void newGUI() {
		
		//텍스트 필드
		tfID = new JTextField(15);	//id
		tfNAME = new JTextField(15);	//name
		tfEMAIL1 = new JTextField(15);	//email
		tfEMAIL2 = new JTextField(14);
		tfByear = new JTextField(4);		//birthday
		tfBmonth = new JTextField(2);
		tfBdate = new JTextField(2);
		
		//비밀번호 필드
		pfPASSWORD = new JPasswordField(20);	//password
		
		//라디오 버튼
		ButtonGroup a = new ButtonGroup();
		rbMan = new JRadioButton();			//gender
		rbWoman = new JRadioButton();
		rbN = new JRadioButton();
		a.add(rbMan); a.add(rbWoman); a.add(rbN);
		
		//버튼
		btnInsert = new JButton("삽입");		//button
		btnDelete = new JButton("삭제");
		btnUpdate = new JButton("갱신");
		btnCancel = new JButton("취소");
		
		this.setLayout(new GridLayout(6, 1));
		
		p = new JPanel();	
		p1 = new JPanel();	
		p2 = new JPanel();	
		p3 = new JPanel();	
		p4 = new JPanel();	
		p5 = new JPanel();	
		
		this.add(p1); this.add(p2); this.add(p3);
		 this.add(p4); this.add(p5); this.add(p);

		 JLabel l1 = new JLabel("아이디 : ");
		 JLabel l2 = new JLabel("비밀번호 : ");
		p1.add(l1); p1.add(tfID); p1.add(l2);  p1.add(pfPASSWORD);
		
		 JLabel l3 = new JLabel("이름 : ");
		 p2.add(l3); p2.add(tfNAME);
		 
		 JLabel l4 = new JLabel("성별 : ");
		 JLabel l5 = new JLabel("여 ");
		 JLabel l6 = new JLabel("남 ");
		 JLabel l7 = new JLabel("미입력 ");
		 p3.add(l4);  p3.add(l5); p3.add(rbWoman); 
		 p3.add(l6); p3.add(rbMan);  p3.add(l7); p3.add(rbN);
		 
		 JLabel l8 = new JLabel("이메일 : ");
		 JLabel l9 = new JLabel("@");
		p4.add(l8); p4.add(tfEMAIL1); p4.add(l9); p4.add(tfEMAIL2);
		
		 JLabel l10 = new JLabel("생년월일 : ");
		 JLabel l11 = new JLabel(" - ");
		 JLabel l12 = new JLabel(" - ");
		 p5.add(l10); p5.add(tfByear); p5.add(l11); 
		 p5.add(tfBmonth); p5.add(l12); p5.add(tfBdate);
		
		p.add(btnInsert);
		p.add(btnDelete);
		p.add(btnUpdate);
		p.add(btnCancel);

		
		//리스너 등록
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDelete.addActionListener(this);
		
		setSize(600,300);		//창 크기

	}
	
	private void toDB() {
		try {
			Class.forName(DB2021Team01_DBManager.JDBC_Driver);
			System.out.println("드라이버 연결 완료");
			System.out.println("-----------------------");
			
		} catch (Exception e) {
			System.out.println("오류가 발생했습니다. :" + e);
		}
	}

	@SuppressWarnings("deprecation")
	private void setUser(DB2021Team01_userGS U) {
		
		U.setID(tfID.getText());
		U.setPASSWORD(pfPASSWORD.getText());
		U.setNAME(tfNAME.getText());
		
		String G = "N";
		if (rbMan.isSelected()) {
			G = "M";
		} else if (rbWoman.isSelected()) {
			G = "W";
		}
		U.setGENDER(G);
		
		String E = tfEMAIL1.getText() + "@" + tfEMAIL2.getText();
		U.setEMAIL(E);
		
		String B = tfByear.getText() + tfBmonth.getText() + tfBdate.getText();
		U.setBIRTHDAY(B);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			
			conn = DriverManager.getConnection(DB2021Team01_DBManager.DB_URL, 
					DB2021Team01_DBManager.userName, DB2021Team01_DBManager.password);
			System.out.println("데이터베이스 연결 완료");
			System.out.println("-----------------------");
			DB2021Team01_userGS user = new DB2021Team01_userGS();
			
			String sql = "";
			
			if (e.getSource() == btnInsert) {
				
				conn.setAutoCommit(false);
				sql = "insert into DB2021_users values(?,?,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				setUser(user);
				
				System.out.println(user.getID());
				System.out.println(user.getPASSWORD());
				System.out.println(user.getNAME());
				System.out.println(user.getGENDER());
				System.out.println(user.getEMAIL());
				System.out.println(user.getBIRTHDAY());
				
				pstmt.setString(1,  user.getID());
				pstmt.setString(2,  user.getPASSWORD());
				pstmt.setString(3,  user.getNAME());
				pstmt.setString(4,  user.getGENDER());
				pstmt.setString(5,  user.getEMAIL());
				pstmt.setString(6,  user.getBIRTHDAY());
				int rs = pstmt.executeUpdate();
				
				if (user.getID().length() == 0 || user.getPASSWORD().length() == 0|| 
						user.getNAME().length() == 0 ||
						user.getGENDER().length() == 0) {
					JOptionPane.showMessageDialog(null, "값을 입력해주세요.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("값을 입력해주세요.");
					conn.rollback();
				}
				else if(rs > 0) {
					JOptionPane.showMessageDialog(null, "처리되었습니다.");
					System.out.println("처리되었습니다.");
					conn.commit();
				}
				else {
					JOptionPane.showMessageDialog(null, "등록에 실패하였습니다.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("등록에 실패하였습니다.");
					conn.rollback();
				}
			}
			
			if (e.getSource() == btnDelete) {
				
				sql = "delete from DB2021_Users where ID = ? && PASSWORD = ?";
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(sql);
				setUser(user);
				pstmt.setString(1, user.getID());
				pstmt.setString(2, user.getPASSWORD());
				
				System.out.println(user.getID());
				System.out.println(user.getPASSWORD());
				
				int rs = pstmt.executeUpdate();
				
				if (user.getID().length() == 0 || user.getPASSWORD().length() == 0) {
					JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호 값을 입력해주세요.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("아이디 혹은 비밀번호 값을 입력해주세요.");
					conn.rollback();
				}
				else if (rs > 0) {
					JOptionPane.showMessageDialog(null, "삭제에 성공하였습니다.");
					System.out.println("삭제에 성공하였습니다.");
					conn.commit();
				}
				else {
					JOptionPane.showMessageDialog(null, "삭제에 실패하였습니다. 존재하지 않는 데이터입니다.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					conn.rollback();
				}
				
			}
			
			if (e.getSource() == btnCancel) {
				
				this.dispose();
			}
			
			if (e.getSource() == btnUpdate) {
				sql = "update DB2021_users set NAME = ?, GENDER = ? , EMAIL = ? , "
						+ "BIRTHDAY = ? where ID = ? && PASSWORD = ?";
				conn.setAutoCommit(false);
				setUser(user);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user.getNAME());
				pstmt.setString(2, user.getGENDER());
				pstmt.setString(3, user.getEMAIL());
				pstmt.setString(4, user.getBIRTHDAY());
				pstmt.setString(5, user.getID());
				pstmt.setString(6, user.getPASSWORD());
				int rs = pstmt.executeUpdate();
				if (user.getID().length() == 0 || user.getPASSWORD().length() == 0|| 
						user.getNAME().length() == 0 ||
						user.getGENDER().length() == 0) {
					JOptionPane.showMessageDialog(null, "값을 입력해주세요.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("값을 입력해주세요.");
					conn.rollback();
				}
				else if(rs > 0) {
					JOptionPane.showMessageDialog(null, "처리되었습니다.");
					System.out.println("처리되었습니다.");
					conn.commit();
				}
				else {
					JOptionPane.showMessageDialog(null, "갱신에 실패하였습니다.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("갱신에 실패하였습니다.");
					conn.rollback();
				}
			
			}	
			
		}catch(Exception e2) {
			e2.printStackTrace();
			System.out.println("오류가 발생했습니다. : " +e2);
		} finally {
			
			try {
				
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				
			} catch(Exception e3) {
				System.out.println("오류가 발생했습니다. : " + e3);
			}
		}
	}
	
	public static void main(String[] args) {
		
		new DB2021Team01_GUIuser();
		
	}
}
