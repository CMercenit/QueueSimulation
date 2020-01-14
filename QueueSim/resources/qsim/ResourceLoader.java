package qsim;

import java.awt.Image;
import java.awt.Toolkit;

//https://www.codexpedia.com/java/java-packaging-resource-files-into-a-runnable-jar-file/

public class ResourceLoader
{
	static ResourceLoader rl = new ResourceLoader();
	
	public static Image loadImage(String imgName)
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource("images/"+imgName));
	}
}
