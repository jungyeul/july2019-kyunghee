import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.net.*;

public class MakeBerkeleyTestWithPOSIn {

	public static void main (String[] args) throws IOException {

		String fileName = args[0];

		BufferedReader d
			= new BufferedReader(new InputStreamReader(new FileInputStream(new File (fileName) )));
		//File outputFile = new File(fileName + ".pos");
		//OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outputFile),"UTF-8");

		String str = new String();

		str = d.readLine();
		while (str != null) {
			str = str.trim();
			String token = str.substring(str.lastIndexOf("\t"), str.length()).trim();
			String delim = "\\+";
			String[] entry;
			entry = token.split(delim);

			for (int i =0; i< entry.length; i++) {
				String t = entry[i];
				if (t.contains("/")) {
					String han = t.substring(0, t.indexOf("/")).trim();
					String pos = t.substring(t.indexOf("/")+1, t.length()).trim();
					if (pos.equals("PRON")) pos = "NP"; 
					System.out.println(han + "\t" + pos);
				}
				else System.err.println(t + "\t" + "NA");
			}

			if (str.contains("EOS")) System.out.println();

			str = d.readLine();
		}

		d.close(); //out.close();
	}


}

