package blurry;
import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.*;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import org.opencv.highgui.*;
import javax.swing.*;
import java.awt.image.*;

public class Hello {
   public static void main( String[] args ){
	   String file = "/home/ayush/Pictures/Webcam/4.jpg";
	   System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	   Mat src = Imgcodecs.imread(file);
//	   Mat dst = new Mat();
//	   Size sz = new Size(500,500);
//	   Imgproc.resize(src,dst,sz);
	   Mat gray = new Mat();
	   Imgproc.cvtColor(src,gray ,Imgproc.COLOR_BGR2GRAY);
	   Mat blurred = new Mat();
	   Imgproc.Laplacian(gray, blurred, gray.depth(),3,3);
	   System.out.println(gray.depth());	   
	   BufferedImage bi = Mat2BufferedImage(blurred);
	   
	   double a = mean(bi);
	   double b = variance(bi);
	   System.out.println("Mean is "+ a);
	   System.out.println("Variance is "+ b);
       displayImage(bi);
	   
//	   imageCodecs.imwrite(file2, dst);
//	   
	   System.out.println("ayush");
	   

   }
   public static Mat LaplacianContrast(Mat img) {
		Mat laplacian = new Mat();
		Imgproc.Laplacian(img, laplacian, img.depth());
		//Imgproc.Laplacian(img, laplacian, img.depth(), 3, 1, 0);
		Core.convertScaleAbs(laplacian, laplacian);
		return laplacian;
	}
   public static BufferedImage Mat2BufferedImage(Mat m)
   {
       int type = BufferedImage.TYPE_BYTE_GRAY;
       if (m.channels() > 1)
       {
           type = BufferedImage.TYPE_3BYTE_BGR;
       }
       int bufferSize = m.channels()*m.cols()*m.rows();
       byte[] b = new byte[bufferSize];
       m.get(0, 0, b); // get all the pixels
       BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
       final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
       System.arraycopy(b, 0, targetPixels, 0, b.length);  
       return image;
   }
   public static void displayImage(Image img)
   {   
       ImageIcon icon = new ImageIcon(img);
       JFrame frame = new JFrame();
       frame.setLayout(new FlowLayout());        
       frame.setSize(img.getWidth(null)+50, img.getHeight(null)+50);     
       JLabel lbl = new JLabel();
       lbl.setIcon(icon);
       frame.add(lbl);
       frame.setVisible(true);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   public static double mean(BufferedImage image)
   {
	   double sum = 0;
	   Raster raster = image.getRaster();
	   for (int y = 0; y < image.getHeight(); ++y)
		   for (int x = 0; x < image.getWidth(); ++x)
			   sum += raster.getSample(x, y, 0) ;
	   return sum / (image.getWidth() * image.getHeight());
   }
   public static double variance(BufferedImage image) {
	    double mean = mean(image);
	    double sumOfDiff = 0.0;
	    double n = 0.0;
	   Raster raster = image.getRaster();

	    for (int y = 0; y < image.getHeight(); ++y) {
	        for (int x = 0; x < image.getWidth(); ++x) {
	        		n = raster.getSample(x, y, 0) ;
	        		n-= mean;
	        		sumOfDiff += Math.pow(n, 2);
	         }
	    }
	    return sumOfDiff / ((image.getWidth() * image.getHeight()) - 1);
	}

}

