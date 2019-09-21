import java.awt.image.BufferedImage;

/**
 * This is the Runnable representing the recoloring task assigned to each
 * thread.
 * 
 * @author Giovanbattista
 *
 */
public class RecolorTask implements Runnable {

	private BufferedImage originalImage;
	private BufferedImage resultImage;
	private int leftCorner;
	private int topCorner;
	private int width;
	private int height;

	public RecolorTask(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner, int width,
			int height) {
		super();
		this.originalImage = originalImage;
		this.resultImage = resultImage;
		this.leftCorner = leftCorner;
		this.topCorner = topCorner;
		this.width = width;
		this.height = height;
	}

	@Override
	public void run() {
		ImageProcessing.recolorImage(originalImage, resultImage, leftCorner, topCorner, width, height);
	}

}
