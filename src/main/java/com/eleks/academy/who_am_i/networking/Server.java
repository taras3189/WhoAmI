package com.eleks.academy.who_am_i.networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String argvs[]) throws Exception {

		ServerSocket serverSocket = new ServerSocket(888);
		System.out.println("Server starts");
		System.out.println("Waiting for a client connect....");

		Socket clientSocket = serverSocket.accept();
		System.out.println("Connected with client");

		PrintStream ps = new PrintStream(clientSocket.getOutputStream());

		BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));

		String str, str1;

		while ((str = br.readLine()) != null) {
			System.out.println("Client: " + str);
			str1 = kb.readLine();

			ps.println("Server: " + str1);
		}

		ps.close();
		br.close();
		kb.close();
		serverSocket.close();
		clientSocket.close();

		System.exit(0);

	}
}
