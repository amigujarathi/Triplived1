package com.triplived.service.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public interface ImageResizeService {

	Boolean resizeImages(String imagePath, String resizePath,
			Integer maxTransformSize) throws IOException;
 
 
}