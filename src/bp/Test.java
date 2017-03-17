package bp;

import io.TxtIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test 
{
	  /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException 
    {
        BPNeuralNet bp = new BPNeuralNet(72, 73, 3);

        //迭代200次进行训练
        for (int i = 0; i != 300; i++) 
        {
        	List<String> train_list=TxtIO.readTxtToList("trainfile\\train_0.txt");
        	for(String train:train_list)
        	{
        		double[] real=new double[3];
        		double[] music_feature=new double[72];
        		
//        		System.out.println(train);
        		String[] strs=train.split(" ");
        		if(strs[0].equals("+1"))
        		{
        			real[0]=1;
//        			System.out.println("classic");
        		}else if(strs[0].equals("-1"))
        		{
        			real[1]=1;
//        			System.out.println("easylistening");
        		}else if(strs[0].equals("+2"))
        		{
        			real[2]=1;
//        			System.out.println("folk");
        		}
        		
        		for(int j=1;j<=72;j++)
        		{
        			music_feature[j-1]=Double.parseDouble(strs[j].split(":")[1]);
        		}
        		
        		bp.train(music_feature, real);
        	}
        }
        
        System.out.println("训练完毕，下面请输入一个任意数字，神经网络将自动判断它是正数还是复数，奇数还是偶数。");

        
        List<String> test_list=TxtIO.readTxtToList("trainfile\\test_0.txt");
        
        for(String test:test_list)
        {
//        	System.out.println(test);
        	double[] music_feature=new double[72];
        	String[] strs=test.split(" ");
        	
        	double res=Double.parseDouble(strs[0]);
        	for(int j=1;j<=72;j++)
    		{
    			music_feature[j-1]=Double.parseDouble(strs[j].split(":")[1]);
    		}
        	
            double[] result = bp.test(music_feature);
//            System.out.println(result[0]);
//            System.out.println(result[1]);
//            System.out.println(result[2]);
//            System.out.println(result[3]);
//            System.out.println(result[4]);
            
	         double max = -Integer.MIN_VALUE;
	         int idx = -1;
	
	         for (int i = 0; i != result.length; i++) {
	             if (result[i] > max) {
	                 max = result[i];
	                 idx = i;
	             }
	         }
	         
	         switch (idx) 
	         {
	         	case 0:
	             System.out.println("classic"+res);
	             break;
	         case 1:
	        	 System.out.println("easylistening");
	             break;
	         case 2:
	        	 System.out.println("folk"+res);
	             break;
         }
        	
        }
//        while (true) 
//        {
//            byte[] input = new byte[10];
//            System.in.read(input);
//            Integer value = Integer.parseInt(new String(input).trim());
//            int rawVal = value;
//            double[] binary = new double[32];
//            int index = 31;
//            do {
//                binary[index--] = (value & 1);
//                value >>>= 1;
//            } while (value != 0);
//
//            double[] result = bp.test(binary);
//
//            double max = -Integer.MIN_VALUE;
//            int idx = -1;
//
//            for (int i = 0; i != result.length; i++) {
//                if (result[i] > max) {
//                    max = result[i];
//                    idx = i;
//                }
//            }
//
//            
//            //进行判断
//            switch (idx) {
//            case 0:
//                System.out.format("%d是一个正奇数\n", rawVal);
//                break;
//            case 1:
//                System.out.format("%d是一个正偶数\n", rawVal);
//                break;
//            case 2:
//                System.out.format("%d是一个负奇数\n", rawVal);
//                break;
//            case 3:
//                System.out.format("%d是一个负偶数\n", rawVal);
//                break;
//            }
//        }
    }
	
}
