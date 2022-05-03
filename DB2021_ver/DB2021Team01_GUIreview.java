/* ���� : review ����� �����ϴ� GUI�� ����� ���� class�Դϴ�.
review ���� : user id, ��ǰ ��ȣ, ����� �����Ǿ� �ֽ��ϴ�.
��� ���� null�̾ �ȵ˴ϴ�. */


package DB2021Team01;

import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class DB2021Team01_GUIreview extends JFrame implements ActionListener {

	JPanel p, p1, p2, p3, p4;
	// button | id | password | review | performance
	JTextField tfID, tfREVIEW;
	JPasswordField pfPASSWORD;	
	JButton btnInsert, btnCancel, btnUpdate, btnDelete;	
	JComboBox<String> combo;
	ArrayList<String> performance = new ArrayList<String>();
	//��ǰ�� ������ ����Ʈ
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public DB2021Team01_GUIreview() {
		
		setTitle("���� ����");	//â �̸�
		newGUI();
		toDB();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);	//���߾ӿ� â �߰� �ϱ�
		
	}
	
private void newGUI() {	

	
	performance();	

	combo = new JComboBox<String>(performance.toArray(new String[performance.size()]));

	combo.setPreferredSize(new Dimension(150,20));	//�޺� �ڽ� ũ��
	
		//�ؽ�Ʈ �ʵ�
		tfID = new JTextField(15);	//id
		tfREVIEW = new JTextField(50);	//id
		
		//��й�ȣ �ʵ�
		pfPASSWORD = new JPasswordField(20);	//password
				
		//��ư
		btnInsert = new JButton("����");		//button
		btnDelete = new JButton("����");
		btnUpdate = new JButton("����");
		btnCancel = new JButton("���");
		
		this.setLayout(new GridLayout(5,1));
		
		p = new JPanel();	
		p1 = new JPanel();	
		p2 = new JPanel();		
		p3 = new JPanel();	
		p4 = new JPanel();	
		
		this.add(p1); this.add(p2); this.add(p4); this.add(p3); this.add(p);

		 JLabel l1 = new JLabel("���̵� : ");
		p1.add(l1); p1.add(tfID);
		
		
		 JLabel l2 = new JLabel("��й�ȣ (����/����) : ");
		p2.add(l2); p2.add(pfPASSWORD);
		 
		 JLabel l3 = new JLabel("���� (100�� �̳�) : ");
		 p3.add(l3); p3.add(tfREVIEW);
		 
		 JLabel l4 = new JLabel("��ǰ�� : ");
		 p4.add(l4); p4.add(combo);
		
		p.add(btnInsert);
		p.add(btnDelete);
		p.add(btnUpdate);
		p.add(btnCancel);

		
		//������ ���
		btnInsert.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDelete.addActionListener(this);
		btnUpdate.addActionListener(this);
		
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
			System.out.println("������ �߻��߽��ϴ�. : " +e2);
		} 	
	}

	private void setReview(DB2021Team01_reviewGS R) {
		
		R.setID(tfID.getText());
		R.setREVIEW(tfREVIEW.getText());
		
		String a = combo.getSelectedItem().toString();
		System.out.println("������ ��ǰ : " + a);
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
		R.setPERFORMANCE(rs.getString(1));
		
		}catch(Exception e2) {
			e2.printStackTrace();
			System.out.println("������ �߻��߽��ϴ�. : " +e2);
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
			System.out.println("�����ͺ��̽� ���� �Ϸ�");
			System.out.println("-----------------------");
			DB2021Team01_reviewGS review = new DB2021Team01_reviewGS();
			DB2021Team01_userGS user = new DB2021Team01_userGS();
			
			String sql = "";
			
			if (e.getSource() == btnInsert) {
				
				conn.setAutoCommit(false);
				sql = "insert into DB2021_reviews values(?,?,?)";
				pstmt = conn.prepareStatement(sql);
				setReview(review);
				
				System.out.println(review.getID());
				System.out.println(review.getPERFORMANCE());
				System.out.println(review.getREVIEW());
				
				pstmt.setString(1,  review.getID());
				pstmt.setString(2,  review.getPERFORMANCE());
				pstmt.setString(3,  review.getREVIEW());

				int rs = pstmt.executeUpdate();
				if (review.getID().length() == 0 || review.getREVIEW().length() == 0 ) {
					JOptionPane.showMessageDialog(null, "���̵� Ȥ�� ���並 �Է����ּ���.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("���̵� Ȥ�� ���並 �Է����ּ���.");
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
				
				
				sql = "Delete from DB2021_reviews as R where ID = ? && PERFORMANCE = ? "
						+ "&& ID IN (SELECT ID FROM DB2021_users AS U "
						+ "WHERE R.ID = U.ID && U.PASSWORD = ?)";
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(sql);
				setReview(review);
				setUser(user);
				
				pstmt.setString(1, review.getID());
				pstmt.setString(2, review.getPERFORMANCE());
				pstmt.setString(3, user.getPASSWORD());
				
				System.out.println(review.getID());
				System.out.println(review.getPERFORMANCE());
				System.out.println(user.getPASSWORD());
				
				int rs = pstmt.executeUpdate();
				
				if (review.getID().length() == 0 || user.getPASSWORD().length() == 0) {
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
					JOptionPane.showMessageDialog(null, "������ �����Ͽ����ϴ�. �������� �ʴ� ������ "
							+ "Ȥ�� Ʋ�� ��й�ȣ�Դϴ�.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);		
					conn.rollback();
				
			}
			}
			
			if (e.getSource() == btnCancel) {
				
				this.dispose();
			}
			
			if (e.getSource() == btnUpdate) {
				sql = "UPDATE DB2021_REVIEWS AS R SET REVIEW = ? WHERE ID = ?"
						+ "&& PERFORMANCE = ? && ID IN (select ID from DB2021_users as U where U.ID = R.ID"
						+ "&& U.PASSWORD = ? ) ";

				conn.setAutoCommit(false);
				setReview(review);
				setUser(user);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, review.getREVIEW());
				pstmt.setString(2, review.getID());
				pstmt.setString(3, review.getPERFORMANCE());
				pstmt.setString(4, user.getPASSWORD());
	
				int rs = pstmt.executeUpdate();

				if (review.getID().length() == 0 || user.getPASSWORD().length() == 0
						|| review.getREVIEW().length() == 0 ) {
					JOptionPane.showMessageDialog(null, "���̵� Ȥ�� ��й�ȣ Ȥ�� ���並 �Է����ּ���.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("���̵� Ȥ�� ��й�ȣ Ȥ�� ���並 �Է����ּ���.");
					conn.rollback();
				}
				if (rs > 0) {
					JOptionPane.showMessageDialog(null, "���� �Ϸ��߽��ϴ�.");
					System.out.println("���� �Ϸ��߽��ϴ�.");
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
		
		new DB2021Team01_GUIreview();
		
	}	
}
