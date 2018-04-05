import java.util.ArrayList;

public class Lobby {//TODO
	public ArrayList<Client> clients = new ArrayList<Client>();
	//characters
	Client LobbyMaster;
	String title;
	int id;
	//pic

	public Lobby(int id, String title,Client LobbyMaster)
	{
		this.id=id;
		this.title=title;
		this.LobbyMaster=LobbyMaster;
		//clients.add(this.LobbyMaster);
	}

	public void RemoveClient(Client c)
	{
		synchronized (this.clients) {
			c.memberin = null;
			clients.remove(c);
			if(clients.isEmpty())
				Close();
			else
			{
				if(this.LobbyMaster.id == c.id)
				{
					this.LobbyMaster = clients.get(0); // NEED TO NOTIFY THE NEW LOBBY MASTER
					this.LobbyMaster.client_class.queueMessage(DataMaster.sendV2(Integer.toString(this.LobbyMaster.memberin.LobbyMaster.id),this.LobbyMaster.getSubid(),0,3));
				}
				NotifyLeave(c);
			}
			System.out.println("[DEBUG] [REMOVECLIENT] finished");
		}
	}
	private void Close()
	{
		Server.lobbies.remove(this);
	}
	protected void NotifyLeave(Client client)
	{
		for(Client c : clients)
		{
			c.client_class.queueMessage(DataMaster.sendClientLeft(client.id, c.getSubid()));//need to create function
		}
	}


	public String toString()
	{
		String str="";
		String sc = "";
		for(Client c : clients)
		{
			sc+="%"+c;
		}

		str+="id:"+id+"%title:"+title+"%lobbymaster_id:"+LobbyMaster.id+sc;

		return str;
	}
	public static Lobby fromString(String str)//EXPERIMENTAL, FOR CLIENT USE ONLY.
	{
		int lobmaster_id = -1;
		String ttle = "";//title
		int id = -1;

		String[] map = str.split("%");
		for(String s : map)
		{
			String[] values = s.split(":");
			switch(values[0])
			{
			case "title":
				ttle = values[1];
				break;
			case "lobbymaster_id":
				lobmaster_id = Integer.parseInt(values[1]);
				break;
			case "id":
				id = Integer.parseInt(values[1]);
				break;
			}
		}
		return new Lobby(id,ttle,new Client(null,lobmaster_id));
	}
}
