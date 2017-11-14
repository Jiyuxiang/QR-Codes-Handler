package com.pd.qrcode;
/*
 * 灰度化算法
 * */
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.paint.Color;
import jp.sourceforge.qrcode.QRCodeDecoder;

public class GrayImage {
	BufferedImage image;
	//构造函数，读取二维码到image
	public GrayImage(WritableImage img) throws IOException{
		
		image = new BufferedImage((int)img.getWidth(),(int) img.getHeight(),BufferedImage.TYPE_INT_RGB);  
		PixelReader pixelReader = img.getPixelReader();
		int i,j;
		//System.out.print(pixelReader.getColor(3, 3).getRed());
		for(i=0;i<image.getWidth();i++){
			for(j=0;j<image.getHeight();j++){
				Color color = pixelReader.getColor(i, j);
				int r = (int) (color.getRed()*255);
				int g = (int) (color.getGreen()*255);
				int b = (int) (color.getBlue()*255);
				int rgb = (r<<16) + (g<<8) + b;
				
				image.setRGB(i, j, rgb);
			}
		}
	}
	//灰度化算法1 分量法
	public WritableImage Components(int kind){
		
		WritableImage target = new WritableImage(image.getWidth(), image.getHeight());
		PixelWriter targetWriter = target.getPixelWriter();
		int i,j;
		int rgb,cache = 0;
		//System.out.print(pixelReader.getColor(3, 3).getRed());
		for(i=0;i<image.getWidth();i++){
			for(j=0;j<image.getHeight();j++){
				
				rgb = image.getRGB(i, j);
				
				switch(kind){
				case 1:
					cache = (rgb & 0xff0000) >> 16; 
					break;
				case 2:
					cache = (rgb & 0xff00) >> 8;  
					break;
				case 3:
					cache = (rgb & 0xff); 
					break;
				}
				
				targetWriter.setColor(i, j, Color.rgb(cache, cache, cache));
			}
		}
		
		return target;
	}
	public WritableImage MaxValue(){
		
		WritableImage target = new WritableImage(image.getWidth(), image.getHeight());
		PixelWriter targetWriter = target.getPixelWriter();
		int i,j;
		int rgb,r,g,b,max;
		//System.out.print(pixelReader.getColor(3, 3).getRed());
		for(i=0;i<image.getWidth();i++){
			for(j=0;j<image.getHeight();j++){
				
				rgb = image.getRGB(i, j);
				
				r = (rgb & 0xff0000) >> 16; 
				g = (rgb & 0xff00) >> 8;  
				b = (rgb & 0xff); 
				max = Math.max(Math.max(r, g), b);
				targetWriter.setColor(i, j, Color.rgb(max, max, max));
			}
		}
		
		return target;
	}
	public WritableImage Average(){
		
		WritableImage target = new WritableImage(image.getWidth(), image.getHeight());
		PixelWriter targetWriter = target.getPixelWriter();
		int i,j;
		int rgb,r,g,b,ave;
		//System.out.print(pixelReader.getColor(3, 3).getRed());
		for(i=0;i<image.getWidth();i++){
			for(j=0;j<image.getHeight();j++){
				
				rgb = image.getRGB(i, j);
				
				r = (rgb & 0xff0000) >> 16; 
				g = (rgb & 0xff00) >> 8;  
				b = (rgb & 0xff); 
				ave = (r + g + b) / 3;
				targetWriter.setColor(i, j, Color.rgb(ave, ave, ave));
			}
		}
		
		return target;
	}
	public WritableImage weightedAverage(double redWeight,double greenWeight,double blueWeight){
		
		WritableImage target = new WritableImage(image.getWidth(), image.getHeight());
		PixelWriter targetWriter = target.getPixelWriter();
		int i,j;
		int rgb,r,g,b,wave;
		//System.out.print(pixelReader.getColor(3, 3).getRed());
		for(i=0;i<image.getWidth();i++){
			for(j=0;j<image.getHeight();j++){
				
				rgb = image.getRGB(i, j);
				
				r = (rgb & 0xff0000) >> 16; 
				g = (rgb & 0xff00) >> 8;  
				b = (rgb & 0xff); 
				wave = (int) (r*redWeight + g*greenWeight + b*blueWeight);
				targetWriter.setColor(i, j, Color.rgb(wave, wave, wave));
			}
		}
		
		return target;
	}
	
}
