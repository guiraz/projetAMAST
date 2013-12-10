package asimilProject;

import java.io.*;
import java.util.*;

public class BDTrainee {
	private static List<Integer> _id_traineeaction;
	private static List<Integer> _id_pedagogy;
	private static List<Integer> _id_action;
	private static List<Integer> _timeelapsed_traineeaction;
	private static List<String> _errormessage_traineeaction;
	private static List<Integer> _gravity_traineeaction;
	
	public static int getCount() {
		return _id_traineeaction.size();
	}
	
	public static void create() {
		_id_traineeaction = new ArrayList<Integer>();
		_id_pedagogy = new ArrayList<Integer>();
		_id_action = new ArrayList<Integer>();
		_timeelapsed_traineeaction = new ArrayList<Integer>();
		_errormessage_traineeaction = new ArrayList<String>();
		_gravity_traineeaction = new ArrayList<Integer>();
	}
	
	public static Integer getIdTrainneAction(int i) {
		return _id_traineeaction.get(i);
	}
	
	public static Integer getIdPedagogy(int i) {
		return _id_pedagogy.get(i);
	}
	
	public static Integer getIdAction(int i) {
		return _id_action.get(i);
	}
	
	public static Integer getTimeElapsed(int i) {
		return _timeelapsed_traineeaction.get(i);
	}
	
	public static String getErrorMessage(int i) {
		return _errormessage_traineeaction.get(i);
	}
	
	public static Integer getGravity(int i) {
		return _gravity_traineeaction.get(i);
	}
	
	public static void setIdTrainneAction(Integer i) {
		_id_traineeaction.add(i);
	}
	
	public static void setIdPedagogy(Integer i) {
		_id_pedagogy.add(i);
	}
	
	public static void setIdAction(Integer i) {
		_id_action.add(i);
	}
	
	public static void setTimeElapsed(Integer i) {
		_timeelapsed_traineeaction.add(i);
	}
	
	public static void setErrorMessage(String s) {
		_errormessage_traineeaction.add(s);
	}
	
	public static void setGravity(Integer i) {
		_gravity_traineeaction.add(i);
	}
	
	public static void exportDB(String s) {
		try {
			File f = new File(s+".txt");
			FileWriter fw = new FileWriter(f);
			
			for(int i=0; i<getCount(); i++) {
				fw.write(getIdTrainneAction(i).toString()+"\n");
				fw.write(getIdPedagogy(i).toString()+"\n");
				fw.write(getIdAction(i).toString()+"\n");
				fw.write(getTimeElapsed(i).toString()+"\n");
				fw.write(getErrorMessage(i)+"\n");
				fw.write(getGravity(i).toString()+"\n");
				fw.write("~~\n");
			}
			System.out.println(f.getAbsolutePath());
			fw.close();
		}
		catch (Exception e) {
			System.err.println("No can open file : " + e);
		}
	}
	
	public static void importDB(String s) {
		try{
			InputStream ips=new FileInputStream(s+".txt"); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				if(!ligne.equals("~~")) {
					setIdTrainneAction(Integer.parseInt(ligne));
					ligne=br.readLine();
					setIdPedagogy(Integer.parseInt(ligne));
					ligne=br.readLine();
					setIdAction(Integer.parseInt(ligne));
					ligne=br.readLine();
					setTimeElapsed(Integer.parseInt(ligne));
					ligne=br.readLine();
					setErrorMessage(ligne);
					ligne=br.readLine();
					setGravity(Integer.parseInt(ligne));
				}
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
}
