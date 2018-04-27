package queues;

public class Name
{
	private String myName;
	private String[] names;
	
	public Name()
	{
		createNames();
		myName = getName();
	}
	
	public void createNames()
	{
		names = new String[30];
		names[0] = "Patricija Cavanagh";
		names[1] = "Celia Hughes";
		names[2] = "Ove Carlisle";
		names[3] = "Rosa Quincy";
		names[4] = "Redd Newell";
		names[5] = "Storm Albertsen";
		names[6] = "Kaapo Ness";
		names[7] = "Roslyn Derby";
		names[8] = "John Reeve";
		names[9] = "Moyra Cavan";
		names[10] = "Kegan Connor";
		names[11] = "Greer Frye";
		names[12] = "Dan Albertson";
		names[13] = "Dan Plante";
		names[14] = "Madisyn English";
		names[15] = "Tristen Curran";
		names[16] = "Rebekka Church";
		names[17] = "Lyssa Macy";
		names[18] = "Nando Honeycutt";
		names[19] = "Roderick Cooper";
		names[20] = "Rhoda Leary";
		names[21] = "Kristen Nevin";
		names[22] = "Lucky Järvinen";
		names[23] = "Jewel Abraham";
		names[24] = "Jim Outterridge";
		names[25] = "Laura Mac Pharlain";
		names[26] = "Theodor Kinley";
		names[27] = "Sheri Power";
		names[28] = "Aiden Montero";
		names[29] = "Dorthy Courtney";
	}
	
	public String getName()
	{
		return names[(int)(Math.random()*30)];
	}
	
	public String toString()
	{
		return myName;
	}
}
