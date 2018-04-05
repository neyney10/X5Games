public class Program {

	public static final String file_chars = "C:\\Users\\neyne\\desktop\\data.xml";
	public static final String file_sets = "C:\\Users\\neyne\\desktop\\data_set.xml";

	public static void main(String[] args) {	
		if(args.length != 2)
		{
			Server.characters = XML.getData(file_chars);//"C:\\Users\\neyne\\desktop\\data.xml"
			Server.sets = XML.getDataSet(file_sets);
		}
		else
		{
			Server.characters = XML.getData(args[0]);//"C:\\Users\\neyne\\desktop\\data.xml"
			Server.sets = XML.getDataSet(args[1]);
		}
		Server server = new Server();
		

	}

}
