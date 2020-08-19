package thuan.com.vn.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import thuan.com.vn.server.ChatServer;

public class ChatClient {
    private String hostName;
    private int port;
    private String userName;
    static Scanner sc = new Scanner(System.in);
    public ChatClient(){

    }
    public ChatClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;

    }
  
    public void execute() {
        try {
            Socket socket = new Socket(hostName, port);
            System.out.println("Connected to the ChatServer");
            new ReadThread(socket, this).start();
            new WriterThread(socket, this).start();
           
           

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            System.out.println("Server not found:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("I/O Error:" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
   

    public static void main(String[] args) {
            System.out.println("   ~~~~~~~~~~~~~~~~~~~REGISTER~~~~~~~~~~~~~~~~~~~~   ");
            System.out.println("      -------------------------------------");
            System.out.println("      |1.Sign Up To ChatRoom              |");
            System.out.println("      |-----------------------------------|");
            System.out.println("      |2.Exit                             |");
            System.out.println("      |-----------------------------------|");
            System.out.print("--->>> Enter Chon:");
            String choice=sc.nextLine();
            switch (choice) {
                case "1":
                    ChatServer.clrscr();
                    System.out.print("Enter HostName:");
                    String hostName = sc.nextLine();
                    System.out.println("--------------------------------");
                    System.out.print("Enter Port:");
                    int port = Integer.parseInt(sc.nextLine());
                    ChatClient client = new ChatClient(hostName, port);
                    ChatServer.clrscr();
                    client.execute();
                    break;
                case "2":
                    System.out.println("Client END");
                    System.exit(0);
                    break;
                default:
                    // System.out.println("There is no function for this!\nPlease Re-enter:");
                    // System.exit(0);
                    System.out.println("There is no function for this!");
                    break;
                   
                   
            
        }

        

    }

}