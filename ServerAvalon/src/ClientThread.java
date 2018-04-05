import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class ClientThread
{
	Socket socket;
	int myClientID;
	public Client client;
	ClientOutput coutput;

	public ClientThread(Socket s,int id)
	{
		this.socket = s;
		this.myClientID = id;
	}
	public void Start()
	{
		coutput = new ClientOutput();
		ClientInput cinput = new ClientInput();
		coutput.start();
		cinput.start();
	}

	public void queueMessage(String msg)
	{
		coutput.putData(msg);//+client.getMsgid()
	}

	private class ClientOutput extends Thread // send
	{
		LinkedList<String> data = new LinkedList<String>();
		public ClientOutput() //Constructor
		{

		}
		public void putData(String msg) // using QUEUE
		{
			data.addLast(msg);
			interrupt();//TEST
		}

		@Override
		public void run() {
			try {
				PrintStream writer = new PrintStream(socket.getOutputStream());

				while(!socket.isClosed()){
					if(!data.isEmpty())
					{
						writer.print(data.poll());
						writer.flush();//NOT NEEDED
					}
					else
					{
						try{
							sleep(1000);
							//if not interupped check if alive
							queueMessage(DataMaster.keepalive());
						} catch (InterruptedException e) {
							//Nothing
						}

					}
				}


			} catch (IOException e) {
				e.printStackTrace();

			} 
			finally {
				Server.Remove(myClientID);
			}

		}

	}


	private class ClientInput extends Thread // receive
	{
		public ClientInput() //Constructor
		{

		}

		@Override
		public void run() {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
				StringBuilder strbuild = new StringBuilder();
				while(!socket.isClosed()){
					strbuild.setLength(0);
					int c;
					while((c = reader.read()) != -1) {
						strbuild.append((char)c);

						if(DataMaster.readKeepaliveAack(strbuild)) {break;}
						if(DataMaster.readV2(strbuild, client))
							break;
		
					}
					if(strbuild.length()==0) {break;}
					System.out.println("[Client][Message] "+strbuild);
				}


			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				Server.Remove(myClientID);
			}

		}

	}

}