package java;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

class MyFilter implements FilenameFilter {
	private String extension;

	MyFilter(String extension) {
		this.extension = extension;
	}

	@Override
	public boolean accept(File dir, String name) {

		return name.endsWith(extension);
	}

}

public class FileDemo {

	public static BufferedImage readFile(String path) throws IOException {

		BufferedImage img = null;
		File file = new File(path);
		StringBuffer sb = new StringBuffer();
		if (file.exists()) {
			img = ImageIO.read(file);
		}

		return img;
	}

	public static void saveFile(String path, ImageIcon icon)
			throws IOException {
		File file=null;
		Image image = icon.getImage();

		BufferedImage buffered = (BufferedImage) image;
		if(path.endsWith(".png") ||path.endsWith(".jpg")||path.endsWith(".jpeg")||path.endsWith(".png"))
			file = new File(path );
		else
			file = new File(path +".jpg");
		//System.out.println("name is " + n);
		ImageIO.write(buffered, "png", file);

	}


}
