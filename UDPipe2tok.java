import java.io.*;
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.zip.GZIPInputStream;


public class UDPipe2tok {

	public static void main (String[] args) throws IOException {

		String fileName = args[0];
		BufferedReader d = new BufferedReader(new InputStreamReader(new FileInputStream(new File (fileName))));
		//OutputStreamWriter out = new OutputStreamWriter (new FileOutputStream(fileName+".1"), "UTF-8");

		String str = new String();
		str = d.readLine();
		/*
# sent_id = 2
# text = 친구하고 갔습니다.
1-2     친구하고        _       _       _       _       _       _       _       _
1       친구    친구    NOUN    NNG     _       _       _       _       _
2       하고    하고    ADP     JKB     _       _       _       _       _
3-5     갔습니다        _       _       _       _       _       _       _       SpaceAfter=No
3       가      가      VERB    VV      _       _       _       _       _
4       았      았      PRT     EP      _       _       _       _       _
5       습니다  습니다  PRT     EF      _       _       _       _       _
6       .       .       PUNCT   SF      _       _       _       _       SpacesAfter=\n
		 */
		String line = new String(); 
		while (str != null) {
			str = str.trim();
			if (str.startsWith("#")) {
				if (str.startsWith("# text = ")) {
					str = str.substring(str.indexOf("=")+1, str.length()).trim(); 
					//str = SJTree.reverse(str); 
					//System.out.print(str + "\t" ); 
				}
			}
			else if (str.contains("\t")) {

				String delim = "\t";
				String[] entry;
				entry = str.split(delim);
				if (entry[0].contains("-")) {}
				else {
					line += entry[1] + " ";
				}

			}
			else if (str.length()==0) {
				line = line.trim(); 
				//line = SJTree.reverse(line); 
				if (line.length()>0) System.out.println(line); 
				line = new String(); 
			}
			str = d.readLine();
		}
		d.close();
	}
}

/*
   StringTokenizer st = new StringTokenizer(str, delim);
   while (st.hasMoreTokens()) {
   String tok = st.nextToken();
   }
 */
/*
   Enumeration k = hash.keys();
   while(k.hasMoreElements()) {
   String key = (String) k.nextElement();
   System.out.println(key + "\t" + hash.get(key));
   }
 */
