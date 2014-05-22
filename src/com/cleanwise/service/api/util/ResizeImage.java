package com.cleanwise.service.api.util;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStream;

public class ResizeImage {
       public static void resizeToSquare(InputStream input, int size, OutputStream output, String imageSuffix)
       throws Exception
       {
         BufferedImage image = ImageIO.read(input);
         resizeToSquare(image, size, output, imageSuffix);
       }
       public static void resizeToSquare(String urlString, int size, OutputStream output)
       throws Exception
       {
         URL url = new URL(urlString);
         BufferedImage image = ImageIO.read(url);
         resizeToSquare(image, size, output, null);
       }

	public static void resizeToSquare(BufferedImage image, int size, OutputStream output, String imageSuffix)
	throws Exception
	{
		int hh = image.getHeight();
		int ww = image.getWidth();

		BufferedImage image1;
		if(hh<=size && ww<=size) {
			image1 = image;
		} else {
		  double hSmall = 0;
		  double wSmall = 0;
		  if(hh>ww) {
			hSmall = size;
			wSmall = (((double) size) / (double) hh)* (double) ww;
		  } else {
			wSmall = size;
			hSmall = (((double) size) / (double) ww)* (double) hh;
		  }
		  image1 = resize(image, (int) wSmall, (int ) hSmall);

		}
        String[] imageTypes = ImageIO.getReaderFormatNames();

		String typeStr = "na";
		int type = image.getType();
		if(type < imageTypes.length) {
			typeStr = imageTypes[type].toLowerCase();
		}
		if(typeStr.equals("jpeg")) {
		    typeStr = "jpg";
		}
        String[] imageWTypes = ImageIO.getWriterFormatNames();

		ImageIO.write(image1, typeStr, output);
		return;
	}

	public static BufferedImage resize(String urlString, int width, int height)
	throws Exception
	{
	    URL url = new URL(urlString);
        BufferedImage image = ImageIO.read(url);
		BufferedImage image1 = resize(image, width, height);
		return image1;
	}

	public static BufferedImage resize(BufferedImage image, int width, int height) {
		int type = image.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}
}
