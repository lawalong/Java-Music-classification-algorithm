package spider;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Download 
{
	
	 public  static void saveMUiscByURL(String music_url,String save_path,String type) //通过音乐的URL下载音乐文件
	 {
		  	  
		try {
			 URL url = new URL(music_url);
			 DataInputStream dis = new DataInputStream(url.openStream());  //使用DataInputStream下载url

			 String new_image_path=save_path+type; //保存路径
			  
			 FileOutputStream fos=new FileOutputStream(new File(new_image_path));
			 byte[] buffer=new byte[4096];
			 int length;
			  
			 while((length=dis.read(buffer))>0)
				 fos.write(buffer,0,length);
			  
			  dis.close();
			  fos.close();
			  System.out.println(new_image_path+" has been downloaded");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  
	 }
	
}
