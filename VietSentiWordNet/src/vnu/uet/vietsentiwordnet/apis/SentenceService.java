package vnu.uet.vietsentiwordnet.apis;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import vnu.uet.vietsentiwordnet.objects.ResultObject;
import vnu.uet.vietsentiwordnet.objects.SentenceLevel;
import vnu.uet.vietsentiwordnet.utils.Ultils;
import jvntextpro.JVnTextPro;

public class SentenceService {

	public static void main(String[] args) {
		SentenceLevel sl = new SentenceLevel();
		Ultils ul = new Ultils();
		JVnTextPro jvn = new JVnTextPro();
		jvn = ul.loadModels();
		try {
			sl.loadSenLevel(jvn);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ServerSocket serverSocket = null;
		int portNum = 1988;
		try {
			serverSocket = new ServerSocket(portNum);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + portNum);
			System.exit(-1);
		}
		System.out.println("Listening to new document from monitor ...");
		while (true) {
			Socket connectSock;
			try {
				connectSock = serverSocket.accept();
				new ResponseThread(connectSock, sl).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// serverSocket.close();
	}
}

class ResponseThread extends Thread {
	private Socket socket = null;
	SentenceLevel sl = null;

	public ResponseThread(Socket socket, SentenceLevel senlevel) {
		super("Response");
		this.socket = socket;
		this.sl = senlevel;
	}

	public void run() {
		ResultObject res = new ResultObject();
		try {
			OutputStreamWriter out = new OutputStreamWriter(
					socket.getOutputStream());
			BufferedInputStream in = new BufferedInputStream(
					socket.getInputStream());
			int totalBytes = in.available();
			byte buffer[] = new byte[totalBytes];
			in.read(buffer, 0, totalBytes);
			String body = new String(buffer, "UTF-8");
			System.out.println(body);
			// do opinion mining at senlevel
			res = sl.doSenLevel(body);
			// get score
			double Score = res.getScore();
			// get sentiment string
			String Sentiment = res.getResultString();
			if (Sentiment != null)
				out.write(Sentiment);
			else {
				out.write("Xin lỗi. Hệ thống chúng tôi chưa thể đoán được hướng quan điểm câu này :((");
			}
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
