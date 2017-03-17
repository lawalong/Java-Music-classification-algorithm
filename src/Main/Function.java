/**
 * Project: Music classifier
 * Author: Kevin Li (lawalong@gmail.com)
 * Date: 2015-5-2
 * Description: The sample program to classifier music
 * Algorithm: Neural network 
 */
package Main;

import io.TxtIO;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;
import jAudioFeatureExtractor.Controller;
import jAudioFeatureExtractor.DataModel;
import jAudioFeatureExtractor.ExtractionThread;
import jAudioFeatureExtractor.ModelListener;
import jAudioFeatureExtractor.ACE.DataTypes.Batch;
import jAudioFeatureExtractor.Aggregators.Aggregator;
import jAudioFeatureExtractor.DataTypes.RecordingInfo;
import jAudioFeatureExtractor.jAudioTools.FeatureProcessor;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import bp.BPNeuralNet;

import util.WaveFileReader;

import libsvm.*;

public class Function 
{
	
	public static void main(String[] args) throws IOException  
	{
		

		System.out.println(Function2("music.wav",0));
		System.out.println(Function2("1.wav",0));
		System.out.println(Function2("2.wav",0));
		System.out.println(Function2("3.wav",0));
		System.out.println(Function2("4.wav",0));
		System.out.println(Function2("5.wav",0));
		System.out.println(Function2("6.wav",0));
		System.out.println(Function2("7.wav",0));
	
	
	}
	
	
	public static String Function2(String wav_path,int classifier)
	{
		try {
			
			/*
			 * JAudio
			 */
			boolean each_window = false;
			boolean overall = true;
			String feature_values_save_path = "feature_values.xml"; //feature_value
			String feature_definitions_save_path = "feature_definition.xml";
			int window_size = 512;
			double window_overlap = 0.0;
			boolean normalise = false;
			double sampling_rate = 22.05*1000;
			int outputType = 0;
		
			
			File f=new File(wav_path);
			System.out.println(f.getAbsolutePath());
			
			RecordingInfo[] recordings = new RecordingInfo[]{
					new RecordingInfo(f.getName(),f.getPath(), null, false),
					new RecordingInfo(f.getName(),f.getPath(), null, false)
			};
			
			DataModel dm=new DataModel("features.xml", null);
			
			dm.featureKey=new FileOutputStream(new File(feature_definitions_save_path));
			dm.featureValue=new FileOutputStream(new File(feature_values_save_path));
			dm.extract(window_size,window_overlap, sampling_rate, normalise, each_window,overall, recordings, outputType);
			
			
			String music_features="";
			SAXReader saxReader = new SAXReader();
			Document document= saxReader.read(new File("feature_values.xml"));
			
			
			Element root = document.getRootElement();
	        System.out.println("Root: " + root.getName());
			
	     // 
	        List<Element> childList = root.elements();
	        System.out.println("total child count: " + childList.size());
	        
	        // 
	        List<Element> data_set_list= root.elements("data_set");
	        System.out.println("hello child: " + data_set_list.size());
	      
        	List<Element> feature_list= data_set_list.get(0).elements("feature");
//	        	System.out.println("feature" + feature_list.size());
        	
        	int i=1;
        	int j=1;
        	System.out.print("+1.0 ");
        	music_features=music_features+"+1.0 ";
        	
        	for(Element feature:feature_list)
        	{
        		List<Element> v_list= feature.elements("v");
        		
        		for(Element v:v_list)
        		{
        			System.out.print((i++)+":"+v.getText()+" ");
        			music_features=music_features+(j++)+":"+v.getText()+" ";
        		}
        	}
        	
        	System.out.println();
        	TxtIO.writeToTxtWithDeleteIfExists("trainfile/test00.txt", music_features);
        	
        	if(classifier==0)
        	{
	        	
	   
	    		String[] arg={"trainfile/easylistening.txt", //
	    				"trainfile/model_r.txt" //
	    		};
	    		
	    		String[] parg={"trainfile/test00.txt", //
	    				"trainfile/model_r.txt",  //
	    				"trainfile/out_r.txt"//
	    		};
	    		
	    		System.out.println("Beginning");
	    		svm_train t=new svm_train();
	    		svm_predict p=new svm_predict(); //
	    		t.main(arg); //
	    		p.main(parg); //
	    		
	    		String type=TxtIO.readTxTAll("trainfile/out_r.txt");
	    	
	    		if(type.trim().equals("1.0"))
	    			return "classic";
	    		else if(type.trim().equals("-1.0"))
	    			return "easylistening";  //jazz 
	    		else if(type.trim().equals("2.0"))
	    			return "folk";  //easylistening
	    		else if(type.trim().equals("-2.0"))
	    			return "jazz";  //folk
	    		else if(type.trim().equals("3.0"))
	    			return "pop";
	    		
	    		return "other";
		        
		}

        	
		if(classifier==1)
		{
			 BPNeuralNet bp = new BPNeuralNet(72, 73, 5);

		        //
		        for (int ii = 0; ii != 300; ii++) 
		        {
		        	List<String> train_list=TxtIO.readTxtToList("trainfile/train_0.txt");
		        	for(String train:train_list)
		        	{
		        		double[] real=new double[5];
		        		double[] music_feature=new double[72];
		        		
//		        		System.out.println(train);
		        		String[] strs=train.split(" ");
		        		if(strs[0].equals("+1"))
		        		{
		        			real[0]=1;
//		        			System.out.println("classic");
		        		}else if(strs[0].equals("-1"))
		        		{
		        			real[1]=1;
//		        			System.out.println("easylistening");
		        		}else if(strs[0].equals("+2"))
		        		{
		        			real[2]=1;
//		        			System.out.println("jazz");
		        		}else if(strs[0].equals("-2"))
		        		{
		        			real[3]=1;
//		        			System.out.println("folk");
		        		}else if(strs[0].equals("+3"))
		        		{
		        			real[4]=1;
//		        			System.out.println("pop");
		        		}
		        		
		        		
		        		
		        		for(int jj=1;jj<=72;jj++)
		        		{
		        			music_feature[jj-1]=Double.parseDouble(strs[jj].split(":")[1]);
		        		}
		        		
		        		bp.train(music_feature, real);
		        	}
		        }
		        
		        
		        List<String> test_list=TxtIO.readTxtToList("trainfile/test00.txt");
		        
		        for(String test:test_list)
		        {
//		        	System.out.println(test);
		        	double[] music_feature=new double[72];
		        	String[] strs=test.split(" ");
		        	
		        	for(int jj=1;jj<=72;jj++)
		    		{
		    			music_feature[jj-1]=Double.parseDouble(strs[jj].split(":")[1]);
		    		}
		        	
		            double[] result = bp.test(music_feature);
//		            System.out.println(result[0]);
//		            System.out.println(result[1]);
//		            System.out.println(result[2]);
//		            System.out.println(result[3]);
//		            System.out.println(result[4]);
		            
			         double max = -Integer.MIN_VALUE;
			         int idx = -1;
			
			         for (int ii = 0; ii != result.length; ii++) {
			             if (result[ii] > max) {
			                 max = result[ii];
			                 idx = ii;
			             }
			         }
			         
			         if(idx==0)
			        	 return "classic";
			         else if(idx==1)
			        	 return "jazz";
			         else if(idx==2)
			        	 return "easylistening";
			         else if(idx==3)
			        	 return  "folk";
			         else if(idx==4)
			        	 return "pop";
			         else
			        	  return "other";
			       
		        }
			
		}
		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
