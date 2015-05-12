package fer.unizg.ui.lab3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		System.out.println("-----------Begin---------");
		// TODO Auto-generated method stub
		BufferedReader reader=null;
		try {
			reader=new BufferedReader(new FileReader("inputLab3.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExpertKnowledgeBase base=ExpertKnowledgeBase.parseInputFromReader(reader);
		System.out.println(base);
		BufferedReader inputReader=new BufferedReader(new InputStreamReader(System.in));
		base.resolve(inputReader);
		System.out.println("-----------End-----------");
	}

}
