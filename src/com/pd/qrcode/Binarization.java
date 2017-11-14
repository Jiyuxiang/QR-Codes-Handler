package com.pd.qrcode;
/*
 * ��ֵ���㷨
 * */
import java.io.IOException;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import jp.sourceforge.qrcode.QRCodeDecoder;

public class Binarization {
	
	public static WritableImage Otsu(WritableImage source){
		int width = (int)source.getWidth();
		int height = (int) source.getHeight();
		WritableImage target = new WritableImage(width,height);
		PixelWriter targetWriter = target.getPixelWriter();
		PixelReader sourceReader = source.getPixelReader();
		
		int i,j;
		int[] gray = new int[256];
		for(i=0;i<256;i++)
			gray[i]=0;
		//����ÿ���Ҷ�ֵ��Ӧ�����ظ���,�õ��Ҷ�ֱ��ͼ
		int grayDegree;
		for(i=0;i<width;i++){
			for(j=0;j<height;j++){
				grayDegree = (int) (sourceReader.getColor(i, j).getRed()*255);
				gray[grayDegree]++;
			}
		}
		//ͳ��ÿ���Ҷ�ֵ��Ӧ���ֵ���䷽��ҳ���䷽�����ֵ
		final int total = width*height;
		double theta,maxTheta = 0;
		double w0,w1,u0,u1;
		int bestGray = 0;
		for(i=0;i<256;i++){
			w0 = w1 = u0 = u1 = theta = 0;
			for(j=0;j<256;j++){
				if(j<=i){
					w0 += (double)gray[j]/total;
					u0 += (double)j*gray[j]/total;
				}	
				else{
					u1 += (double)j*gray[j]/total;
				}
			}
			w1 = 1-w0;
			theta = w0 * w1 * (u0 - u1) * (u0 - u1);
			if(theta>maxTheta){
				maxTheta = theta;
				bestGray = i;
			}
		}
		
		for(i=0;i<width;i++){
			for(j=0;j<height;j++){
				grayDegree = (int) (sourceReader.getColor(i, j).getRed()*255);
				if(grayDegree>bestGray)
					targetWriter.setColor(i, j, Color.WHITE);
				else
					targetWriter.setColor(i, j, Color.BLACK);
			}
		}
		
		return target;
	}
	//������
	public static WritableImage Iterate(WritableImage source){
		int width = (int)source.getWidth();
		int height = (int) source.getHeight();
		WritableImage target = new WritableImage(width,height);
		PixelWriter targetWriter = target.getPixelWriter();
		PixelReader sourceReader = source.getPixelReader();
		
		int i,j;
		int[] gray = new int[256];
		for(i=0;i<256;i++)
			gray[i]=0;
		//����ÿ���Ҷ�ֵ��Ӧ�����ظ���,�õ��Ҷ�ֱ��ͼ
		int grayDegree;
		for(i=0;i<width;i++){
			for(j=0;j<height;j++){
				grayDegree = (int) (sourceReader.getColor(i, j).getRed()*255);
				gray[grayDegree]++;
			}
		}
		int max=255,min=0;
		//ͳ�����Ҷ�ֵ����С�Ҷ�ֵ
		while(gray[max]==0)
			max--;
		while(gray[min]==0)
			min++;
		//��ʼ��ֵ
		int t0 = (max + min) / 2;
		int t1 = min;
		int aveb,avef,numb,sumb,numf,sumf;
		while(t0!=t1){
			t0 = t1;
			//��ǰ���ͱ�����ƽ���Ҷ�ֵ
			numb = sumb =0;
			for(i=min;i<=t0;i++){
				sumb += gray[i]*i;
				numb += gray[i];
			}
			aveb = sumb / numb;
			numf = sumf = 0;
			for(i=t0+1;i<=max;i++){
				sumf += gray[i]*i;
				numf += gray[i];
			}
			avef = sumf / numf;
			t1 = (aveb + avef) / 2;
		}
		
		for(i=0;i<width;i++){
			for(j=0;j<height;j++){
				grayDegree = (int) (sourceReader.getColor(i, j).getRed()*255);
				if(grayDegree>t0)
					targetWriter.setColor(i, j, Color.WHITE);
				else
					targetWriter.setColor(i, j, Color.BLACK);
			}
		}	
		
		return target;
	}
	//˫�巨
	public static WritableImage Doublet(WritableImage source){
		int width = (int)source.getWidth();
		int height = (int) source.getHeight();
		WritableImage target = new WritableImage(width,height);
		PixelWriter targetWriter = target.getPixelWriter();
		PixelReader sourceReader = source.getPixelReader();
		
		int i,j;
		int[] gray = new int[256];
		for(i=0;i<256;i++)
			gray[i]=0;
		//����ÿ���Ҷ�ֵ��Ӧ�����ظ���,�õ��Ҷ�ֱ��ͼ
		int grayDegree;
		for(i=0;i<width;i++){
			for(j=0;j<height;j++){
				grayDegree = (int) (sourceReader.getColor(i, j).getRed()*255);
				gray[grayDegree]++;
			}
		}
		
		double[] histgram1 = new double[256];           
	    double[] histgram2 = new double[256];     
	    for (i = 0; i < 256; i++)
	    {
	    	histgram1[i] = gray[i];
	    	histgram2[i] = gray[i];
	    	//System.out.print(gray[i]+" ");
	    }
	    int Iter = 0;
	    //ͨ���������ֵ��ƽ��ֱ��ͼ
        while (IsDimodal(histgram2) == false)           // �ж��Ƿ��Ѿ���˫���ͼ����      
        {
        	histgram2[0] = (histgram1[0] + histgram1[0] + histgram1[1]) / 3;                
            for (i = 1; i < 255; i++)
            	histgram2[i] = (histgram1[i - 1] + histgram1[i] + histgram1[i + 1]) / 3;    
            histgram2[255] = (histgram1[254] +histgram1[255] + histgram1[255]) / 3;         
           
            Iter++;
            if (Iter >= 1000){          // ֱ��ͼ�޷�ƽ��Ϊ˫��ģ�����ԭʼͼ��
            	System.out.println("can not find doublet!");
            	return source;
            }
        }
        boolean peakFound = false;
        for (int Y = 1; Y < 255; Y++)
        {
            if (histgram2[Y - 1] < histgram2[Y] && histgram2[Y + 1] < histgram2[Y]) 
            	peakFound = true;
            if (peakFound == true && 
            		histgram2[Y - 1] >= histgram2[Y] && 
            		histgram2[Y + 1] >= histgram2[Y]){
            	//YΪ��ֵ
            	for(i=0;i<width;i++){
        			for(j=0;j<height;j++){
        				grayDegree = (int) (sourceReader.getColor(i, j).getRed()*255);
        				if(grayDegree>Y)
        					targetWriter.setColor(i, j, Color.WHITE);
        				else
        					targetWriter.setColor(i, j, Color.BLACK);
        			}
        		}	
        		return target;
            }
        }
        System.out.println("failed!");
    	return source;
		
	}
	// ���ֱ��ͼ�Ƿ�Ϊ˫���
	private static boolean IsDimodal(double[] histgram) {
		// TODO Auto-generated method stub
		int Count = 0;
        for (int Y = 1; Y < 255; Y++)
        {
            if (histgram[Y - 1] < histgram[Y] && histgram[Y + 1] < histgram[Y])
            {
                Count++;
                if (Count > 2) return false;
            }
        }
        if (Count == 2)
            return true;
        else
            return false;
	}
	
