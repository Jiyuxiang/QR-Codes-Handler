package com.pd.qrcode;
/*
 * ±ﬂ‘µºÏ≤‚À„∑®
 * */
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class EdgeDetect {
	static int[] grayData;
	static int width;
	static int height;
	static int gradientThreshold = 50;
	
	public static WritableImage Roberts(WritableImage source){
		width = (int)source.getWidth();
		height = (int) source.getHeight();
		WritableImage target = new WritableImage(width,height);
		PixelWriter targetWriter = target.getPixelWriter();
		PixelReader sourceReader = source.getPixelReader();
		grayData = new int[width*height];
		
		for(int x=0 ; x<width ; x++){
			for(int y=0 ; y<height ; y++){
			grayData[y*width+x] = (int) (sourceReader.getColor(x, y).getRed()*255);
			}
		}
		float[] gradient = RobertGradientM();
		float maxGradient = gradient[0];
		int i;
		for(i=0;i<gradient.length;i++){
			if(maxGradient<gradient[i])
				maxGradient = gradient[i];
		}
		float scaleFactor = 255.0f / maxGradient;
		
		if(gradientThreshold>0){
			for(int y=1; y<height-1 ; y++){
				for(int x=1; x<width-1 ; x++){
					//System.out.println(Math.round(scaleFactor*gradient[y*width+x]));
					if(Math.round(scaleFactor*gradient[y*width+x])>=gradientThreshold)
						targetWriter.setColor(x, y, Color.BLACK);
					else if(grayData[y*width+x]<200)
						targetWriter.setColor(x, y, Color.BLACK);
					else
						targetWriter.setColor(x, y, Color.WHITE);
				}
			}
		}
		else{
			for(int y=1; y<height-1 ; y++){
				for(int x=1; x<width-1 ; x++){
					targetWriter.setColor(x, y, Color.WHITE);
				}
			}
		}
		
		return target;
	}

	private static float[] RobertGradientM() {
		// TODO Auto-generated method stub
		float[] mag = new float[width*height];
		int gx,gy;
		for(int y=1 ; y<height-1 ; y++){
			for(int x=1 ; x<width-1 ; x++){
				gx = RobertGradientX(x,y);
				gy = RobertGradientY(x,y);
				mag[y*width+x] = (float)(Math.abs(gx))+ (float)(Math.abs(gy));
			}
		}
		return mag;
	}

	private static int RobertGradientY(int x, int y) {
		// TODO Auto-generated method stub
		return RobertsGetGrayPoint(x,y)-RobertsGetGrayPoint(x+1,y+1)
				+RobertsGetGrayPoint(x,y+1)-RobertsGetGrayPoint(x+1,y);
	}

	private static int RobertsGetGrayPoint(int x, int y) {
		// TODO Auto-generated method stub
		return grayData[y*width + x];
	}

	private static int RobertGradientX(int x, int y) {
		// TODO Auto-generated method stub
		return RobertsGetGrayPoint(x,y)-RobertsGetGrayPoint(x+1,y+1)
				+RobertsGetGrayPoint(x+1,y)-RobertsGetGrayPoint(x,y+1);
	}
	
	public static WritableImage Uncurl(WritableImage source){
		width = (int)source.getWidth();
		height = (int) source.getHeight();
		WritableImage target = new WritableImage(width,height);
		PixelWriter targetWriter = target.getPixelWriter();
		PixelReader sourceReader = source.getPixelReader();
		
		int[] gray = new int[width*height];
		for(int y=0; y<height ; y++){
			for(int x=0; x<width ; x++){
				gray[y*width+x] =  (int) (sourceReader.getColor(x, y).getRed()*255);
			}
		}
		int h,v;
		
		for(int y=0; y<height ; y++){
			for(int x=0; x<width ; x++){
				h=v=0;
				if(x>0)
					h += gray[y*width+x-1];
				if(x<width-1)
					h += gray[y*width+x+1];
				if(y>0)
					v += gray[(y-1)*width+x];
				if(y<height-1)
					v += gray[(y+1)*width+x];
				
				if(gray[y*width+x]==0){
					if((h+v)>=(255*3)){
						//gray[y*width+x]=255;
						targetWriter.setColor(x, y, Color.WHITE);
					}
//					else if(h>0&& v>0){
//						gray[y*width+x]=255;
//						targetWriter.setColor(x, y, Color.WHITE);
//					}
					else 
						targetWriter.setColor(x, y, Color.BLACK);
				}
				else if(gray[y*width+x]==255){
					if((h+v)<=255){
						//gray[y*width+x]=0;
						targetWriter.setColor(x, y, Color.BLACK);
					}
//					else if(h<255*2&& v<255*2){
//						gray[y*width+x]=255;
//						targetWriter.setColor(x, y, Color.BLACK);
//					}
					else 
						targetWriter.setColor(x, y, Color.WHITE);
				}
			}
		}
		
		
		return target;
	}
}
