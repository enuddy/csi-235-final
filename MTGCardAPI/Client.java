import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Nick on 3/7/2016.
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("Echo Client");
            try {
                System.out.println("Waiting for connection.....");
                InetAddress localAddress;
                localAddress = InetAddress.getLocalHost();
                try {
                    Socket clientSocket = new Socket(localAddress, 6000);
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Connected to server");
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        System.out.print("Enter a full or partial card name and how many results you would like (Goblin, 5): ");
                        String inputLine = scanner.nextLine();

                        if ("quit".equalsIgnoreCase(inputLine)) {
                            break;
                        }
                        out.println(inputLine);
                        String response;
                        int size = Integer.parseInt(br.readLine());
                        /*for(int i = 0; i < size; i++) {
                            response = br.readLine();
                            System.out.println(response);
                        }*/
                        response = br.readLine();
                        while(response != null) {
                            System.out.print(response);
                            response = br.readLine();
                            System.out.flush();
                        }
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }
}
