import java.util.ArrayList;

public class Set {
	ArrayList<Integer> characters = new ArrayList<>();
	String name;
	
	public Set(String new_set_name, ArrayList<Integer> idsset)
	{
		this.name = new_set_name;
		this.characters = idsset;
	}
	public Set(String new_set_name, int[] idsset)
	{
		this.name = new_set_name;
		this.characters = convertToList(idsset);
	}
	
	public ArrayList<Integer> convertToList(int[] array)
	{
		ArrayList<Integer> list = new ArrayList<>();
		for(int i=0;i<array.length;i++)
		{
			list.add(array[i]);
		}
		return list;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();  
		sb.append(this.name);
		for(int id : this.characters)
		{
			sb.append("%id:" + id);
		}
		return sb.toString();
	}
}
