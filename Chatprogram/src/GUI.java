import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame implements ActionListener, Runnable {

	private JFrame Firstframe=new JFrame();//로그인, 회원가입, 비밀번호/학번 찾기를 위한 초기 화면
	private JFrame Joinframe=new JFrame();//회원가입을 위한 화면
	private JFrame roomframe=new JFrame();//방을 생성할지 방에 참가할지 고르는 화면
	private JFrame Search_frame = new JFrame();//비밀번호를 찾을 때 사용하는 화면
	private JFrame Search_frame2 = new JFrame();//학번을 찾을 때 사용하는 화면
	
	private JTextField textField;
	private JTextField ID_textField;

	private JTextField Name_textField2;
	private JTextField search_ID_field;
	private JTextField search_name_field;
	private JTextField search_name_textfield2;
	
	private JPasswordField ID_check_PWField;
	private JPasswordField PWField;
	private JPasswordField CreatePWField;
	
	private JButton CancleButton;
	private JButton join_button;
	private JButton login_button;
	private JButton Check_Id_Button;
	private JButton join_Button2;
	private JButton makeButton;
	private JButton room_join_Button;
	private JButton search_button;
	private JButton search_ID_button;
	private JButton search_name_button2;
	private JButton search_pw_button;
	
	private boolean check=false;
	PreparedStatement ps=null;
	ResultSet rs=null;
	static Connection con=null;
	String sql;
	boolean check_ID=false;
	BufferedImage img=null;
	
	class myPanel extends JPanel{
		public void paint(Graphics g)
		{
			g.drawImage(img,  0,  0, null);//배경 이미지를 그리기 위한 paint메소드
		}
	}
	public static void main(String[] args) {
		GUI g=new GUI();//main문에서 생성자 호출
	}
	public GUI() {
		First_frame();//생성자에서 첫 로그인 화면 실행
	}

	public void First_frame() {
		JLayeredPane layeredPane=new JLayeredPane();
		layeredPane.setSize(450,360);
		layeredPane.setBounds(0,0,450,360);
		
		try {
			img=ImageIO.read(new File("C:\\Users\\ParkJY\\eclipse-workspace\\Chatprogram\\src\\sch.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(0);
		}
		myPanel panel=new myPanel();
		panel.setSize(450,360);
		layeredPane.add(panel);//배경 이미지를 불러와 사이즈를 정하고 Panel에 추가
		
		Firstframe.setBounds(100, 100, 450, 360);//크기 설정
		Firstframe.setTitle("Login");//제목 설정
		Firstframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//창을 정상적으로 닫음
		
		textField = new JTextField();
		textField.setBounds(125, 47, 196, 32);
		Firstframe.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("학번 : ");
		lblNewLabel.setBounds(77, 55, 36, 15);
		Firstframe.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("비밀번호 : ");
		lblNewLabel_1.setBounds(56, 107, 57, 15);
		Firstframe.getContentPane().add(lblNewLabel_1);
		
		PWField = new JPasswordField();
		PWField.setBounds(125, 100, 196, 30);
		Firstframe.getContentPane().add(PWField);
		PWField.setColumns(10);
		
		join_button = new JButton("회원 가입");
		join_button.setBounds(99, 180, 97, 23);
		Firstframe.getContentPane().add(join_button);
		join_button.addActionListener(this);
		
		login_button = new JButton("로그인");
		login_button.setBounds(224, 180, 97, 23);
		Firstframe.getContentPane().add(login_button);
		login_button.addActionListener(this);
		
		search_button = new JButton("학번/비밀번호 찾기");
		search_button.setBounds(99, 212, 222, 23);
		Firstframe.add(search_button);
		search_button.addActionListener(this);
		
		Firstframe.add(layeredPane);
		
		Firstframe.setVisible(true);
		Thread t = new Thread(this);//화면을 스레드로 각각 실행
        t.start();
	}
	
	public void Join_frame()
	{
		JLayeredPane layeredPane=new JLayeredPane();
		layeredPane.setSize(450,300);
		layeredPane.setBounds(0,0,450,300);
		
		try {
			img=ImageIO.read(new File("C:\\Users\\ParkJY\\eclipse-workspace\\Chatprogram\\src\\sch4.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(0);
		}
		myPanel panel=new myPanel();
		panel.setSize(450,300);
		panel.setLayout(new FlowLayout());
		layeredPane.add(panel);
		
		 Joinframe.setTitle("Join Membership");
		 Joinframe.setBounds(100, 100, 450, 300);
	     Joinframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 Joinframe.getContentPane().setLayout(null);
		 
			
		 JLabel Create_ID_Label = new JLabel("학번 : ");
		 Create_ID_Label.setBounds(93, 50, 36, 15);
		 Joinframe.getContentPane().add(Create_ID_Label);
			
		 JLabel Create_PW_Label = new JLabel("비밀번호 : ");
		 Create_PW_Label.setBounds(67, 91, 69, 15);
		 Joinframe.getContentPane().add(Create_PW_Label);
			
		 JLabel lblNewLabel_2 = new JLabel("이름 : ");
		 lblNewLabel_2.setBounds(92, 171, 36, 15);
		 Joinframe.getContentPane().add(lblNewLabel_2);
			
		 CreatePWField = new JPasswordField();
		 CreatePWField.setBounds(148, 88, 140, 21);
		 Joinframe.getContentPane().add(CreatePWField);
			
		 ID_textField = new JTextField();
		 ID_textField.setBounds(148, 47, 140, 21);
		 Joinframe.getContentPane().add(ID_textField);
		 ID_textField.setColumns(10);
		 
			
		 Name_textField2 = new JTextField();
		 Name_textField2.setBounds(148, 168, 140, 21);
		 Joinframe.getContentPane().add(Name_textField2);
		 Name_textField2.setColumns(10);
			
		 join_Button2=new JButton("가입");
		 join_Button2.setBounds(93, 210, 97, 23);
		 Joinframe.getContentPane().add(join_Button2);
		 join_Button2.addActionListener(this);
			
		 CancleButton = new JButton("취소");
		 CancleButton.setBounds(247, 210, 97, 23);
		 Joinframe.getContentPane().add(CancleButton);
		 CancleButton.addActionListener(this);
			
		 Check_Id_Button = new JButton("학번 check");
		 Check_Id_Button.setBounds(312, 46, 97, 23);
		 Joinframe.getContentPane().add(Check_Id_Button);
		 Check_Id_Button.addActionListener(this);
		 
		 JLabel Check_Label = new JLabel("비밀번호 재확인 : ");
		 Check_Label.setBounds(27, 130, 112, 15);
		 Joinframe.getContentPane().add(Check_Label);
		 
		 ID_check_PWField = new JPasswordField();
		 ID_check_PWField.setBounds(148, 127, 140, 21);
		 Joinframe.getContentPane().add(ID_check_PWField);
		 ID_check_PWField.setColumns(10);
		 ID_check_PWField.addActionListener(this);
		 
		 Joinframe.add(layeredPane);
		 
		 Joinframe.setVisible(true);
		 Thread t = new Thread(this);
	     t.start();
	}
	public void Room_frame()
	{
		JLayeredPane layeredPane=new JLayeredPane();
		layeredPane.setSize(450,300);
		layeredPane.setBounds(0,0,450,300);
		
		try {
			img=ImageIO.read(new File("C:\\Users\\ParkJY\\eclipse-workspace\\Chatprogram\\src\\sch.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(0);
		}
		myPanel panel=new myPanel();
		panel.setSize(450,300);
		layeredPane.add(panel);
		
		roomframe.setBounds(100, 100, 450, 300);
	    roomframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		roomframe.getContentPane().setLayout(null);
		
		
		makeButton = new JButton("방 생성");
		makeButton.setBounds(44, 105, 149, 59);
		roomframe.getContentPane().add(makeButton);
		makeButton.addActionListener(this);
		
		room_join_Button = new JButton("방 참가");
		room_join_Button.setBounds(243, 105, 149, 59);
		roomframe.getContentPane().add(room_join_Button);
		room_join_Button.addActionListener(this);
		
		roomframe.add(layeredPane);
		
		roomframe.setVisible(true);
		Thread t = new Thread(this);
		t.start();
	}
	
	public void Search_frame()
	{
		JLayeredPane layeredPane=new JLayeredPane();
		layeredPane.setSize(355, 278);
		layeredPane.setBounds(0,0,355, 278);
		
		try {
			img=ImageIO.read(new File("C:\\Users\\ParkJY\\eclipse-workspace\\Chatprogram\\src\\sch2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(0);
		}
		myPanel panel=new myPanel();
		panel.setSize(355, 278);
		layeredPane.add(panel);
		
		Search_frame.setBounds(100, 100, 355, 278);
	    Search_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Search_frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("학번 : ");
		lblNewLabel.setBounds(70, 65, 57, 15);
		Search_frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("이름 : ");
		lblNewLabel_1.setBounds(70, 120, 57, 15);
		Search_frame.getContentPane().add(lblNewLabel_1);
		
		search_ID_field = new JTextField();
		search_ID_field.setBounds(139, 62, 116, 21);
		Search_frame.getContentPane().add(search_ID_field);
		search_ID_field.setColumns(10);
		
		search_name_field = new JTextField();
		search_name_field.setBounds(139, 117, 116, 21);
		Search_frame.getContentPane().add(search_name_field);
		search_name_field.setColumns(10);
		
		search_ID_button = new JButton("학번 찾기");
		search_ID_button.setBounds(70, 163, 97, 23);
		Search_frame.getContentPane().add(search_ID_button);
		search_ID_button.addActionListener(this);
		
		search_pw_button = new JButton("찾기");
		search_pw_button.setBounds(179, 163, 97, 23);
		Search_frame.getContentPane().add(search_pw_button);
		search_pw_button.addActionListener(this);
		
		Search_frame.add(layeredPane);
		
		Search_frame.setVisible(true);
		Thread t = new Thread(this);
		t.start();
	}
	
	public void Search_ID_frame()
	{
		JLayeredPane layeredPane=new JLayeredPane();
		layeredPane.setSize(309,211);
		layeredPane.setBounds(0,0,309,211);
		
		try {
			img=ImageIO.read(new File("C:\\Users\\ParkJY\\eclipse-workspace\\Chatprogram\\src\\sch3.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(0);
		}
		myPanel panel=new myPanel();
		panel.setSize(309,211);
		layeredPane.add(panel);
		
		Search_frame2.setBounds(100, 100, 309, 211);
	    Search_frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Search_frame2.getContentPane().setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("이름 : ");
		lblNewLabel.setBounds(50, 73, 57, 15);
		Search_frame2.getContentPane().add(lblNewLabel);
		
		search_name_textfield2 = new JTextField();
		search_name_textfield2.setBounds(90, 70, 116, 21);
		Search_frame2.getContentPane().add(search_name_textfield2);
		search_name_textfield2.setColumns(10);
		
		search_name_button2 = new JButton("찾기");
		search_name_button2.setBounds(90, 101, 97, 23);
		Search_frame2.getContentPane().add(search_name_button2);
		search_name_button2.addActionListener(this);
		
		Search_frame2.add(layeredPane);
		
		Search_frame2.setVisible(true);
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	
	public static Connection makeConnection()
	{
		 String url = "jdbc:mysql://localhost:3306/chat_db?autoReconnect=true&useSSL=false";//DB 서버가 놓여져 있는 위치
         Connection con=null;
		 try {
			Class.forName("com.mysql.cj.jdbc.Driver");//드라이버를 적재
	        con = DriverManager.getConnection(url,"root","jyjyjy0698!");//DB서버와 연결
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
         return con;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {//버튼을 클릭했을 때 나오는 action을 처리
		if(e.getSource()==join_button)
		{
			Join_frame();//회원가입을 위한 화면으로 이동
		}
		else if(e.getSource()==login_button)//로그인 버튼
		{
			String tf=textField.getText();
			String pw=new String(PWField.getPassword());//학번과 비밀번호를 입력한 것을 변수에 저장
			sql = String.format("select id, password from chats where id = '%s' and password ='%s'",
					tf, pw);//db에서 학번과 비밀번호가 일치하는 곳을 참조
			
			try {
				Connection con=makeConnection();//DB와 연결
				Statement stmt = con.prepareStatement(sql);//statement를 만듦
				ResultSet rs=stmt.executeQuery(sql);//statement를 실행하여 resultset이 넘어옴
				rs.next();//다음 레코드를 참조
				if(tf.equals(rs.getString(1)) && pw.equals(rs.getString(2)))
				{
					Room_frame();//비밀번호와 학번이 db에 있는 내용과 일치할 경우 방을 선택하는 frame으로 이동
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}			
		}
		else if(e.getSource()==makeButton)//방 만들기 버튼
		{
			SwingUtilities.invokeLater(new Runnable() {//스레드를 각각 실행할 때 스윙 이벤트 충돌을 막음
			    public void run() {
			    	try {
			    		String data2=textField.getText();
						MessengerA messengerA=new MessengerA(data2);//로그인 할 때 학번을 messengerA(서버 역할)에 보냄
					} catch (IOException e) {
						e.printStackTrace();
					} 
			    }
			});
		}
		
		
		else if(e.getSource()==room_join_Button)//방 참여 버튼
		{
			SwingUtilities.invokeLater(new Runnable() {
		        public void run() {
		        	String data=textField.getText();
		        	MessengerB messengerB=new MessengerB(data);//로그인 할 때 학번을 messengerB(클라이언트 역할)에 보냄
		        }
		    });
		}
		
		
		else if(e.getSource()==search_button)//비밀번호, 학번 찾기 버튼
		{
			Search_frame();
		}
		else if(e.getSource()==search_ID_button)//학번 찾기 버튼
		{
			Search_ID_frame();
		}
		else if(e.getSource()==search_pw_button)//비밀번호 찾기 버튼
		{
			sql = String.format("select id, name from chats where id = '%s' and name ='%s'",
					search_ID_field.getText(), search_name_field.getText());
			//DB에서 입력한 학번과 이름이 일치하는 곳을 참조
			String sql2= String.format("select password from chats where id = '%s' and name ='%s'",
					search_ID_field.getText(), search_name_field.getText());
			//비밀번호를 찾기 위해 명령문을 하나 더 사용
			try {
				Connection con=makeConnection();
				PreparedStatement stmt=con.prepareStatement(sql);
				ResultSet rs=stmt.executeQuery(sql);
				rs.next();
				Connection con2=makeConnection();
				PreparedStatement stmt2=con2.prepareStatement(sql2);
				ResultSet rs2=stmt2.executeQuery(sql2);
				rs2.next();
				
				if(search_ID_field.getText().equals(rs.getString("id")) && search_name_field.getText().equals(rs.getString("name")))
				{//DB에서 입력한 학번과 이름이 일치하는 곳의 비밀번호를 가져옴
					JOptionPane.showMessageDialog(null, "콘솔 창 확인", "콘솔 창 확인", 1);
					System.out.println(rs2.getString(1));
				}
			}
			catch(SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==search_name_button2)//학번 찾기하는 버튼
		{
			sql = String.format("select name from chats where name ='%s'",
					search_name_textfield2.getText());
			String sql2= String.format("select id from chats where name ='%s'",
					search_name_textfield2.getText());
			try {
				Connection con=makeConnection();
				PreparedStatement stmt=con.prepareStatement(sql);
				ResultSet rs=stmt.executeQuery(sql);//입력한 이름을 db에서 검색
				rs.next();
				Connection con2=makeConnection();
				PreparedStatement stmt2=con2.prepareStatement(sql2);
				ResultSet rs2=stmt2.executeQuery(sql2);
				rs2.next();
				
				if(search_name_textfield2.getText().equals(rs.getString("name")))//DB에 있는 이름이 입력한 이름과 일치하면 학번 출력 
				{
					JOptionPane.showMessageDialog(null, "콘솔 창 확인", "콘솔 창 확인", 1);
					System.out.println(rs2.getString(1));
				}
			}
			catch(SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
		else if(e.getSource()==join_Button2)//회원가입하는 버튼
		{
			String pw = new String(CreatePWField.getPassword());
			String pwcheck = new String(ID_check_PWField.getPassword());
			Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
			//8자 이상 20자 이하 영문과 숫자, 특수문자를 입력한 비밀번호를 사용해야 함
			Matcher passMatcher = passPattern1.matcher(new String(CreatePWField.getPassword()));
			if(check_ID==false)
			{
				JOptionPane.showMessageDialog(null, "학번 check 필요", "학번 오류", 1);
			}
			else if (!passMatcher.find()) {
				JOptionPane.showMessageDialog(null, "비밀번호는 영문과 특수문자, 숫자 8자 이상 20자 이하로 구성되어야 합니다", "비밀번호 오류", 1);
			}//비밀번호가 규칙에 맞지 않을 경우 오류
			else if (!pw.equals(pwcheck)) {
				JOptionPane.showMessageDialog(null, "비밀번호가 서로 맞지 않습니다", "비밀번호 오류", 1);
			}//입력한 비밀번호와 비밀번호 재확인이 일치하지 않을 경우 오류
			else
			{				
				 sql = "insert into chats(id, password, name) values (?,?,?)";
				try {
					Connection con=makeConnection();
					PreparedStatement stmt=con.prepareStatement(sql);
					
					stmt.setString(1, ID_textField.getText());
					stmt.setString(2, pw);
					stmt.setString(3, Name_textField2.getText());
					int n=stmt.executeUpdate();//입력한 학번, 비밀번호, 이름을 DB에 입력
					if(n>0 && check_ID==true) {//성공적으로 입력이 됐고 학번 중복 체크를 했을 경우 회원가입 성공
						JOptionPane.showMessageDialog(null, "회원가입 성공", "회원가입 성공", 1);
						Joinframe.dispose();
					}
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		else if(e.getSource()==CancleButton)//비밀번호 찾기 닫기 버튼
		{
			Joinframe.dispose();
		}
		else if(e.getSource()==Check_Id_Button)//학번 check
		{
			String tf=ID_textField.getText();
			sql = String.format("select * from chats");
			ArrayList <String> ID_str=new ArrayList<>();
			
			try {
				Connection con=makeConnection();
				Statement stmt = con.prepareStatement(sql);
				
				ResultSet rs=stmt.executeQuery(sql);
				while(rs.next())
				{
					ID_str.add(rs.getString(1));//존재하는 학번들을 arraylist에 넣음
				}
				for(String st:ID_str)
				{
					if(ID_str.contains(tf)==true)
					{
						JOptionPane.showMessageDialog(null, "학번 중복", "학번 중복", 1);
						check_ID=false;//arraylist에 넣은 학번들과 입력한 학번이 일치할 경우 중복
						break;
					}
					check_ID=true;
				}
				if(check_ID==true)
				{
					JOptionPane.showMessageDialog(null, "학번 check 완료", "check 완료", 1);
				}
				

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
