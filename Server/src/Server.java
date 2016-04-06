
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) {
        Socket sSocket = null;

        try {
            ServerSocket serverSocket = new ServerSocket(6000);
            System.out.println("Waiting for connection.....");
            sSocket = serverSocket.accept();
            System.out.println("Connected to client");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(sSocket.getInputStream()));
            PrintWriter out = new PrintWriter(sSocket.getOutputStream(), true);
            String inputLine;



            while ((inputLine = br.readLine()) != null) {
                System.out.println("Client request: " + inputLine);

                // Split the client's request by , delimiter   TODO maybe check for whitespace here?
                String cardName = inputLine.split(",")[0];
                int    cardCount = Integer.parseInt(inputLine.split(",")[1]);

                // Access the API and get the amount of cards requested.
                String cardJsonStr = URLConnection.fetchData(cardName);
                Card[] arr = URLConnection.parseData(cardJsonStr, cardCount);

                // Send the information to the client if any is generated.
                if(!(cardJsonStr.equals("[]\n"))) {
                    for (int i = 0; i < cardCount; i++) {
                        out.print(arr[i].toString() + "~~"); // ~ = newline
                    }
                    out.println("~");
                }
                else
                {
                    out.println("There are no cards with that name.");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
