import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This is a sample java application that process an image applying
 * multithreading.
 * 
 * @author Giovanbattista
 *
 */
public class Main {

	public static final String SOURCE_FILE = "./resources/many-flowers.jpg";
	public static final String DESTINATION_FILE = "./out/many-flowers.jpg";

	public static void main(String[] args) throws IOException {

		BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
		BufferedImage resultimage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		
		

	}

}
