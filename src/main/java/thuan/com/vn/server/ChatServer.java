package thuan.com.vn.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ChatServer {
    private int port;
    private List<String> userNames = new ArrayList<>();
    private List<UsersThread> usersThreads = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    public ChatServer(int port) {
        this.port = port;

    }

    //  xao man hinh 
    public static void clrscr() {
        
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }


    public void excute() {
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            System.out.println("  /-------------------------------------------/");
            System.out.println(" /   Chat Server is listening on port " + port+"   /");
            System.out.println("/-------------------------------------------/");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user Connected");

                UsersThread newUser = new UsersThread(socket, this);
                usersThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // để lấy thông báo chi tiết về ngoại lệ dưới dạng giá trị chuỗi.
            System.out.println("Error:in the server" + e.getMessage());

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
            System.out.println( " ════════════════════════════ASM JAVA NETWORK:NGO VAN THUAN-PF10══════════════════════════════");
            System.out.println("                ************************************************************* ");
            System.out.println("                     |~~~~~~~~~~~~~~~~~~~APP CHAT~~~~~~~~~~~~~~~~~~~~|");
            System.out.println("                     |1.Open The Connection Port                     |");
            System.out.println("                     |-----------------------------------------------|");
            System.out.println("                     |2.EXIT                                         |");
            System.out.println("                     |-----------------------------------------------|");
            System.out.print("         ==>>  Invite Server Select:");
            while (true) {
          String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    
                    System.out.println("Please enter the port to connect:");
                   
                    int port = Integer.parseInt(sc.nextLine());
                    ChatServer server = new ChatServer(port);
                    clrscr();
                    server.excute();
                    break;
                case "2":
                    System.out.println("Server End !");
                    System.exit(0);
                    break;
                default:
                   System.out.println("There is no function for this!\nPlease Re-enter:");
                // System.exit(0);
                    break;
            }
        }

    }
    // Cung cấp tin nhắn từ người dùng này đến người dùng khác(BroadCasting)

    public void broadCasting(String message, UsersThread excludeUser) {
        for (UsersThread aUser : usersThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    // Lưu tên người dùng của client mới đc kết nối
    public void addUserName(String userName) {
        userNames.add(userName);
    }

    // Khi một máy khách bị từ chối hãy xóa tên người dùng và userThread đc liên kết
    public void removeUser(String userName, UsersThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            usersThreads.remove(aUser);
            System.out.println("The User-" + userName + ":quitted");
        }
    }

    // Trả về true nếu người dùng khác đc két nối(Không tính ng dung hiện tại)
    public boolean hasUsers() {
        return !this.userNames.isEmpty();

    }

    /**
     * @return the userNames
     */
   /**
    * @return the userNames
    */
   public List<String> getUserNames() {
       return userNames;
   }
    
}