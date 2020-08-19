package thuan.com.vn.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import thuan.com.vn.encryptChat.*;

public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;



    public ReadThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void run() {
        while (true) {
            String response;
            try {
                response = reader.readLine();
                String decode = EncodeDeCode.decode(response);
                if (decode.isEmpty()) {
                    return;
                }
                System.out.println("\n" + decode);

                // if (client.getUserName() != null) {
                //     System.out.println("[" + client.getUserName() + "]:");

                // }

            } catch (IOException e) {
                // TODO Auto-generated catch block

                System.out.println("Error reading from server :" + e.getMessage());

                e.printStackTrace();
                break;
            }

        }
    }

}
