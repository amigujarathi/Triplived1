package com.triplived.service.image.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.springframework.stereotype.Service;

import com.triplived.service.image.ImageResizeService;

/**
 * 
 * 
 * @author Ayan
 * 
 */

@Service
public class ImageResizeServiceImpl implements ImageResizeService {

	@Override
	public Boolean resizeImages(String imagePath, String resizePath,
			Integer maxTransformSize) throws IOException {

		long startTime = System.currentTimeMillis();
		File f = new File(imagePath);
		BufferedImage img = ImageIO.read(f); // load image

		// Quality indicate that the scaling implementation should do everything
		// create as nice of a result as possible , other options like speed
		// will return result as fast as possible
		// Automatic mode will calculate the resultant dimensions according
		// to image orientation .so resultant image may be size of 50*36.if you
		// want
		// fixed size like 50*50 then use FIT_EXACT
		// other modes like FIT_TO_WIDTH..etc also available.

		BufferedImage thumbImg = Scalr.resize(img, Method.QUALITY,
				Mode.AUTOMATIC, maxTransformSize, Scalr.OP_ANTIALIAS);

		// convert bufferedImage to outpurstream
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(thumbImg, "jpg", os);

		// or wrtite to a file

		File f2 = new File(resizePath);

		Boolean status = ImageIO.write(thumbImg, "jpg", f2);

		/*System.out.println("time is : "
				+ (System.currentTimeMillis() - startTime));*/

		os.close();
	
		return status;
	}

}