package thuan.com.vn.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


import thuan.com.vn.encryptChat.*;

public class UsersThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    public UsersThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            printUsers();
           
            String userName = reader.readLine();
            String decode= EncodeDeCode.decode(userName);
            String decodeUserName = decode;
            server.addUserName(decode);
            String serverMessage ="New User Connected:"+decodeUserName ;
            System.out.println(serverMessage);
            String encode=EncodeDeCode.encode(serverMessage);

            server.broadCasting(encode, this);



            String clientMessage;
            do {
                clientMessage = reader.readLine();
                decode=EncodeDeCode.decode(clientMessage);
                encode = EncodeDeCode.encode("[" + decodeUserName + "]:" + decode) ;
                server.broadCasting(encode, this);
            } while (!decode.equals("bye"));

            server.removeUser(decodeUserName, this);
            System.out.println(decodeUserName +": has quitted");
            socket.close();
            encode = EncodeDeCode.encode(decodeUserName + ":has quitted") ;
            server.broadCasting(encode, this);
         
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error in UserThread:"+ e.getMessage());
            e.printStackTrace();
        }

    }
    public  void printUsers(){
        if (server.hasUsers()) {
            try {
                String encode= EncodeDeCode.encode("Connected Uses:"+ server.getUserNames().toString());
                
               
                 writer.println(encode);
                //  server.dataOutputStream.writeUTF(encode);
                //  System.out.println(encode);
                //  writer.flush();
                // server.dataOutputStream.writeUTF(encode);
                //  writer.println("---- Note: In ChatRoom If You Enter <bye> You Will Be Erased from ChatRoom System Erase!-----");
                // writer.println("Register with your name to enter");
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }

        }else{
            try {
                String encode =EncodeDeCode.encode("No users are currently connected!");
            // server.dataOutputStream.writeUTF(encode);
                // System.out.println(encode);
                writer.println(encode);
                // server.dataOutputStream.writeUTF(encode);
                // System.out.println(encode);
                // writer.flush();
            //   writer.println("---- Note: In ChatRoom If You Enter <bye> You Will Be Erased from ChatRoom System Erase!-----");
            // writer.println("Register with your name to enter");
                
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
            
            
        }
        
    }
    public void sendMessage(String message){
        writer.println(message);
    }

}
