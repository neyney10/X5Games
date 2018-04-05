import java.util.ArrayList;

public class Character {
	public String name;
	public int loyalty; // 0 = good, 1 = evil, 2= else?
	public int id;
	public String lore;
	public String gamedata;
	public int power;
	public String img64;
	
	public Character(String name,int loyalty,int id,String lore,String gamedata,int power,String img64)
	{
		this.name = name;
		this.loyalty=loyalty;
		this.id=id;
		this.lore=lore;
		this.gamedata = gamedata;
		this.power = power;
		this.img64 = img64;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("name:");
		sb.append(this.name);
		sb.append("%loyalty:");
		sb.append(this.loyalty);
		sb.append("%id:");
		sb.append(this.id);
		sb.append("%lore:");
		sb.append(this.lore);
		sb.append("%gamedata:");
		sb.append(this.gamedata);
		sb.append("%power:");
		sb.append(this.power);
		sb.append("%img64:");
		sb.append(this.img64);
		
		return sb.toString();
	}
	
	public static ArrayList<Integer> getIds(String str)
	{
	        ArrayList<Integer> charids = new ArrayList<Integer>();

	        String[] map = str.split("%");
	        for(String s : map)
	        {

	            String[] values = s.split(":");
	            switch(values[0])
	            {
	                case "id":
	                    charids.add(Integer.parseInt(values[1]));
	                    break;
	            }
	        }
	        try {
	            return charids;
	        }
	        catch (Exception e) {
	            return null;
	        }
	}
}
