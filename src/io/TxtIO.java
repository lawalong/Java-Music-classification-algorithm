package io;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class TxtIO 
{
	private static Scanner sc;
	
	public static void writeToTxt(String path,String labs)
	{
		try 
		{		
			
			BufferedWriter bw=new BufferedWriter(new FileWriter(path,true));
			bw.write(labs);
			bw.newLine();
			bw.close();
//			System.out.println("baidu.txt已经写入    "+labs);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void writeToTxtWithDeleteIfExists(String path,String labs)
	{
		try 
		{		
			Files.deleteIfExists(Paths.get(path));
			BufferedWriter bw=new BufferedWriter(new FileWriter(path,true));
			bw.write(labs);
			bw.newLine();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeToTxt(String path,List<String> list)
	{
		
		try 
		{	
			Files.deleteIfExists(Paths.get(path));
			
			int size=list.size();
			BufferedWriter bw=new BufferedWriter(new FileWriter(path,true));
			for(int i=0;i<size;i++)
			{
				bw.write(list.get(i));
				bw.newLine();
			}

			bw.close();	
			
			System.out.println("已经全部写入"+path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> readTxtToList(String path)
	{
		ArrayList<String> list=new ArrayList<String>();
		try {
			sc = new Scanner(new FileReader(path));
			String tmplabs;
			while(sc.hasNextLine())
			{
				tmplabs=sc.nextLine();
				list.add(tmplabs);
			//	System.out.println(tmplabs);
			}		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<String> readTxtToList(File file)
	{
		ArrayList<String> list=new ArrayList<String>();
		try {
			sc = new Scanner(file);
			String tmplabs;
			while(sc.hasNextLine())
			{
				tmplabs=sc.nextLine();
				list.add(tmplabs);
			//	System.out.println(tmplabs);
			}		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static int[][] readTxtMatrix(String path)
	{
		ArrayList<String> strlist=new ArrayList<String>();
	
		try {
		
			sc = new Scanner(new FileReader(path));
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tmp_line;
		int row=0;
		
		while(sc.hasNextLine())
		{
			row++;
			tmp_line=sc.nextLine();
			strlist.add(tmp_line);	
		}		
			
		int column=strlist.get(0).split(",").length;
		System.out.println(row);
		System.out.println(column);
		
		int[][] a=new int[row][column];
		for(int i=0;i<row;i++)
		{
			System.out.println(strlist.get(i));
			String[] tmp_strs=(strlist.get(i)).split(",");
			
			for(int j=0;j<column;j++)
			{
				System.out.print(tmp_strs[j]);
				a[i][j]=Integer.parseInt(tmp_strs[j]);
			}
			System.out.println();
		}

		return a;
	}
	
	
	public static String readTxTAll(String path)
	{
	
		FileReader fr;
		try {
			fr = new FileReader(path);
			BufferedReader br=new BufferedReader(fr);
			StringBuffer sb=new StringBuffer();
			String tmp_str="";
			while((tmp_str=br.readLine())!=null)
				sb.append(tmp_str+"\n");
			br.close();
			
			return sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	     
		return "";
		
	}
	
	
}
