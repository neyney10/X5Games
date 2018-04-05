
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
public class Server {

	static ServerSocket server_socket;
	static int server_port = 9099;
	static int connections;
	public static ArrayList<Client> clients = new ArrayList<Client>();
	public static ArrayList<Lobby> lobbies = new ArrayList<Lobby>();
	public static ArrayList<Set> sets = new ArrayList<Set>();
	public static Character[] characters;
	//reserve
	
	
	
	public Server() {
		
		try {
			System.out.println("[Setup] Establishing server...");
			server_socket = new ServerSocket(server_port);
			System.out.println("[Server] Established: "+getIpAddress() +" Port: "+server_port);
			
			 while (true) {
				 System.out.println("[Server] Waiting for connection...");
                 // block the call until connection is created and return
                 // Socket object
                 Socket socket = server_socket.accept();
                 socket.setSoTimeout(3222); //3.222 seconds timeout
                 
                 int newid = Server.idGenerator_Client();
                 ClientThread newClient = new ClientThread(socket,newid);
                 Client client = new Client(newClient,newClient.myClientID);
                 newClient.client = client;
                 clients.add(client);
                 newClient.Start();
                 
                 connections++;
                 System.out.println("[Server][Connection][New] #" + connections + " from "
                         + socket.getInetAddress() + ":"
                         + socket.getPort()
                         + " | Connected: "+Server.clients.size());
			 }
			
			
			
		} catch (IOException e) {
			System.out.println("[ERROR 0001] "+e.getMessage());
		}

	}
	
	
	/////OP
	//TEMP
	public static void Remove(int id)
	{//TODO: ThreadSafe
		synchronized (Server.clients) {
		for(Client c : clients)
		{
			if(c.id == id)
			{
				if(c.memberin!=null)
					c.memberin.RemoveClient(c);
				clients.remove(c);
				String add = c.client_class.socket.getInetAddress().toString();
				if(add ==null) 
					add="NO IP";
				System.out.println("[Server][Connection][Closed] Client connection terminated. " + add);
				return; 
			}
		}
		}
	}
	public static Client Find(int id)
	{//TODO: ThreadSafe
		synchronized (Server.clients) {
		for(Client c : clients)
		{
			if(c.id == id)
			{
				return c;
			}
		}
		return null;
		}
	}
	private static boolean LobbyNameExist(String title)
	{
		for(Lobby lby : Server.lobbies)
		{
			if(lby.title.equals(title))
				return true;
		}
		return false;
	}
	private static int idGenerator_Lobby() 
	{
		int id=0;

		for(id =0;id<Server.lobbies.size()+1;id++)
		{
			boolean found = false;
			for(int i =0;i<Server.lobbies.size();i++)
			{
				if(Server.lobbies.get(i).id==id)
				{
					found = true;
					break;
				}
			}
			if(!found)
				return id;
		}
		return id;
	}
	public static int idGenerator_Client()
	{
		int id=0;

		for(id =0;id<Server.clients.size()+1;id++)
		{
			boolean found = false;
			for(int i =0;i<Server.clients.size();i++)
			{
				if(Server.clients.get(i).id==id)
				{
					found = true;
					break;
				}
			}
			if(!found)
				return id;
		}
		return id;
	}
	public static Lobby FindLobby(int lobbyid) //Linear Search \:
	{
		for(Lobby lby : Server.lobbies)
		{
			if(lby.id == lobbyid)
				return lby;
		}
		return null;
	}
	public static Lobby addLobby(String title, Client client)
	{
		//return 0 for name is taken
		//return -1 for other error
		//return 1 for success
		
		if(LobbyNameExist(title))
		{	
			int counter = 1;
			String replacement;
			do{
				counter++;
				replacement = title + counter;
			}
			while(LobbyNameExist(replacement));
			
			title = replacement;
			//return 0;
		}
		Lobby lby = new Lobby(idGenerator_Lobby(),title,client);
		Server.lobbies.add(lby);
		return lby;
	}
	
	
	
	public static String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += " Server running at : "
                                + inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }

}
