import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
/**
 * 图片加载类
 */
public class GetImage {
	private GetImage() {
	}
	public static Image getImage(String path) {
		BufferedImage res=null;
		try {
			URL url=GetImage.class.getClassLoader().getResource(path);		//图片路径
			res=ImageIO.read(url);
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "图片加载异常："+e,"异常",JOptionPane.ERROR_MESSAGE);
		}
		return res;
	}
}
