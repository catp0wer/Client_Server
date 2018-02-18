import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{

    private static Socket socket;
    public static void main(String[] args)
    {
        try
        {

            int port = 45002;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Main: Server started and listens to the port " + port);
            System.out.println("Main: Server socket "+serverSocket);

            System.out.println("Main: Starting infinite loop");
            int clientNumber = 1;
            ArrayList<Socket> connectedClients = new ArrayList<>();
            while(true)
            {
                System.out.println("Main: Blocking while waiting for a new client");
                socket = serverSocket.accept();
                connectedClients.add(socket);
                String clientName = "Client"+Integer.toString(clientNumber);

                MyRunnable myRun = new MyRunnable(socket,clientName,connectedClients);
                new Thread(myRun).start();
                clientNumber++;
//                MyRunnable myRun2 = new MyRunnable(socket,"Thread2");
//                new Thread(myRun2).start();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                System.out.println("Main Closing connection "+socket);
                socket.close();
            }
            catch(Exception e){}
        }
    }
}