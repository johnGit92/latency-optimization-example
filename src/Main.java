import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		long startTime = System.currentTimeMillis();

//		recolorSingleThread(originalImage, resultImage);
		int numberOfThreads = 4;
		recolorMultithreaded(originalImage, resultImage, numberOfThreads);

		long endTime = System.currentTimeMillis();

		File outputFile = new File(DESTINATION_FILE);
		ImageIO.write(resultImage, "jpg", outputFile);

		/**
		 * Performance/latency measurement
		 */
		long duration = endTime - startTime;
		System.out.println(String.valueOf(duration));
	}

	public static void recolorSingleThread(BufferedImage originalImage, BufferedImage resultImage) {
		ImageProcessing.recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(),
				originalImage.getHeight());
	}

	/**
	 * This method represents the multithreaded solution to recolor the original
	 * image. The strategy is to partition the image and assign each partition to a
	 * thread to process in parallel.
	 * 
	 * @param originalImage   original image.
	 * @param resultImage     result image.
	 * @param numberOfThreads number of threads used to process image.
	 */
	public static void recolorMultithreaded(BufferedImage originalImage, BufferedImage resultImage,
			int numberOfThreads) {

		List<Thread> threads = new ArrayList<>();
		int width = originalImage.getWidth();
		int height = originalImage.getHeight() / numberOfThreads;

		for (int i = 0; i < numberOfThreads; i++) {
			final int threadMultiplier = i;

			int leftCorner = 0;
			int topCorner = height * threadMultiplier;
			RecolorTask task = new RecolorTask(originalImage, resultImage, leftCorner, topCorner, width, height);
			Thread thread = new Thread(task);
			threads.add(thread);
		}

		for (Thread thread : threads) {
			thread.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
