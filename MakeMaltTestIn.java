import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.net.*;

public class MakeMaltTestIn {

	public static void main (String[] args) throws IOException {

		String fileName = args[0];

		BufferedReader d
			= new BufferedReader(new InputStreamReader(new FileInputStream(new File (fileName) )));
		//File outputFile = new File(fileName + ".pos");
		//OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outputFile),"UTF-8");


		String str = new String();
		String prn = new String();

		int id = 1;

		str = d.readLine();
		while (str != null) {
			str = str.trim();
			str = SJTree.cleaning(str);
			str = str.replaceAll("_", "-");



			if (str.length()==0) {}
			else {
				String delim = "\t";
				String[] entry;
				entry = str.split(delim);

				String word = entry[2];

				String delimPlus = "\\+";
				String[] pos;
				pos = word.split(delimPlus);

				String tail = new String();
				String lexicalHead = new String();
				String lexicalPOS = new String();
				String functionalPOS = new String();

				int funcPOSStart = -1;

				boolean flag = true;
				for (int i=0; i<pos.length;i++) {
					String t = pos[i];
					String tt = t.substring(t.indexOf("/")+1, t.length()) + "+";
					tail += tt;
					if ((tt.startsWith("J") || tt.startsWith("E") || ( tt.startsWith("S") && i!=0) ) && flag) {
						funcPOSStart = i;
						flag =false;
					}
				}
				flag = true;

				tail = tail.substring(0, tail.length()-1);



				// 2 합주에서는  합주   NNG 		 NNG+JKB+JX  JKB|JX     9       NP-SBJ  _  _
				if (funcPOSStart == -1) {
					lexicalHead = entry[0];
					lexicalPOS = tail;
				}
				else {
					for (int i=0; i<funcPOSStart;i++) {
						String t = pos[i].trim();;
						//System.err.println(t);
						if (t.length()>0) {
							lexicalHead += t.substring(0, t.indexOf("/")); 
							lexicalPOS += t.substring(t.indexOf("/")+1, t.length()) + "+";
						}
					}
					if (lexicalPOS.length()>0) lexicalPOS = lexicalPOS.substring(0, lexicalPOS.length()-1);
					else lexicalPOS = "_";
					if (lexicalHead.length()==0) {lexicalHead = "_";}


					for (int i=funcPOSStart; i<pos.length;i++) {
						String t = pos[i];
						functionalPOS += t.substring(t.indexOf("/")+1, t.length()) + "|";
					}
					functionalPOS = functionalPOS.substring(0, functionalPOS.length()-1);
				}

				if (functionalPOS.length()==0) functionalPOS = "_";

				//전체    BOS     전체/NNG
				String toto = entry[0];
				toto = toto.replaceAll("\\+", "*PLUS*");
				toto = toto.replaceAll("\\/", "*SLASH*");
				toto = toto.replaceAll("\\-", "*MINUS*");
				toto = toto.replaceAll("\\_", "*UNDERBAR*");

				toto = toto.replaceAll("\\(", "*LRB*");
				toto = toto.replaceAll("\\)", "*RRB*");

				toto = toto.replaceAll("\"", "*DQUOTE*");
				toto = toto.replaceAll("'", "*SQUOTE*");
				toto = toto.replaceAll("<", "*LT*");
				toto = toto.replaceAll(">", "*GT*");
				entry[0] = toto;


				//9       가감된다.       가감/NNG+되/XSV+ㄴ다/EF+./SF    NNG+XSV+EF+SF   NNG+XSV+EF+SF   _


				//// 2 합주에서는  합주   NNG 		 NNG+JKB+JX  JKB|JX     9       NP-SBJ  _  _
				//					prn += entry[0] + "\t"+entry[3] + "\t" + lexicalHead +"\t"+ lexicalPOS 
				//						+ "\t" +  tail + "\t" + functionalPOS + "\t"  + entry[1] + "\t" + entry[2] + "\t_" + "\t_" + "\n";

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				System.out.println(id++ + "\t" + entry[0] + "\t" + lexicalHead + "\t" + lexicalPOS 
						+ "\t" +  tail + "\t" + functionalPOS);
				if (str.contains("EOS")) {
					System.out.println();
					prn = new String();
					id=1;
				}

			}

			str = d.readLine();
		}

		d.close(); //out.close();
	}
}

