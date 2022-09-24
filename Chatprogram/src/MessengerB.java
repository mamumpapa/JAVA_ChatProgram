import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class MessengerB extends JFrame implements ActionListener, Runnable {
	
	private JTextArea jta = new JTextArea(40,25);//메시지가 출력되는 textarea 선언
	private JTextField jtf = new JTextField(25);//메시지를 입력하는 textfield 선언
	private Client client = new Client();
	private static GUI g;
	private static String nickName;
	
	public MessengerB(String ID) {
		nickName=ID;//nickname을 gui.java로부터 받음
		Thread t = new Thread(this);//스레드로 실행
        t.start();
	}
	public static void main(String[] args) {
		g=new GUI();
	}
	@Override
	public void actionPerformed (ActionEvent e) {
		String msg=nickName + ":" + jtf.getText() + "\n";//메시지를 출력할 때 메시지 전에 나올 닉네임(학번)을 설정
		client.sendMessage(msg);//입력한 메시지를 보냄
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
		setBounds (800, 100, 400, 600);
		setTitle("클라이언트");
		//클라이언트를 스레드로 실행
		client.setGui(this);//클라이언트 gui를 이 스레드에 세팅
		client.setNickname(nickName);//닉네임을 설정
		client.connect();//클라이언트를 통해 서버 연결
	}
}