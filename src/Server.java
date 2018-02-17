import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{

    private static Socket socket;
    public static void main(String[] args)
    {
        try
        {

            int port = 45000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started and listens to the port " + port);
            System.out.println("Server socket "+serverSocket);

            System.out.println("Starting infinite loop");

            while(true)
            {
                System.out.println("Blocking while waiting for a new client");
                socket = serverSocket.accept();
                System.out.println("Got new connection from "+socket);
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                System.out.println("Blocking while waiting a message from client");
                String num = br.readLine();
                System.out.println("Message received from client is "+num);
                    if(num.equals("quit")){
                        System.out.println("Closing the connection");
                        break;
                    }

                String returnMessage;
                try
                {
                    int numberInIntFormat = Integer.parseInt(num);
                    int returnValue = numberInIntFormat*2;
                    returnMessage = String.valueOf(returnValue) + "\n";
                }
                catch(NumberFormatException e)
                {
                    returnMessage = "The message is not a number ";
                }
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                System.out.println("Message sent to the client is "+returnMessage);
                bw.flush();
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
                System.out.println("Closing conection "+socket);
                socket.close();
            }
            catch(Exception e){}
        }
    }
}