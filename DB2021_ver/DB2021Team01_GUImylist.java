/* 설명 : mylist 등록을 관리하는 GUI를 만들기 위한 class입니다.
mylist 정보 : user id, 작품 번호로 구성되어 있습니다.
모든 값은 null이어선 안됩니다. */

package DB2021Team01;

import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class DB2021Team01_GUImylist extends JFrame implements ActionListener {
	JPanel p, p1, p2, p3;
	// button | id | performance | password
	JTextField tfID;
	JButton btnInsert, btnCancel, btnDelete;	
	JPasswordField pfPASSWORD;	//삭제 시 받는 값
	// 즐겨찾기를 추가하는 버튼, 관리를 취소하는 버튼, 즐겨찾기를 삭제하는 버튼. 즐겨찾기에서는 갱신을 사용하지 않습니다.
	JComboBox<String> combo;
	ArrayList<String> performance = new ArrayList<String>();
	//작품을 저장할 리스트

	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public DB2021Team01_GUImylist() 
	{

		setTitle("즐겨찾기 관리");	//창 이름
		newGUI();
		toDB();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);	//정중앙에 창 뜨게 하기
	}
	
	private void newGUI() {
		
		performance();	

		combo = new JComboBox<String>(performance.toArray(new String[performance.size()]));

		combo.setPreferredSize(new Dimension(150,20));	//콤보 박스 크기
		//텍스트 필드
		tfID = new JTextField(15);	//id
		
		//비밀번호 필드
		pfPASSWORD = new JPasswordField(20);	//password
				
		//버튼
		btnInsert = new JButton("삽입");		//button
		btnDelete = new JButton("삭제");
		btnCancel = new JButton("취소");
		
		this.setLayout(new GridLayout(4,1));
		
		p = new JPanel();	
		p1 = new JPanel();	
		p2 = new JPanel();		
		p3 = new JPanel();	
		
		this.add(p1); this.add(p2); this.add(p3);  this.add(p);

		 JLabel l1 = new JLabel("아이디 : ");
		p1.add(l1); p1.add(tfID);
		
		 JLabel l2 = new JLabel("작품명 : ");
		 p2.add(l2); p2.add(combo);
		 
		 JLabel l3 = new JLabel("삭제 시 비밀번호를 입력 : ");
		 p3.add(l3); p3.add(pfPASSWORD);
		
		p.add(btnInsert);
		p.add(btnDelete);
		p.add(btnCancel);

		
		//리스너 등록
		btnInsert.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDelete.addActionListener(this);
		
		setSize(600,300);		//창 크기

	}
	
	private void performance() {
		
		toDB();
		try {
			PreparedStatement pstmt;
			ResultSet rs;
			
		Connection conn = DriverManager.getConnection(DB2021Team01_DBManager.DB_URL, 
				DB2021Team01_DBManager.userName, DB2021Team01_DBManager.password);

		String sql = "select performance from DB2021_performances";
		
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int c = 0;
		while (rs.next()) {
			performance.add(rs.getString(1));
			rs.next();
			c++;
		}
		
		}catch(Exception e2) {
			e2.printStackTrace();
			System.out.println("오류가 발생했습니다. : " +e2);
		} 	
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

	private void setMyList(DB2021Team01_myListGS L) {
		
		L.setID(tfID.getText());
		
		String a = combo.getSelectedItem().toString();
		System.out.println("선택한 작품 : " + a);
		toDB();
		
		try {
			PreparedStatement pstmt;
			ResultSet rs;
			
		Connection conn = DriverManager.getConnection(DB2021Team01_DBManager.DB_URL, 
				DB2021Team01_DBManager.userName, DB2021Team01_DBManager.password);
		String sql = "select id from DB2021_performances where performance = ?";

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, a);
		rs = pstmt.executeQuery();

		rs.next();
		System.out.println(rs.getString(1));
		L.setPERFORMANCE(rs.getString(1));
		
		}catch(Exception e2) {
			e2.printStackTrace();
			System.out.println("오류가 발생했습니다. : " +e2);
		} 

	}
	
	@SuppressWarnings("deprecation")
	private void setUser(DB2021Team01_userGS U) {
		U.setPASSWORD(pfPASSWORD.getText());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			
			conn = DriverManager.getConnection(DB2021Team01_DBManager.DB_URL, 
					DB2021Team01_DBManager.userName, DB2021Team01_DBManager.password);
			System.out.println("데이터베이스 연결 완료");
			System.out.println("-----------------------");
			DB2021Team01_myListGS list = new DB2021Team01_myListGS();
			DB2021Team01_userGS user = new DB2021Team01_userGS();
			
			String sql = "";
			
			if (e.getSource() == btnInsert) {
				
				conn.setAutoCommit(false);
				sql = "insert into DB2021_my_lists values(?,?)";
				pstmt = conn.prepareStatement(sql);
				setMyList(list);
				
				System.out.println(list.getID());
				System.out.println(list.getPERFORMANCE());
				
				pstmt.setString(1,  list.getID());
				pstmt.setString(2,  list.getPERFORMANCE());

				int rs = pstmt.executeUpdate();
				if (list.getID().length() == 0) {
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
				
				sql = "Delete from DB2021_my_lists as L WHERE ID = ? &&PERFORMANCE = ?"
						+ "&& ID IN (SELECT ID FROM DB2021_usersUSERS AS U WHERE L.ID = U.ID"
						+ "&& U.PASSWORD = ? )";
				
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(sql);
				setMyList(list);
				setUser(user);
				pstmt.setString(1, list.getID());
				pstmt.setString(2, list.getPERFORMANCE());
				pstmt.setString(3, user.getPASSWORD());
				
				System.out.println(list.getID());
				System.out.println(list.getPERFORMANCE());
				System.out.println(user.getPASSWORD());
				
				int rs = pstmt.executeUpdate();
				if (list.getID().length() == 0 || user.getPASSWORD().length() == 0) {
					JOptionPane.showMessageDialog(null, "값을 입력해주세요.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("값을 입력해주세요.");
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
					System.out.println("삭제에 실패하였습니다. 존재하지 않는 데이터입니다.");
					conn.rollback();
				}
			}
			
			if (e.getSource() == btnCancel) {
				
				this.dispose();
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
		
		new DB2021Team01_GUImylist();
		
	}
}
