package net.dingyabin.test;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MrDing
 * Date: 2018/11/15.
 * Time:23:20
 */
public class LogSocket {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(4560);
        ExecutorService service = Executors.newFixedThreadPool(50);
        while (true) {
            Socket socket = serverSocket.accept();
            Runnable runnable = () -> {
                InputStream inputStream = null;
                ObjectInputStream objectInputStream=null;
                while (true) {
                    try {
                        if(socket.isClosed()){
                            System.out.println("xxxxxxclosexxxxxx");
                           break;
                        }
                        System.out.println("----------");
                        inputStream = socket.getInputStream();
                        System.out.println("++++++++++");
                        objectInputStream= new ObjectInputStream(inputStream);
                        Object o = objectInputStream.readObject();
                        System.out.println(o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (socket != null) {
                            close(socket);
                        }
                        if (objectInputStream != null) {
                            close(objectInputStream);
                        }
                        if (inputStream != null) {
                            close(inputStream);
                        }
                    }
                }
            };
            service.submit(runnable);
        }
    }




    private static void close(Closeable c){
        try {
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
