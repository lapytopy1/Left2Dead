package test;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import loader.LoadGame;

import org.junit.Test;

public class TestLoad {
	
	@Test
	public void testLoadFileCorrectly_1(){
		try {
			BufferedImage testPng = ImageIO.read(new File("data/testData/test.png"));
			
			LoadGame loadGame = new LoadGame("data/testData/test.tmx"); 
			List<BufferedImage> images = loadGame.getImages();
			for(int i = 0; i<images.size();i++){
				assertTrue(images.get(i).getWidth() == testPng.getWidth());
				assertTrue(images.get(i).getHeight() == testPng.getHeight());
				for(int j = 0; j<testPng.getWidth();j++){
					for(int a = 0; a< testPng.getHeight();a++){
						System.out.println(j+" : "+a);
						assertTrue(images.get(i).getRGB(j, a) == testPng.getRGB(j, a));
					}
				}
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
