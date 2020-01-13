package application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

	public static void main(String[] args) throws ClassNotFoundException {

		ExecutorService exSrv = Executors.newCachedThreadPool();
		try {
			CustomerUtilitiesServer server = new CustomerUtilitiesServer(9001);
			exSrv.submit(server);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
