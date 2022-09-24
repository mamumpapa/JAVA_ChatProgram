import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private MessengerB gui;
	private String msg;
	private String nickName;
	public final void setGui(MessengerB gui) {
		this.gui=gui;
	}
	public void connect() {
		try {
			socket = new Socket("127.0.0.1", 5058);//소켓을 사용하여 자체 IP로 연결
			out = new DataOutputStream(socket.getOutputStream());
			//파일 입출력을 통해 데이터를 파일에 씀
			in = new DataInputStream(socket.getInputStream());//데이터를 받음
			out.writeUTF(nickName);//nickname(string 문자열)을 파일에 씀
			while (in != null) {
				msg=in.readUTF();//string 문자열을 받음
				gui.appendMsg(msg);//gui에 받은 문자열을 출력
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Client client = new Client();
		client.connect();//클라이언트를 연결
	}
	public void sendMessage(String msg2) {
		try {
			out.writeUTF(msg2);//메시지(string 문자열)를 보냄
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setNickname(String nickName) {
		this.nickName = nickName;//생성자를 통해 닉네임을 설정
	}
}