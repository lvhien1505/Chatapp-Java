package thuan.com.vn.client;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import thuan.com.vn.encryptChat.*;

public class WriterThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;


    public WriterThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

        } catch (Exception e) {
            // TODO: handle exception
            // lỗi khi nhận luồng đầu ra
            System.out.println("Error getting output stream:" + e.getMessage());
            e.printStackTrace();
        }

    }

    

    public void run() {
        Console console = System.console();
        String userName = console.readLine("\n Enter your Name:");
        client.setUserName(userName);
        String encode = EncodeDeCode.encode(userName);
        writer.println(encode);
        String text;
     
        do {
            text = console.readLine(" ");
        
            encode = EncodeDeCode.encode(text );
            writer.println(encode);
        } while (!text.equals("bye"));

        try {
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error writing to server:" + e.getMessage());
            e.printStackTrace();
        }
    }

    
}
