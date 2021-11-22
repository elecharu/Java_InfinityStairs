package com.badlogicgames.superjumper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket socket = null;

        OutputStream outputStream = null;
        DataOutputStream dataOutputStream = null;

        InputStream inputStream = null;
        DataInputStream dataInputStream = null;

        try {

            serverSocket = new ServerSocket(9000);
            System.out.println("클라이언트로부터 데이터 전송받을 준비 완료");

            socket = serverSocket.accept();
            System.out.println("클라이언트 연결 완료");
            System.out.println("socket : " + socket);

            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);

            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

            while (true) {
                String clientMessage = dataInputStream.readUTF();
                System.out.println("clientMessage : " + clientMessage);

                dataOutputStream.writeUTF("메세지 전송 완료");
                dataOutputStream.flush();

                if (clientMessage.equals("stop")) break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {

                if (dataOutputStream != null) dataOutputStream.close();
                if (outputStream != null) outputStream.close();
                if (dataInputStream != null) dataInputStream.close();
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
