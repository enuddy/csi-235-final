import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Nick on 3/7/2016.
 */
public class Server {
    public static void main(String[] args) {
        String cName;
        int numMovies = 1;
        //System.out.println("Echo Server ...");
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

                cName = inputLine;
                //numMovies = Integer.parseInt(inputLine.substring(inputLine.indexOf(' ') + 1));
                String cardJsonStr = URLConnection.fetchData(cName);
                Card[] arr = URLConnection.parseData(cardJsonStr);

                out.println(Integer.toString(numMovies));

                if(!(cardJsonStr.equals("[]\n")))
                    for(int i = 0; i < numMovies; i++)
                    {
                        out.println(arr[i].toString());
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
