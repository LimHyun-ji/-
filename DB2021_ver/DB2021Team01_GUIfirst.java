package DB2021Team01;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

public class DB2021Team01_GUIfirst extends JFrame {
	
	static final String AdminID = "DB2021Team01";
	static final String AdminPASSWORD = "DB2021Team01";
	
	//관리자인지 사용자인지 확인하는 창
	JPanel p1 = new JPanel();	//label이 들어가는 패널
	JPanel p2 = new JPanel();	// User, Admin 라디오 버튼이 들어가는 패널
	JPanel p3 = new JPanel();
	JLabel lWho = new JLabel();
	ButtonGroup bg;
	JRadioButton rbU, rbA ;
	JButton jbOK;
	
	public DB2021Team01_GUIfirst() {
		
		newGUI();
		setVisible(true);
		setLocationRelativeTo(null);
		
	}
	public void newGUI() {
		
		setTitle("main");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(3, 1));
		
		bg = new ButtonGroup();
		rbU = new JRadioButton();
		rbA = new JRadioButton();
		bg.add(rbU); bg.add(rbA);

		this.add(p1);
		this.add(p2);
		this.add(p3);
		
		lWho.setText("====선택해주세요.====");
		lWho.setFont(new Font (null, Font.BOLD, 30));
		JLabel l1 = new JLabel("관리자 : ");
		JLabel l2 = new JLabel("             일반 유저 : ");
		l1.setFont(new Font (null, Font.BOLD, 20));
		l2.setFont(new Font (null, Font.BOLD, 20));
		p1.add(lWho);
		p2.add(l1); p2.add(rbA); p2.add(l2); p2.add(rbU);
		 jbOK = new JButton("확인");
		p3.add(jbOK);
		
		setSize(600,300);		//창 크기
		
		jbOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (rbA.isSelected()) {
					new DB2021Team01_AdminLogIn();
				}
				else if (rbU.isSelected()) {
					new DB2021Team01_UserLogIn();
				}
				else {
					JOptionPane.showMessageDialog(null, "값을 선택해주세요.", "ERROR", JOptionPane.ERROR_MESSAGE);			
				}
			}		
		});
	}
	
	public static void main(String[] args) {
		
		new DB2021Team01_GUIfirst();
		
	}
	
}

class DB2021Team01_UserLogIn extends JFrame {
	
	//사용자일 경우 로그인 -> my_lists, reviews에 자신의 데이터만 보인다. my_lists의 작품번호가 작품제목으로 보인다.
	
	JPanel p4 = new JPanel();
	JPanel p5 = new JPanel();
	JPanel p6 = new JPanel();
	JPanel p7 = new JPanel();
	JLabel lUser = new JLabel();
	JTextField tfUID = new JTextField(15);	//id
	JPasswordField pfUPASSWORD = new JPasswordField(30); //입력값이 보이지 않게 처리합니다.
	JButton jbOK2;

	
	DB2021Team01_UserLogIn(){
		
		setTitle("일반 유저 로그인");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(4, 1));
		
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		
		lUser.setText("====로그인하십시오.====");
		lUser.setFont(new Font (null, Font.BOLD, 30));
		JLabel l1 = new JLabel("아이디 : ");
		JLabel l2 = new JLabel("비밀번호 : ");
		l1.setFont(new Font (null, Font.BOLD, 20));
		l2.setFont(new Font (null, Font.BOLD, 20));
		p4.add(lUser);
		p5.add(l1); p5.add(tfUID); 
		p6.add(l2); p6.add(pfUPASSWORD);
		jbOK2 = new JButton("확인");
		p7.add(jbOK2);
		
		this.add(p4);
		this.add(p5);
		this.add(p6);
		this.add(p7);
		
		setSize(500,200);		//창 크기
		jbOK2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				@SuppressWarnings("deprecation")
				String sql = "select id from db2021_users where id ='"+ tfUID.getText() 
				+"'&& password ='"+ pfUPASSWORD.getText()+"';";
				DB2021Team01_DBManager A = new DB2021Team01_DBManager(sql);
				String id = A.temp;
				if (A.temp != "") {
					JOptionPane.showMessageDialog(null, id + "님, 확인되었습니다.");

					DB2021Team01_GUIManager.usermode = 1;		//유저 로그인은 1
					DB2021Team01_GUIManager guiManager=new DB2021Team01_GUIManager();
				}
				else {
					JOptionPane.showMessageDialog(null, "틀린 아이디 혹은 비밀번호입니다.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);			
					
				}
			}
		}

		);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}


class DB2021Team01_AdminLogIn extends JFrame {
	
	//관리자일 경우 로그인 -> 모든 데이터가 보인다.
	
		JPanel p8 = new JPanel();
		JPanel p9 = new JPanel();
		JPanel p10 = new JPanel();
		JPanel p11 = new JPanel();
		JLabel lAdmin = new JLabel();
		JTextField tfAID = new JTextField(15);	//id
		JPasswordField pfAPASSWORD = new JPasswordField(30); //입력값이 보이지 않게 처리합니다.
		JButton jbOK3;
		
		DB2021Team01_AdminLogIn(){
		setTitle("관리자 로그인");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(4, 1));
		
		p8 = new JPanel();
		p9 = new JPanel();
		p10 = new JPanel();
		p11 = new JPanel();
		
		lAdmin.setText("====로그인하십시오.====");
		lAdmin.setFont(new Font (null, Font.BOLD, 30));
		JLabel l1 = new JLabel("아이디 : ");
		JLabel l2 = new JLabel("비밀번호 : ");
		l1.setFont(new Font (null, Font.BOLD, 20));
		l2.setFont(new Font (null, Font.BOLD, 20));
		p8.add(lAdmin);
		p9.add(l1); p9.add(tfAID); 
		p10.add(l2); p10.add(pfAPASSWORD);
		jbOK3 = new JButton("확인");
		p11.add(jbOK3);
		
		this.add(p8);
		this.add(p9);
		this.add(p10);
		this.add(p11);
		
		setSize(500,200);		//창 크기
		
		jbOK3.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				System.out.println(tfAID.getText());
				System.out.println(pfAPASSWORD.getText());
				String ID = tfAID.getText();
				String PASSWORD = pfAPASSWORD.getText();

				//관리자 아이디와 비밀번호는 모두 DB2021Team01
				if (ID.equals(DB2021Team01_GUIfirst.AdminID)
						&& PASSWORD.equals(DB2021Team01_GUIfirst.AdminPASSWORD)) {
					JOptionPane.showMessageDialog(null, "확인되었습니다.");	
					DB2021Team01_GUIManager.usermode = 2;		//관리자 로그인은 2
					DB2021Team01_GUIManager guiManager=new DB2021Team01_GUIManager();
				}
				else {
					JOptionPane.showMessageDialog(null, "틀린 아이디 혹은 비밀번호입니다.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);			
				}
				
			}	
		}
		);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}

