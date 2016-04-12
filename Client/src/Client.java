import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
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
                    System.out.print("Enter a full or partial card name and how many results you would like (Goblin,5): ");
                    String inputLine = scanner.nextLine();

                    // Check to see the client wants to quit the server.
                    if ("quit".equalsIgnoreCase(inputLine)) {
                        break;
                    }
                    out.println(inputLine);

                    String response;
                    while((response = br.readLine()) != null) {
                        //System.out.println("Log: " + response);
                        String[] parsedResponse = response.split("~");

                        // Check for a end response from server to know that the request is complete.
                        if (parsedResponse[0].equals("[END]"))
                            break;

                        // Go through and print out each line from the response.
                        for (String line : parsedResponse) {
                            System.out.println(line);
                        }
                    }
                }

                // close the sockets and readers
                br.close();
                clientSocket.close();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}