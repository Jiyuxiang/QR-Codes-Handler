package com.pd.qrcode;
/*
 * 调用主要功能函数的UI面板
 * */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class QRCodeHandler extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	private ImageView fileImageView;
	private WritableImage fileCodeImage;
	private WritableImage codeImage;
	private Text result;

	public void start(final Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//设置控件
		Label title = new Label("视觉码的预处理和解码");
		title.setFont(Font.font("Cambria", FontWeight.BOLD,32));
		title.setTextFill(Color.web("#0076a3")); 
		title.setAlignment(Pos.CENTER);
		
		Label first = new Label();
		Image imageFirst = new Image(getClass().getResourceAsStream("first.png"));  
		first.setGraphic(new ImageView(imageFirst));
		
		Label second = new Label();
		Image imageSecond = new Image(getClass().getResourceAsStream("second.png"));  
		second.setGraphic(new ImageView(imageSecond));
		
		Label third = new Label();
		Image imageThird = new Image(getClass().getResourceAsStream("third.png"));  
		third.setGraphic(new ImageView(imageThird));
		
		Label fourth = new Label();
		Image imageFourth = new Image(getClass().getResourceAsStream("fourth.png"));  
		fourth.setGraphic(new ImageView(imageFourth));
		
		Text text1 = new Text("从文件中选择一副二维码图片…");
		text1.setFont(Font.font(20));
		
		Text text2 = new Text("选择灰度化算法");
		text2.setFont(Font.font(20));
		
		Text text3 = new Text("选择二值化算法");
		text3.setFont(Font.font(20));
		
		Text text4 = new Text("选择边缘检测算法");
		text4.setFont(Font.font(20));
		
		Text text5 = new Text("解码结果：");
		text5.setFont(Font.font(20));
		
		Text text6 = new Text("选择边缘检测算法");
		text6.setFont(Font.font(20));
		
		Text explain1 = new Text("(按人眼对颜色的敏感程度加权：");
		Text explain2 = new Text("red 0.30，green 0.59， blue 0.11)");
		VBox explain = new VBox();
		explain.setSpacing(10);
		explain.getChildren().addAll(explain1,explain2);
		
		
		result = new Text();
		result.setFont(Font.font("Arial", 20));
		
		Button openFileBtn = new Button("浏览");
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("打开一个二维码");
		//fileChooser.setInitialDirectory(new File("F:\\胖丢科创\\from panger\\二维码实例")); 
		openFileBtn.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					try {
						loadImage(file);
						String path = file.getAbsolutePath();
						String nextPath = path.substring(0, path.lastIndexOf('\\'));
						fileChooser.setInitialDirectory(new File(nextPath)); 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }	
			}
		});
		Button recover = new Button("恢复");
		recover.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				fileImageView.setImage(fileCodeImage);
				codeImage = fileCodeImage;
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}
		});
		
		fileImageView = new ImageView();
		
		Button redPortionBtn = new Button("红色分量法");
		redPortionBtn.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				GrayImage gray = null;
				try {
					gray = new GrayImage(fileCodeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				codeImage = gray.Components(1);
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}

		});
		
		Button greenPortionBtn = new Button("绿色分量法");
		greenPortionBtn.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				GrayImage gray = null;
				try {
					gray = new GrayImage(fileCodeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				codeImage = gray.Components(2);
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}

		});
		
		Button bluePortionBtn = new Button("蓝色分量法");
		bluePortionBtn.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				GrayImage gray = null;
				try {
					gray = new GrayImage(fileCodeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				codeImage = gray.Components(3);
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}

		});
		
		Button maxValue = new Button("最大值法");
		maxValue.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				GrayImage gray = null;
				try {
					gray = new GrayImage(fileCodeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				codeImage = gray.MaxValue();
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}

		});
		
		Button average = new Button("平均值法");
		average.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				GrayImage gray = null;
				try {
					gray = new GrayImage(fileCodeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				codeImage = gray.Average();
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}

		});
		
		Button weightedAverage = new Button("加权平均法");
		weightedAverage.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				GrayImage gray = null;
				try {
					gray = new GrayImage(fileCodeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				codeImage = gray.weightedAverage(0.3, 0.59, 0.11);
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}

		});
		
		Button wholeOtsu = new Button("全局Otsu算法");
		wholeOtsu.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				codeImage = Binarization.Otsu(codeImage);
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}
		});
		
		Button iterate = new Button("迭代算法");
		iterate.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				codeImage = Binarization.Iterate(codeImage);
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}
		});
		
		Button doublet = new Button("谷底最小值算法(可能无效)");
		doublet.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				codeImage = Binarization.Doublet(codeImage);
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}
		});
		
		Button bersen = new Button("bernsen算法");
		bersen.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				codeImage = Binarization.Bernsen(codeImage);
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}
		});
		Button roberts = new Button("使用reberts算子检测边缘");
		roberts.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				 //TODO Auto-generated method stub
				codeImage = EdgeDetect.Roberts(codeImage);
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}
		});
		Button uncurl = new Button("边缘平直化");
		uncurl.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				 //TODO Auto-generated method stub
				codeImage = EdgeDetect.Uncurl(codeImage);
				fileImageView.setImage(codeImage);
				MyQRCodeDecoder decoder = null;
				try {
					decoder = new MyQRCodeDecoder(codeImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.setText(decoder.getMessage());
			}
		});
		
		//布置面板
		BorderPane border = new BorderPane();
		
		HBox hboxTop = new HBox();
		border.setTop(hboxTop);
		hboxTop.setPadding(new Insets(20, 12, 20, 12));
		hboxTop.setStyle("-fx-background-color: #BEBEBE;");
		hboxTop.setSpacing(10);
		//hboxTop.setAlignment(Pos.CENTER);
		hboxTop.getChildren().addAll(text5, result);
		
		
		VBox vboxCenter = new VBox();
		border.setCenter(vboxCenter);
		vboxCenter.setPadding(new Insets(5, 12, 5, 12));
		vboxCenter.setStyle("-fx-background-color: #BEBEBE;");
		vboxCenter.setSpacing(10);
		vboxCenter.getChildren().addAll(first,text1,openFileBtn,fileImageView,recover);
		
		HBox portion = new HBox();
		portion.setSpacing(10);
		portion.getChildren().addAll(redPortionBtn,greenPortionBtn,bluePortionBtn);
		
		VBox vboxLeft = new VBox();
		border.setLeft(vboxLeft);
		vboxLeft.setPadding(new Insets(5, 12, 5, 12));
		vboxLeft.setSpacing(30);
	    vboxLeft.setStyle("-fx-background-color: #A4D3EE;");
	    vboxLeft.setAlignment(Pos.CENTER);
	    vboxLeft.getChildren().addAll(second,text2,portion,maxValue,average,weightedAverage,explain);
	    
	    VBox vboxRight = new VBox();
		vboxRight.setPadding(new Insets(5, 12, 5, 12));
		vboxRight.setSpacing(30);
		vboxRight.setStyle("-fx-background-color: #A4D3EE;");
		vboxRight.setAlignment(Pos.CENTER);
		vboxRight.getChildren().addAll(third,text3,wholeOtsu,iterate,doublet,bersen);
	    border.setRight(vboxRight);
		
	    HBox hboxBottom = new HBox();
	    border.setBottom(hboxBottom);
	    hboxBottom.setPadding(new Insets(20, 30, 20, 30));
	    hboxBottom.setSpacing(10);
	    hboxBottom.setAlignment(Pos.CENTER);
	    hboxBottom.setStyle("-fx-background-color: #9F79EE;");
	    hboxBottom.getChildren().addAll(fourth,text6,roberts,uncurl);
		
	    
	    //vboxBottom.setPadding(new Insets(20, 30, 20, 30));
	    
	    
		Scene scene = new Scene(border,800,650);
	    primaryStage.setTitle("视觉二维码的预处理与识别");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

	
	protected void loadImage(File file) throws IOException {
		// TODO Auto-generated method stub
		BufferedImage fileImage = null;
		try {
			fileImage = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int wid = fileImage.getWidth();
		int hei = fileImage.getHeight();
		fileCodeImage = new WritableImage(wid,hei);
		PixelWriter fileImageWriter = fileCodeImage.getPixelWriter();
		int rgb,r,g,b;
		for(int i=0;i<wid;i++){
			for(int j=0;j<hei;j++){
				fileImageWriter.setColor(i, j, Color.WHITE);
				rgb = fileImage.getRGB(i, j);
				r = (rgb & 0xff0000) >> 16;  
            	g = (rgb & 0xff00) >> 8;  
            	b = (rgb & 0xff);  
            	fileImageWriter.setColor(i, j, Color.rgb(r, g, b));
			}
		}
		codeImage = fileCodeImage;
		fileImageView.setImage(fileCodeImage);
		fileImageView.setFitWidth(250);
		fileImageView.setPreserveRatio(true);
		MyQRCodeDecoder decoder = new MyQRCodeDecoder(fileCodeImage);
		result.setText(decoder.getMessage());
	}
	
}
