package DB2021Team01;

	import javax.swing.*;
	import javax.swing.border.*;
	import java.awt.*;
	import java.awt.event.*;

	public class DB2021Team01_GUIManager extends JFrame {

		static int usermode = 0;
	
		String sql;
		String orderString;
		DB2021Team01_DBManager dbManager;
		
		JPanel contentPane = new JPanel();
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JButton btnActors = new JButton("Actors");
		JButton btnPerformances = new JButton("Performances");
		JButton btnVenues = new JButton("Venues");
		JButton btnProducers = new JButton("Producers");
		JButton btnSchedules = new JButton("Schedules");
		JButton btnUsers = new JButton("Users");
		JButton btnMy_Lists = new JButton("My_Lists");
		JButton btnReviews = new JButton("Reviews");
		JButton btnSearch = new JButton("검색");//검색
		
		JButton btnAddUser = new JButton("유저 관리");
		JButton btnAddMy_list = new JButton ("즐겨찾기");
		JButton btnAddReview = new JButton ("리뷰 작성");
		
		String searchField;

		JTextArea ta = new JTextArea();
		JTextField tf= new JTextField();

		String[] basicCombo= {"오름차 순","내림차 순"};
		String[] producerCombo= {"제작사 순","분야 순","장르 순"};
		String[] venueCombo= {"장소 순", "좌석 수 순"};
		String[] performanceCombo = {"제목 순","평점 순"};
		String[] searchList={"Actors","Performances", "Venues","Producers", "Users",
				"My_Lists", "Reviews"};
		
		private final JScrollPane scrollPane = new JScrollPane();
		JComboBox order = new JComboBox();
		JComboBox<String> searchBox= new JComboBox(searchList);
		
		public DB2021Team01_GUIManager() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("DB관리 프로그램");
			
			setBounds(100, 100, 800, 480);
			setVisible(true);
			
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			panel.setBounds(0, 0, 140, 406);
			panel2.setBounds(650, 70, 140, 406);
			btnActors.setBounds(17, 70, 105, 29);
			btnPerformances.setBounds(17, 114, 105, 29);
			btnVenues.setBounds(17, 158, 105, 29);
			btnProducers.setBounds(17, 202, 105, 29);
			btnSchedules.setBounds(17, 246, 105, 29);
			btnUsers.setBounds(17, 290, 105, 29);
			btnMy_Lists.setBounds(17, 334, 105, 29);
			btnReviews.setBounds(17, 378, 105, 29);
			
			btnAddUser.setBounds(17, 70, 105, 29);
			btnAddMy_list.setBounds(17, 114, 105, 29);
			btnAddReview.setBounds(17, 158, 105, 29);
			
			searchBox.setBounds(167, 20, 63, 27);
			tf.setBounds(231, 20, 196, 27);
			btnSearch.setBounds(422, 19, 69, 29);
			
			tf.setColumns(10);

			panel.setLayout(null);
			panel.setLayout(null);
			
			MyActionListener myAL= new MyActionListener();
			
			btnActors.addActionListener(myAL);
			btnPerformances.addActionListener(myAL);
			btnVenues.addActionListener(myAL);
			btnProducers.addActionListener(myAL);
			btnSchedules.addActionListener(myAL);
			btnUsers.addActionListener(myAL);
			btnMy_Lists.addActionListener(myAL);
			btnReviews.addActionListener(myAL);
			btnSearch.addActionListener(myAL);
			
			btnAddUser.addActionListener(myAL);
			btnAddMy_list.addActionListener(myAL);
			btnAddReview.addActionListener(myAL);
			
			order.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) {
					
					// TODO Auto-generated method stub
					orderString=order.getSelectedItem().toString();
					OrderQuery(orderString);

					System.out.println(sql);
					dbManager = new DB2021Team01_DBManager(sql);
					ta.setText("");
					ta.append(dbManager.temp);
				}
				
			});
			
			searchBox.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					searchField = searchBox.getSelectedItem().toString();
				}
				
			});
			
			scrollPane.setBounds(167, 62, 479, 309);
			order.setBounds(506, 20, 140, 27);
			
			contentPane.add(scrollPane);
			ta.setBounds(157, 33, 486, 337);
			

			contentPane.add(scrollPane);
			scrollPane.setViewportView(ta);
			contentPane.add(panel);
			contentPane.add(panel2);
			contentPane.add(order);
			
			contentPane.add(searchBox);
			contentPane.add(tf);
			contentPane.add(btnSearch);
			
			panel.add(btnActors);	
			panel.add(btnPerformances);
			panel.add(btnVenues);
			panel.add(btnProducers);
			panel.add(btnSchedules);
			panel.add(btnUsers);
			panel.add(btnMy_Lists);
			panel.add(btnReviews);
			
			panel2.add(btnAddUser);
			panel2.add(btnAddMy_list);
			panel2.add(btnAddReview);		
					
		}
		
		class MyActionListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn =(JButton)e.getSource();
				
				if(btn.getText().equals("Actors")) {
					sql="select * from DB2021_Actors";
					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(basicCombo);
					order.setModel( model );
				}
				
				if(btn.getText().equals("Performances")) {
					if (usermode == 1) {
						sql="select * from db2021_show_performances";			// view 사용해서 아이디 숨김
					}
					else if (usermode == 2) {
						sql="select * from db2021_performances";
					}
					else {
						System.out.println("오류 발생.");
					}
					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(performanceCombo);
					order.setModel( model );

				}
				
				if(btn.getText().equals("Venues")) {
					sql="select * from DB2021_Venues";
					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(venueCombo);
					order.setModel( model );	

					}
				
				if(btn.getText().equals("Producers")) {
					sql="select * from DB2021_Producers";
					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(producerCombo);
					order.setModel( model );

					}
				
				if(btn.getText().equals("Schedules")) {
					sql="select * from DB2021_Schedules";
					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>() ;
					order.setModel( model );

				}
				
				if(btn.getText().equals("Users")) {
					if (usermode == 1) {
						sql="select * from DB2021_show_Users";	//user 정보 중 아이디, 이름, 성별만 노출함	
					}
					else if (usermode == 2) {
						sql="select * from DB2021_Users";
					}
					else {
						System.out.println("오류 발생.");
					}
				}
				
				if(btn.getText().equals("My_Lists")) {
					if (usermode == 2) {
					sql="select * from DB2021_My_Lists";
					}
					else if (usermode == 1) {
						sql = "select m.id, p.performance "
							+ "from db2021_my_lists as m "
							+ "join db2021_performances as p "
							+ "on m.performance = p.id";		//inner join으로 작품 아이디를 작품명으로 변경해서 보여준다
					}
					else {
						System.out.println("오류 발생.");
					}
				}
				
				if(btn.getText().equals("Reviews")) {

					
					if (usermode == 2) {
						sql="select * from DB2021_Reviews";
						}
						else if (usermode == 1) {

							sql = "select r.id, p.performance, r.review "
									+ "from db2021_reviews as r "
									+ "join db2021_performances as p "
									+ "on r.performance = p.id";	
							//inner join으로 작품 아이디를 작품명으로 변경해서 보여준다
						}
						else {
							System.out.println("오류 발생.");
						}
				}
				
				if(btn.getText().equals("유저 관리")) {
					DB2021Team01_GUIuser A = new DB2021Team01_GUIuser();
					A.setVisible(true);
				}
				
				if(btn.getText().equals("즐겨찾기")) {
					DB2021Team01_GUImylist A = new DB2021Team01_GUImylist();
					A.setVisible(true);
				}
				
				if(btn.getText().equals("리뷰 작성")) {
					DB2021Team01_GUIreview A = new DB2021Team01_GUIreview();
					A.setVisible(true);
				}
				
				if(btn.getText().equals("검색")) {
					String searchWord = tf.getText();
					String column="";
					
					switch(searchField) {
					case "Actors":
						column="actor";
						break;
					case "Performances":
						column="Performance";
						break;
					case "Venues":
						column="venue";
						break;
					case "Producers":
						column="producer";	
						break;
					}
					System.out.println(searchField);
					System.out.println(column);
					System.out.println(searchWord);
					sql="select * from " + searchField+ " where "+ column+" like '%" + searchWord+"%'";				
				}
				dbManager = new DB2021Team01_DBManager(sql);
				if(dbManager.errorMessage=="error")
					ta.setText("검색 결과가 없습니다.");
				else{
					ta.setText("");
					ta.append(dbManager.temp);
				}
			}
		}
		public void OrderQuery(String orderString) {
			switch(orderString){
			case "오름차 순":
				sql="select *from DB2021_Actors order by actor asc";
				System.out.println("오름차");
				break;
			case "내림차 순":
				sql="select *from DB2021_Actors order by actor desc";
				System.out.println("내림차");
				break;
			case "제작사 순":
				sql="select *from DB2021_Producers order by producers";
				break;
			case "분야 순":
				sql="select * from DB2021_Producers order by class";
				break;
			case "장르 순":
				sql="select * from DB2021_Producers order by genre";
				break;
			case "장소 순":
				sql="select *from DB2021_Venues order by venue";
				break;
			case "좌석 수 순":
				sql="select * from DB2021_Venues order by seat";
				break;
			case "제목 순":
				sql= "select * from DB2021_Performances order by performance";
				break;
			case "평점 순":
				sql="select * from DB2021_performances order by grade desc" ;
				break;

			}
		}
	}
