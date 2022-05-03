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
	
	//���������� ��������� Ȯ���ϴ� â
	JPanel p1 = new JPanel();	//label�� ���� �г�
	JPanel p2 = new JPanel();	// User, Admin ���� ��ư�� ���� �г�
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
		
		lWho.setText("====�������ּ���.====");
		lWho.setFont(new Font (null, Font.BOLD, 30));
		JLabel l1 = new JLabel("������ : ");
		JLabel l2 = new JLabel("             �Ϲ� ���� : ");
		l1.setFont(new Font (null, Font.BOLD, 20));
		l2.setFont(new Font (null, Font.BOLD, 20));
		p1.add(lWho);
		p2.add(l1); p2.add(rbA); p2.add(l2); p2.add(rbU);
		 jbOK = new JButton("Ȯ��");
		p3.add(jbOK);
		
		setSize(600,300);		//â ũ��
		
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
					JOptionPane.showMessageDialog(null, "���� �������ּ���.", "ERROR", JOptionPane.ERROR_MESSAGE);			
				}
			}		
		});
	}
	
	public static void main(String[] args) {
		
		new DB2021Team01_GUIfirst();
		
	}
	
}

class DB2021Team01_UserLogIn extends JFrame {
	
	//������� ��� �α��� -> my_lists, reviews�� �ڽ��� �����͸� ���δ�. my_lists�� ��ǰ��ȣ�� ��ǰ�������� ���δ�.
	
	JPanel p4 = new JPanel();
	JPanel p5 = new JPanel();
	JPanel p6 = new JPanel();
	JPanel p7 = new JPanel();
	JLabel lUser = new JLabel();
	JTextField tfUID = new JTextField(15);	//id
	JPasswordField pfUPASSWORD = new JPasswordField(30); //�Է°��� ������ �ʰ� ó���մϴ�.
	JButton jbOK2;

	
	DB2021Team01_UserLogIn(){
		
		setTitle("�Ϲ� ���� �α���");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(4, 1));
		
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		
		lUser.setText("====�α����Ͻʽÿ�.====");
		lUser.setFont(new Font (null, Font.BOLD, 30));
		JLabel l1 = new JLabel("���̵� : ");
		JLabel l2 = new JLabel("��й�ȣ : ");
		l1.setFont(new Font (null, Font.BOLD, 20));
		l2.setFont(new Font (null, Font.BOLD, 20));
		p4.add(lUser);
		p5.add(l1); p5.add(tfUID); 
		p6.add(l2); p6.add(pfUPASSWORD);
		jbOK2 = new JButton("Ȯ��");
		p7.add(jbOK2);
		
		this.add(p4);
		this.add(p5);
		this.add(p6);
		this.add(p7);
		
		setSize(500,200);		//â ũ��
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
					JOptionPane.showMessageDialog(null, id + "��, Ȯ�εǾ����ϴ�.");

					DB2021Team01_GUIManager.usermode = 1;		//���� �α����� 1
					DB2021Team01_GUIManager guiManager=new DB2021Team01_GUIManager();
				}
				else {
					JOptionPane.showMessageDialog(null, "Ʋ�� ���̵� Ȥ�� ��й�ȣ�Դϴ�.", 
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
	
	//�������� ��� �α��� -> ��� �����Ͱ� ���δ�.
	
		JPanel p8 = new JPanel();
		JPanel p9 = new JPanel();
		JPanel p10 = new JPanel();
		JPanel p11 = new JPanel();
		JLabel lAdmin = new JLabel();
		JTextField tfAID = new JTextField(15);	//id
		JPasswordField pfAPASSWORD = new JPasswordField(30); //�Է°��� ������ �ʰ� ó���մϴ�.
		JButton jbOK3;
		
		DB2021Team01_AdminLogIn(){
		setTitle("������ �α���");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(4, 1));
		
		p8 = new JPanel();
		p9 = new JPanel();
		p10 = new JPanel();
		p11 = new JPanel();
		
		lAdmin.setText("====�α����Ͻʽÿ�.====");
		lAdmin.setFont(new Font (null, Font.BOLD, 30));
		JLabel l1 = new JLabel("���̵� : ");
		JLabel l2 = new JLabel("��й�ȣ : ");
		l1.setFont(new Font (null, Font.BOLD, 20));
		l2.setFont(new Font (null, Font.BOLD, 20));
		p8.add(lAdmin);
		p9.add(l1); p9.add(tfAID); 
		p10.add(l2); p10.add(pfAPASSWORD);
		jbOK3 = new JButton("Ȯ��");
		p11.add(jbOK3);
		
		this.add(p8);
		this.add(p9);
		this.add(p10);
		this.add(p11);
		
		setSize(500,200);		//â ũ��
		
		jbOK3.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				System.out.println(tfAID.getText());
				System.out.println(pfAPASSWORD.getText());
				String ID = tfAID.getText();
				String PASSWORD = pfAPASSWORD.getText();

				//������ ���̵�� ��й�ȣ�� ��� DB2021Team01
				if (ID.equals(DB2021Team01_GUIfirst.AdminID)
						&& PASSWORD.equals(DB2021Team01_GUIfirst.AdminPASSWORD)) {
					JOptionPane.showMessageDialog(null, "Ȯ�εǾ����ϴ�.");	
					DB2021Team01_GUIManager.usermode = 2;		//������ �α����� 2
					DB2021Team01_GUIManager guiManager=new DB2021Team01_GUIManager();
				}
				else {
					JOptionPane.showMessageDialog(null, "Ʋ�� ���̵� Ȥ�� ��й�ȣ�Դϴ�.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);			
				}
				
			}	
		}
		);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}

