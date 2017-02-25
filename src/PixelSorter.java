/*
 * 	File: PixelSorter.java
 * 	Author: Dylan Tobia
 * 	Purpose: Sort a an images pixels in each row, and return this as a new file.
 * 
 */


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public final class PixelSorter {

	private PixelSorter() {}
	
	public static void main(String[] args) {
		//read in image
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(args[0]));
		} catch (IOException e) {
			System.out.println("Couldn't open original image file.");
			System.exit(-1);
		}
		//get image dimensions
		int imageWidth = image.getWidth();
		int imageHeight= image.getHeight();
		
		//get all pixels in file as a 2d array
		int[][] allPixels = getPixels(image, imageWidth, imageHeight);
		//call sort function to sort each row of pixels
		allPixels = sort(allPixels, imageWidth, imageHeight);
		//create a new BufferedImage for creating the sorted image, using the same width, height, and type of original image
		BufferedImage imageToWrite = new BufferedImage(imageWidth, imageHeight, image.getType());
		//step through allPixels 2darray and write their values to the pixels in new file
		for(int i = 0; i < imageHeight; i++)
		{
			for(int j = 0; j < imageWidth; j++)
			{
				imageToWrite.setRGB(j, i, allPixels[i][j]);
			}
		}
		
		
		//create new file and write it.
		try {
			File outputFile = new File(getNewFileName(args[0]));
			ImageIO.write(imageToWrite, "jpg", outputFile);
		} catch (IOException e) {
			System.out.println("Problem writing to new file");
			System.exit(-1);
			
		}

	}

	//takes original file name from original image, removes its old type, adds "Sorted.jpg" to the end of it
	private static String getNewFileName(String originalFile) {
		StringBuilder newName = new StringBuilder(originalFile);
		newName.replace(newName.length()-4, newName.length(), "Sorted.jpg");
		return newName.toString();
	}

	//gets each individual row from the array of pixels, and calls another function to sort them, then returns sorted 2d array
	private static int[][] sort(int[][] allPixels, int width, int height) {
		int[][] sorted = new int[height][width];
		for(int i = 0; i < height; i++)
		{
			sorted[i] = sortRow(allPixels[i]);
			
		}
		return sorted;
	}

	//copy original values into new array, and sort that array using Arrays.sort
	private static int[] sortRow(int[] row) {
		int[] sortedRow = new int[row.length];
		System.arraycopy(row, 0, sortedRow, 0, sortedRow.length);
		Arrays.sort(sortedRow);
		return sortedRow;
	}

	//step through the file and grab each pixel's rgb single int value, and store it in a 2d array, return this array.
	private static int[][] getPixels(BufferedImage image, int imageWidth, int imageHeight) {
		int[][] pixels = new int[imageHeight][imageWidth];
		for(int i = 0; i < imageHeight; i++)
		{
			for(int j = 0; j < imageWidth; j++)
			{
				pixels[i][j] = image.getRGB(j, i);
			}
			
		}
		
		return pixels;
	}

}
