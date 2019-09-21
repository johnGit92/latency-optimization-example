import java.awt.image.BufferedImage;

/**
 * Class which provides image processing utility methods.
 * 
 * @author Giovanbattista
 *
 */
public class ImageProcessing {

	/**
	 * This method iterates through the entire image and applies the recoloring.
	 * 
	 * @param originalImage original image.
	 * @param resultImage   result image.
	 * @param leftCorner    value of the left corner of the image.
	 * @param topCorner     value of the top corner of the image.
	 * @param width         width of the image.
	 * @param height        height of the image.
	 */
	public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner,
			int topCorner, int width, int height) {
		for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
			for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
				recolorPixel(originalImage, resultImage, x, y);
			}
		}
	}

	/**
	 * Recolors the pixel at the x and y coordinates obtained from the original
	 * image into the result image.
	 * 
	 * @param originalImage original image.
	 * @param resultImage   result image.
	 * @param x             x coordinate of the pixel to recolor.
	 * @param y             y coordinate of the pixel to recolor.
	 */
	public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
		int rgb = originalImage.getRGB(x, y);

		int red = getRed(rgb);
		int green = getGreen(rgb);
		int blue = getBlue(rgb);

		int newRed;
		int newGreen;
		int newBlue;

		if (isShadeOfGray(red, green, blue)) {
			newRed = Math.min(255, red + 10);
			newGreen = Math.max(0, green - 80);
			newBlue = Math.max(0, blue - 20);
		} else {
			newRed = red;
			newGreen = green;
			newBlue = blue;
		}

		int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
		setRGB(resultImage, x, y, newRGB);

	}

	/**
	 * Assign the rgb color value to the image.
	 * 
	 * @param image target buffer image.
	 * @param x     pixel x coordinate.
	 * @param y     pixel y coordinate.
	 * @param rgb   rgb value.
	 */
	public static void setRGB(BufferedImage image, int x, int y, int rgb) {
		image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
	}

	/**
	 * Determines if a pixel is a shade of gray. Check if all the three components
	 * have similar color intensity, with no one stronger than the rest.
	 * 
	 * @param red   red value.
	 * @param green green value.
	 * @param blue  blue value.
	 * @return return true if a pixel with the specified color values is a shade of
	 *         gray, false otherwise.
	 */
	public static boolean isShadeOfGray(int red, int green, int blue) {

		/**
		 * Check if the color is almost a perfect mix of green red and blue we know it's
		 * a shade of gray. 30 is the distance used to determine their proximity.
		 */
		return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;

	}

	/**
	 * Build the pixel rgb compound value from the individual value of red, green
	 * and blue.
	 * 
	 * @param red   red value.
	 * @param green green value.
	 * @param blue  blue value.
	 * @return return the rgb compound value from red, green and blue.
	 */
	public static int createRGBFromColors(int red, int green, int blue) {
		int rgb = 0;

		/**
		 * rgb computed applying the logical OR with the individual values, bit shifting
		 * properly.
		 */
		rgb |= blue; // no shift needed cause blue is the rightmost byte value
		rgb |= green << 8; // one byte left shift
		rgb |= red << 16; // two bytes left shift

		rgb |= 0xFF000000; // set the alpha value to the highest value to make the pixel opaque

		return rgb;
	}

	/**
	 * Return only the blue value from the specified rgb value.
	 * 
	 * @param rgb rgb value.
	 * @return return the blue value from the specified rgb value.
	 */
	public static int getBlue(int rgb) {

		/**
		 * Apply a bit mask excluding the rightmost byte which is exactly the blue
		 * component.
		 */
		return rgb & 0x000000FF;
	}

	/**
	 * Return only the green value from the specified rgb value.
	 * 
	 * @param rgb rgb value.
	 * @return return the green value from the specified rgb value.
	 */
	public static int getGreen(int rgb) {

		/**
		 * Apply a bit mask excluding the alpha, red and blue component and shift 8
		 * bytes to the right cause green is the second byte from the right.
		 */
		return (rgb & 0x0000FF00) >> 8;
	}

	/**
	 * Return only the red value from the specified rgb value.
	 * 
	 * @param rgb rgb value.
	 * @return return the red value from the specified rgb value.
	 */
	public static int getRed(int rgb) {

		/**
		 * Apply a bit mask excluding the alpha, green and blue component and shift 16
		 * bytes to the right cause red is the third byte from the right.
		 */
		return (rgb & 0x00FF0000) >> 16;
	}

}
