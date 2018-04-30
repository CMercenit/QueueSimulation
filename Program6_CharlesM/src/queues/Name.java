package queues;

/**
 * Chooses a random name from those supplied, used to
 * give Cashiers a name.
 * 
 * @author Charles Mercenit
 */
public class Name
{
	private String myName;
	private String[] myNames;
	
	public Name()
	{
		createNames();
		myName = getName();
	}
	
	/**
	 * I used a random name generator to come
	 * up with all these names. This method adds
	 * each name to a String array.
	 */
	
	public void createNames()
	{
		myNames = new String[30];
		myNames[0] = "Patricija Cavanagh";
		myNames[1] = "Celia Hughes";
		myNames[2] = "Ove Carlisle";
		myNames[3] = "Rosa Quincy";
		myNames[4] = "Redd Newell";
		myNames[5] = "Storm Albertsen";
		myNames[6] = "Kaapo Ness";
		myNames[7] = "Roslyn Derby";
		myNames[8] = "John Reeve";
		myNames[9] = "Moyra Cavan";
		myNames[10] = "Kegan Connor";
		myNames[11] = "Greer Frye";
		myNames[12] = "Dan Albertson";
		myNames[13] = "Dan Plante";
		myNames[14] = "Madisyn English";
		myNames[15] = "Tristen Curran";
		myNames[16] = "Rebekka Church";
		myNames[17] = "Lyssa Macy";
		myNames[18] = "Nando Honeycutt";
		myNames[19] = "Roderick Cooper";
		myNames[20] = "Rhoda Leary";
		myNames[21] = "Kristen Nevin";
		myNames[22] = "Lucky Järvinen";
		myNames[23] = "Jewel Abraham";
		myNames[24] = "Jim Outterridge";
		myNames[25] = "Laura Mac Pharlain";
		myNames[26] = "Theodor Kinley";
		myNames[27] = "Sheri Power";
		myNames[28] = "Aiden Montero";
		myNames[29] = "Dorthy Courtney";
	}
	
	/**
	 * Returns a random name from the array.
	 * 
	 * @return: a random name
	 */
	
	public String getName()
	{
		return myNames[(int)(Math.random()*30)];
	}
	
	/**
	 * Returns the actual name instead of the location
	 * in memory that myName is stored.
	 * 
	 * @return: myName String
	 */
	
	public String toString()
	{
		return myName;
	}
}