package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleController {
	@FXML
	private TextArea chat;

	@FXML
	private TextField message;

	@FXML
	private Button send;

	@FXML
	private void SendMessage() {
		printWriter.println("SEND " + message.getText());

		message.setText("");
	}

	private Socket socket;
	private Scanner scanner;
	private PrintWriter printWriter;

	public SampleController() {
		new Thread(() -> {
			try {
				socket = new Socket("localhost", 12345);

				scanner = new Scanner(socket.getInputStream());
				printWriter = new PrintWriter(socket.getOutputStream(), true);

				printWriter.println("JOIN VS");

				while (scanner.hasNextLine()) {
					if (chat != null) {
						chat.appendText(scanner.nextLine() + "\n");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
