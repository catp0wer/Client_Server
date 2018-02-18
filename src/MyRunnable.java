import java.io.*;
import java.net.Socket;
import java.util.Random;


public class MyRunnable implements Runnable {
    String threadName;
    Socket socket;
    Random myRan = new Random();

    public MyRunnable(Socket socket, String threadName) {
        this.socket = socket;
        this.threadName = threadName;
    }

    public void run() {
        System.out.println(threadName+" Got new connection from " + socket);
        //System.out.println("Inside " + threadName);
        while (true) {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                System.out.println(threadName+" Blocking while waiting for a message from ");
                String num = br.readLine();
                System.out.println(threadName+" Received message from "+threadName+ "and the message is " + num );
                if (num.equals("quit")) {
                    System.out.println(threadName+"Closing the connection");
                    break;
                }
                String returnMessage;
                try {
                    int numberInIntFormat = Integer.parseInt(num);
                    int returnValue = numberInIntFormat * 2;
                    returnMessage = String.valueOf(returnValue) + "\n";
                } catch (NumberFormatException e) {
                    returnMessage = "The message is not a number ";
                }
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                System.out.println(threadName+"Message sent to the client is " + returnMessage);
                bw.flush();

                try {

                    Thread.sleep(myRan.nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
