


public class Client {
	public ClientThread client_class;
	public int id;
	public String name = "Default_Username";
    public Lobby memberin;
    private int subid = 0;
	
	public Client(ClientThread c,int i)
	{
		this.client_class = c;
		this.id=i;
	}
	
	public void setSubid(int m)
	{
		synchronized (this) { //"this" is too Extreme
			subid = m;
		}
	}
	public int getSubid()
	{
		synchronized (this) { //"this" is too Extreme
			return subid;
		}
	}
	
	public String toString()
	{
		String str="";
		str+="client_id:"+this.id+"%client_name:"+this.name;
		return str;
	}
	
	
}
