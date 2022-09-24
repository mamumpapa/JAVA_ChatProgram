import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server {
		private ServerSocket serverSocket;
		private Socket socket;
		private MessengerA gui;
		private String msg;
		private Statement stmt;
		private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
		//사용자들의 정보와 데이터를 저장
		public final void setGui(MessengerA gui) {
			this.gui= gui;
		}
		public void setting(){
			Collections.synchronizedMap(clientsMap); //사용자들의 정보를 접근하면 다른 스레드가 접근못함
			try {
				serverSocket = new ServerSocket(5058);//서버를 생성
				while (true) {
					socket = serverSocket.accept();//클라이언트 연결을 수락함
					Receiver receiver = new Receiver(socket);//데이터를 받기 위한 스레드를 실행
					receiver.start();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		public static void main(String[] args) throws IOException {
			Server server = new Server();
			server.setting();//main문에서 서버를 생성
		}
		public void addClient(String nick, DataOutputStream out) throws IOException {
			sendMessage(nick + "님이 접속하셨습니다.\n");
			gui.appendMsg(nick+"님이 접속하셨습니다.\n");//클라이언트가 접속하면 누가 접속했는지 보내고, 화면에도 출력
			clientsMap.put(nick, out);//해쉬맵에 클라이언트 정보를 저장
		}
		public void removeClient(String nick) {
			sendMessage(nick+"님이 나가셨습니다.\n");
			gui.appendMsg(nick+"님이 나가셨습니다.\n");//클라이언트가 나가면 누가 나갔는지 보내고, 화면에도 출력
			clientsMap.remove(nick);//해쉬맵에서 클라이언트 정보를 삭제
		}
		public void sendMessage(String msg) {
			Iterator<String> cm =clientsMap.keySet().iterator();//해쉬맵에 있는 정보
			String key = "";//key값을 초기화
			while (cm.hasNext()) {
				key=cm.next();
				try {
					clientsMap.get(key).writeUTF(msg);//해쉬맵에 대한 정보를 통해 통신
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		class Receiver extends Thread {
			private DataInputStream in;
			private DataOutputStream out;
			private String nick;
			public Receiver(Socket socket) throws IOException {
				out = new DataOutputStream(socket.getOutputStream());//파일 입출력을 통해 데이터를 파일에 씀
				in = new DataInputStream(socket.getInputStream());//데이터를 받음
				nick=in.readUTF();//클라이언트의 닉네임(String 데이터)를 읽어옴
				addClient(nick, out);//들어온 클라이언트를 받음
			}
			public void run()
			{
				try {
					while(in!=null) {
						msg=in.readUTF();//메시지(String 데이터)를 읽어옴
						gui.appendMsg(msg);//받은 메시지를 화면에 출력
						sendMessage(msg);//메시지를 클라이언트에 보냄
					}
				}catch(IOException e)
				{
					removeClient(nick);//아무런 데이터도 오지 않았을 시 클라이언트를 삭제
				}
			}
		}
}