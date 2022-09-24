import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MessengerA extends JFrame implements ActionListener,Runnable {
	
	private JTextArea jta = new JTextArea(40, 25);//메시지가 출력되는 textarea 선언
	private JTextField jtf = new JTextField(25);//메시지를 입력하는 textfield 선언
	private Server server = new Server();
	private static GUI g;
	private static String nickName;
	
	public MessengerA(String ID) throws IOException {
		nickName=ID;//nickname을 gui.java로부터 받음
		Thread t = new Thread(this);//스레드로 실행
		t.start();
	}
	public static void main(String[] args) throws IOException {
		g=new GUI();
	}
	@Override
	public void actionPerformed (ActionEvent e) {
		String msg=nickName+ "(방장) : " + jtf.getText() + "\n";//메시지를 출력할 때 메시지 전에 나올 닉네임을 설정
		String msg2="SENT(방장) : " + jtf.getText() + "\n";
		jta.append(msg2);//입력한 메시지를 화면에 출력
		server.sendMessage(msg);//입력한 메시지를 보냄
		jtf.setText("");//채팅 입력란을 공백으로 초기화
	}
	public void appendMsg(String msg) {
		jta.append(msg);//메시지를 Textarea에 출력
	}
	@Override
	public void run() {
		add (jta, BorderLayout.CENTER);
		add (jtf, BorderLayout.SOUTH);
		jtf.addActionListener(this);
		setDefaultCloseOperation (EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200, 100, 400, 600);
		setTitle("서버");
		//서버를 스레드로 실행
		server.setGui(this);//서버 gui를 이 스레드에 세팅
		server.setting();//setting메소드를 통해 서버를 연결할 수 있음
	}	
}