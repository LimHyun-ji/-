/* ���� : user ����� �����ϴ� GUI�� ����� ���� class�Դϴ�.
user ���� : ���̵�, ��й�ȣ, �̸�, ����, �̸���, ������Ϸ� �����Ǿ� �ֽ��ϴ�.
���̵�, ��й�ȣ, �̸�, ����, ������ �ݵ�� ���� �޾ƾ� �մϴ�.
�̸����� null�̾ �������ϴ�.
�߰��ؾ� �ϴ� �� (��ü) : �ּ�, join���� Ȱ���� ���, view 2�� �̻�, index 4�� �̻�
�߰��ϰ� ���� �� : �����޼����� â���� ǥ��, �˻����...? */

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
	//���̵�, �̸��� �ϳ��� �Է����� �ް�, �̸����� @�� ���̿� �ΰ� �� ���� �Է°�,
	// ��������� -�� ���̿� �ΰ� 3���� �Է°����� �޽��ϴ�.
	JPasswordField pfPASSWORD;			//�Է°��� ������ �ʰ� ó���մϴ�.
	JRadioButton rbMan, rbWoman, rbN;	//man = 'M', woman = 'W', N = 'N'
	JButton btnInsert, btnCancel, btnUpdate, btnDelete;	
	// ���� ������ �����ϴ� ��ư, ���� ���� ������ ����ϴ� ��ư, ���� ������ �����ϴ� ��ư, ���� ������ �����ϴ� ��ư

	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public DB2021Team01_GUIuser() 
	{

		setTitle("���� ����");	//â �̸�
		newGUI();
		toDB();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);	//���߾ӿ� â �߰� �ϱ�
	}
	
	private void newGUI() {
		
		//�ؽ�Ʈ �ʵ�
		tfID = new JTextField(15);	//id
		tfNAME = new JTextField(15);	//name
		tfEMAIL1 = new JTextField(15);	//email
		tfEMAIL2 = new JTextField(14);
		tfByear = new JTextField(4);		//birthday
		tfBmonth = new JTextField(2);
		tfBdate = new JTextField(2);
		
		//��й�ȣ �ʵ�
		pfPASSWORD = new JPasswordField(20);	//password
		
		//���� ��ư
		ButtonGroup a = new ButtonGroup();
		rbMan = new JRadioButton();			//gender
		rbWoman = new JRadioButton();
		rbN = new JRadioButton();
		a.add(rbMan); a.add(rbWoman); a.add(rbN);
		
		//��ư
		btnInsert = new JButton("����");		//button
		btnDelete = new JButton("����");
		btnUpdate = new JButton("����");
		btnCancel = new JButton("���");
		
		this.setLayout(new GridLayout(6, 1));
		
		p = new JPanel();	
		p1 = new JPanel();	
		p2 = new JPanel();	
		p3 = new JPanel();	
		p4 = new JPanel();	
		p5 = new JPanel();	
		
		this.add(p1); this.add(p2); this.add(p3);
		 this.add(p4); this.add(p5); this.add(p);

		 JLabel l1 = new JLabel("���̵� : ");
		 JLabel l2 = new JLabel("��й�ȣ : ");
		p1.add(l1); p1.add(tfID); p1.add(l2);  p1.add(pfPASSWORD);
		
		 JLabel l3 = new JLabel("�̸� : ");
		 p2.add(l3); p2.add(tfNAME);
		 
		 JLabel l4 = new JLabel("���� : ");
		 JLabel l5 = new JLabel("�� ");
		 JLabel l6 = new JLabel("�� ");
		 JLabel l7 = new JLabel("���Է� ");
		 p3.add(l4);  p3.add(l5); p3.add(rbWoman); 
		 p3.add(l6); p3.add(rbMan);  p3.add(l7); p3.add(rbN);
		 
		 JLabel l8 = new JLabel("�̸��� : ");
		 JLabel l9 = new JLabel("@");
		p4.add(l8); p4.add(tfEMAIL1); p4.add(l9); p4.add(tfEMAIL2);
		
		 JLabel l10 = new JLabel("������� : ");
		 JLabel l11 = new JLabel(" - ");
		 JLabel l12 = new JLabel(" - ");
		 p5.add(l10); p5.add(tfByear); p5.add(l11); 
		 p5.add(tfBmonth); p5.add(l12); p5.add(tfBdate);
		
		p.add(btnInsert);
		p.add(btnDelete);
		p.add(btnUpdate);
		p.add(btnCancel);

		
		//������ ���
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDelete.addActionListener(this);
		
		setSize(600,300);		//â ũ��

	}
	
	private void toDB() {
		try {
			Class.forName(DB2021Team01_DBManager.JDBC_Driver);
			System.out.println("����̹� ���� �Ϸ�");
			System.out.println("-----------------------");
			
		} catch (Exception e) {
			System.out.println("������ �߻��߽��ϴ�. :" + e);
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
			System.out.println("�����ͺ��̽� ���� �Ϸ�");
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
					JOptionPane.showMessageDialog(null, "���� �Է����ּ���.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("���� �Է����ּ���.");
					conn.rollback();
				}
				else if(rs > 0) {
					JOptionPane.showMessageDialog(null, "ó���Ǿ����ϴ�.");
					System.out.println("ó���Ǿ����ϴ�.");
					conn.commit();
				}
				else {
					JOptionPane.showMessageDialog(null, "��Ͽ� �����Ͽ����ϴ�.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("��Ͽ� �����Ͽ����ϴ�.");
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
					JOptionPane.showMessageDialog(null, "���̵� Ȥ�� ��й�ȣ ���� �Է����ּ���.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("���̵� Ȥ�� ��й�ȣ ���� �Է����ּ���.");
					conn.rollback();
				}
				else if (rs > 0) {
					JOptionPane.showMessageDialog(null, "������ �����Ͽ����ϴ�.");
					System.out.println("������ �����Ͽ����ϴ�.");
					conn.commit();
				}
				else {
					JOptionPane.showMessageDialog(null, "������ �����Ͽ����ϴ�. �������� �ʴ� �������Դϴ�.", 
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
					JOptionPane.showMessageDialog(null, "���� �Է����ּ���.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("���� �Է����ּ���.");
					conn.rollback();
				}
				else if(rs > 0) {
					JOptionPane.showMessageDialog(null, "ó���Ǿ����ϴ�.");
					System.out.println("ó���Ǿ����ϴ�.");
					conn.commit();
				}
				else {
					JOptionPane.showMessageDialog(null, "���ſ� �����Ͽ����ϴ�.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("���ſ� �����Ͽ����ϴ�.");
					conn.rollback();
				}
			
			}	
			
		}catch(Exception e2) {
			e2.printStackTrace();
			System.out.println("������ �߻��߽��ϴ�. : " +e2);
		} finally {
			
			try {
				
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				
			} catch(Exception e3) {
				System.out.println("������ �߻��߽��ϴ�. : " + e3);
			}
		}
	}
	
	public static void main(String[] args) {
		
		new DB2021Team01_GUIuser();
		
	}
}