	public static WritableImage Bernsen(WritableImage source){
		int width = (int)source.getWidth();
		int height = (int) source.getHeight();
		WritableImage target = new WritableImage(width,height);
		PixelWriter targetWriter = target.getPixelWriter();
		PixelReader sourceReader = source.getPixelReader();
		
		int w = 1;
		int i,j;
		int currentGray,arroundGray;
		int maxGray=0,minGray=255;
		int k,t;
		int bestGray;
		
		for(i=0;i<width;i++){
			for(j=0;j<height;j++){
				currentGray = (int) (sourceReader.getColor(i, j).getRed()*255);
				
				for(k=i-w;k<=i+w;k++){
					if(k<0 || k>=width)continue;
					for(t=j-w;t<=j+w;t++){
						if(t<0 || t>=height)continue;
						arroundGray = (int) (sourceReader.getColor(k, t).getRed()*255);
						if(arroundGray>maxGray) maxGray = arroundGray;
						if(arroundGray<minGray) minGray = arroundGray;
					}
				}
				bestGray = (minGray + maxGray ) / 2;
				
				if(currentGray==bestGray){
					if(currentGray>127)
						targetWriter.setColor(i, j, Color.rgb(255, 255, 255));
					else
						targetWriter.setColor(i, j, Color.rgb(0, 0, 0));
				}
				else if(currentGray>bestGray)
					targetWriter.setColor(i, j, Color.WHITE);
				else
					targetWriter.setColor(i, j, Color.BLACK);
			}
		}
		
		
		return target;
	}
}
