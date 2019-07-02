import java.io.*;
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.zip.GZIPInputStream;

public class UDPipe2Espresso {

	public static void main (String[] args) throws IOException {

		String fileName = args[0]; // "/Users/park/Downloads/chosun.com/2018011800052.txt.udpiped"; //
		BufferedReader d = new BufferedReader(new InputStreamReader(new FileInputStream(new File (fileName))));
		String str = new String();
		str = d.readLine();

		String[][] ar = new String[2048][2];
		int id = 1;
		int index = -1;
		int start = 10000;
		int end = -1;
		boolean flag = false;
		int num = 0;

		String line = new String();
		String linePOS = new String();
		String space = new String();

		int dist = 0;

		while (str != null) {
			str = str.trim();

			//if (str.startsWith("#")) {
			if (str.length()==0) {


				if (id>1) {

					boolean no = false;
					for (int i=1; i<id; i++) {

						if (no) {
							ar[i-dist][0] += ar[i][0];
							String temp = ar[i-dist][1];
							if (temp.contains(",SpaceAfter")) temp = temp.substring(0, temp.indexOf(",SpaceAfter"));
							ar[i-dist][1] = temp;
							ar[i-dist][1] += "+" + ar[i][1];
							ar[i][0] = "__NULL__";
						}
						if (ar[i][1].endsWith("SpaceAfter=No"))  { no = true; dist++; }
						else {no = false; dist=0;}

					}

					String[][] arr = new String[id][2];
					int j=1;
					for (int i=1; i<id; i++) {

						if (!ar[i][0].contains("__NULL__")) {
							arr[j][0] = ar[i][0];
							arr[j][1] = ar[i][1];
							j++;
						}
					}

					for (int i=1; i<j; i++) {
						if (i==1) System.out.println(arr[i][0] + "\tBOS\t" + arr[i][1]);
						else if (i==j-1) System.out.println(arr[i][0] + "\tEOS\t" + arr[i][1]);
						else System.out.println(arr[i][0] + "\t\t" + arr[i][1]);
					}
					num++;
					//                    if (num>1) break;
				}
				ar = new String[2048][2];
				id = 1;

			}
			//else if (str.length()==0) {}
			else if (str.startsWith("#")) {}
			else {

				String delim = "\t";
				String[] entry;
				entry = str.split(delim);

				String idx = entry[0];
				String word = entry[1];
				String pos = entry[4];
				String mor = entry[3];
				String after = entry[9];
				if (after.contains("SpaceAfter=")) {
					flag  = true;
					space = after;
				}

				if (!entry[0].contains("-")) {

					index = Integer.parseInt(idx);
					if (index>=start && index <=end) {
						linePOS += "+" + word + "/" + pos;

						if (index == end) {
							if (linePOS.startsWith("+")) linePOS = linePOS.substring(1, linePOS.length());
							ar[id][0] = line;
							ar[id][1] = linePOS;
							if (flag) {
								ar[id][1] +=  "," + space; flag = false;
							}
							id++;

							start = 10000;
							end = -1;
							//                            flag = false;
							line = new String();
							linePOS = new String();
						}
						//                        System.out.println("<<<" + str);
					}
					else {

						if (ar[id][1] != null) {
							ar[id][0] += word;
							ar[id][1] += "+" + word + "/" + pos;
						}
						else {
							ar[id][0] = word;
							ar[id][1] = word + "/" + pos;
						}
						//            id++;
						if (flag) {
							ar[id][1] +=  "," + space; flag = false;
						}

						id++;
					}
				}
				else {
					String s = idx.substring(0,idx.indexOf("-"));
					start = Integer.parseInt(s);
					s = idx.substring(idx.indexOf("-")+1,idx.length());
					end = Integer.parseInt(s);
					//                    System.out.println("!!!" + start + "\t" + end);
					line = word;
				}

				if (start >0) {
				}
			}
			str = d.readLine();
		}
		d.close();
		}
	}


	/*
	   public static void main (String[] args) throws IOException {

	   String fileName = "/Users/park/Downloads/chosun.com/2018011800052.txt.udpiped"; // args[0];
	   BufferedReader d = new BufferedReader(new InputStreamReader(new FileInputStream(new File (fileName))));
	   String str = new String();
	   str = d.readLine();

	   String[][] ar = new String[2048][2];
	   int id = 1;
	   int index = -1;
	   int start = 10000;
	   int end = -1;
	   boolean flag = false;

	   int num = 0;

	   String line = new String();
	   String linePOS = new String();

	   while (str != null) {
	   str = str.trim();
	   System.out.println("@@@" + str);;

	   if (str.startsWith("#")) {

	   if (id>1) {
	   for (int i=1; i<id; i++) {
	   if (i==1) System.out.println(ar[i][0] + "\tBOS\t" + ar[i][1]);
	   else if (i==id-1) System.out.println(ar[i][0] + "\tEOS\t" + ar[i][1]);
	   else System.out.println(ar[i][0] + "\t\t" + ar[i][1]);
	   }
	   num++;
	   if (num>1) break;
	   }
	   ar = new String[2048][2];
	   id = 1;

	   }
	   else if (str.length()==0) {}
	   else {

	   String delim = "\t";
	   String[] entry;
	   entry = str.split(delim);

	   String idx = entry[0];
	   String word = entry[1];
	   String pos = entry[4];
	   String mor = entry[3];
	   String space = entry[9];
	   if (space.equals("SpaceAfter=No")) flag = true;

	   if (!entry[0].contains("-")) {

	   index = Integer.parseInt(idx);
	   if (index>=start && index <=end) {
	   linePOS += "+" + word + "/" + pos;

	   if (index == end) {
	   if (linePOS.startsWith("+")) linePOS = linePOS.substring(1, linePOS.length());
	   ar[id][0] = line;
	   ar[id][1] = linePOS;
	   if (!flag) id++;
	   else {}

	   start = 10000;
	   end = -1;
	   flag = false;
	   line = new String();
	   linePOS = new String();
}
System.out.println("<<<" + str);
}
else {

	if (ar[id][1] != null) {
		ar[id][0] += word;
		ar[id][1] += "+" + word + "/" + pos;
	}
	else {
		ar[id][0] = word;
		ar[id][1] = word + "/" + pos;
	}
	//            id++;
	if (!flag) { }
	//						else {}
	id++;
}
}
else {
	String s = idx.substring(0,idx.indexOf("-"));
	start = Integer.parseInt(s);
	s = idx.substring(idx.indexOf("-")+1,idx.length());
	end = Integer.parseInt(s);
	System.out.println("!!!" + start + "\t" + end);
	line = word;
}

if (start >0) {
}
}
str = d.readLine();
}
d.close();
}
*/
