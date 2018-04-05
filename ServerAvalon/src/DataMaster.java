import java.util.ArrayList;

public class DataMaster {



	public static String sendV2(String str,int subid, int opid, int typeid)
	{//Version 2
		if(subid!=-1)
			return "{["+subid+"&#"+opid+"&#"+typeid+"&#"+str+"]}";
		else
			return "{["+"&#"+opid+"&#"+typeid+"&#"+str+"]}";
	}
	public static boolean readV2(StringBuilder str,Client client) 
	{
		if (str.length() > 4) {
			if (str.charAt(0) == '{' && str.charAt(1) == '[' && str.charAt(str.length() - 1) == '}' && str.charAt(str.length() - 2) == ']') {
				String[] values = str.substring(2, str.length() - 2).split("&#");
				client.setSubid(Integer.parseInt(values[0]));
				int opid = Integer.parseInt(values[1]);
				int typeid = Integer.parseInt(values[2]);
				String data = values[3];

				switch (typeid) {
				case 0:
					switch (opid) {
					case 0: //newid
						client.client_class.queueMessage(DataMaster.sendV2(Integer.toString(client.id), -1, 0, 0));
						client.name = data;
						break;
					case 1: //new lobby id - create new room/lobby
						String lobbytitle = data;
						Lobby lby = Server.addLobby(lobbytitle, client);
						client.client_class.queueMessage(sendV2(Integer.toString(lby.id),-1,1,0));
						System.out.println("[Server][Lobby][Create] due to client operation. lobby count: "+Server.lobbies.size()); 
						break;
					case 2: //enter lobby 
						int lbyid = Integer.parseInt(data);
						Lobby findlobby = Server.FindLobby(lbyid);
						client.memberin = findlobby;
						if(findlobby!=null)
						{
							findlobby.clients.add(client);
							for(Client c : findlobby.clients)
							{
								if (c.id!=client.id)
								{
									c.client_class.queueMessage(sendClient(client,c.getSubid()));
								}
							}

							System.out.println("[Server][Lobby][Enter] Client Entered to lobby"); 
							System.out.println("[Debug] Total members :" + findlobby.clients.size());
						}

						break;
					case 3: //exit lobby
						if(client!=null && client.memberin!=null)
						{
							client.memberin.RemoveClient(client);
							client.client_class.queueMessage(sendV2("0",client.getSubid(),0,9));
						}
						System.out.println("[Server][Lobby][Exit] Client Exited a lobby"); 
						System.out.println("[DEBUG]Datamaster exit lobby op finished");
						break;
					case 4: //send client list of room/ lobby
						Lobby lb = client.memberin;
						if(lb!=null)
							synchronized (lb.clients) {
								for(Client cl : lb.clients)
								{
									client.client_class.queueMessage(sendClient(cl,client.getSubid()));
								}
							}
						break;
					case 5: //send lobby list
							for(Lobby l : Server.lobbies)
							{
								client.client_class.queueMessage(sendLobby(l,client.getSubid()));
							}
						break;
					case 6://get and send Chars
						int idindex = data.indexOf('%'); // FROM LAZINESS
						int charsentid = Integer.parseInt(data.substring(0,idindex));
						ArrayList<Integer> ids = Character.getIds(data.substring(++idindex));
						for(Client clnt : client.memberin.clients)
						{
							if(ids.size()>0)
							{
								int index = (int) (Math.random()*ids.size());
								int id = ids.get(index);
								clnt.client_class.queueMessage(sendV2(Integer.toString(charsentid)+"%"+Integer.toString(id),clnt.getSubid(),0,2));
								ids.remove(index);
							}
						}
						break;
					case 7://get and send Chars ACK
						int charackid = Integer.parseInt(data);
						Client master = client.memberin.LobbyMaster;
						master.client_class.queueMessage(sendV2(Integer.toString(charackid)+"%"+Integer.toString(client.id),master.getSubid(),1,2));
						break;
					case 8: //send lobbymaster id
						client.client_class.queueMessage(sendV2(Integer.toString(client.memberin.LobbyMaster.id),client.getSubid(),0,3));
						//THIS SENT ALSO WHEN THE LOBBYMASTER LEAVES THE LOBBY IN "Lobby" class
						break;
					case 9: //Receive a new SET
						int idindexset = data.indexOf('%'); // FROM LAZINESS
						String new_set_name = data.substring(0,idindexset);
						ArrayList<Integer> idsset = Character.getIds(data.substring(++idindexset));
						Set new_set_to_add = new Set(new_set_name,idsset);
						Server.sets.add(new_set_to_add);
						XML.saveDataSetAppend(Program.file_sets, new_set_to_add);
						break;
					case 10: //user request SETS list
						for(Set s:Server.sets)
						{
							client.client_class.queueMessage(sendSet(s,client.getSubid()));
						}
						break;
					case 15:
						for(Character c : Server.characters)
						{
							client.client_class.queueMessage(sendCharacter(c, client.getSubid()));
						}
					break;
					case 16:
						client.client_class.queueMessage(sendV2(Integer.toString(Server.characters.length),client.getSubid(),1,15));
					break;
				
					}//switch opid
					break;
				} //switch typeid
			return true;
			}
		}
		return false;
	}

	public static String close(String str)//100%
	{
		return str+"^*";
	}
	public static String keepalive()
	{
		return "**kp"; 
	}

	public static boolean readKeepaliveAack(StringBuilder str)
	{
		//**kpa
		if(str.length()>4)
			if(str.charAt(0)=='*'&& str.charAt(1)=='*')
			{
				if(str.substring(2,str.length()).equals("kpa"))
					return true;
			}
		return false;
	}


	public static String sendClient(Client c,int subid) {
		String s=sendV2(c.toString(), subid, 0, 1);
		return s;
	}
	public static String sendLobby(Lobby lby,int subid)//90%
	{
		String s=sendV2( lby.toString(), subid, 0, 0);
		return s;

	}
	public static String sendCharacter(Character c,int subid)
	{
		String s = sendV2(c.toString(),subid,0,15);
		return s;
	}
	public static String sendSet(Set set,int subid)
	{
		String s = sendV2(set.toString(),subid,0,4);
		return s;
	}
	public static String sendClientLeft(int clientid,int subid)
	{
		String s=sendV2(Integer.toString(clientid),subid,1,1);
		return s;
	}





}
