import java.io.*;
import java.util.*;
import java.util.regex.*;

public class SJTree {


	public static  String functs(String str) {
		String line = new String();
		String delim = "()";
		StringTokenizer st = new StringTokenizer(str, delim, true);
		while (st.hasMoreTokens()) {
			String tok = st.nextToken().trim();

			tok = tok.replace("P-", "P");

			if (tok.equals("(") || tok.equals(")")) line += tok;
			else line += tok + " ";
		}
		line = line.trim();
		return line;
	}

	public static String reverse(String str) {
		if (str.contains("*PLUS*/S")) str = str.replaceAll("*PLUS*/S", "\\+/S");
		if (str.contains("*SLASH*/S")) str = str.replaceAll("*SLASH*/S", "//S");
		if (str.contains("*LRB*/S")) str = str.replaceAll("*LRB*/S", "\\(/S");
		if (str.contains("*RRB*/S")) str = str.replaceAll("*RRB*/S", "\\)/S");
		return str; 

	}

	public static  String cleaning(String str) {
		if (str.contains("\\+/S")) str = str.replaceAll("\\+/S", "*PLUS*/S");
		if (str.contains("//S")) str = str.replaceAll("//S", "*SLASH*/S");
		if (str.contains("(/S")) str = str.replaceAll("\\(/S", "*LRB*/S");
		if (str.contains(")/S")) str = str.replaceAll("\\)/S", "*RRB*/S");
		if (str.contains(" ")) {
			String line = new String();
			String delim = "() ";
			StringTokenizer st = new StringTokenizer(str, delim, true);
			while (st.hasMoreTokens()) {
				String tok = st.nextToken().trim();
				if (tok.equals("(") || tok.equals(")")) line += tok;
				else if (tok.equals(" ")) line += tok;
				else {
					tok = sjtreeReplace(tok);
					line += tok + " ";
				}
			}
			line = line.trim();
			return line;
		}
		return str; 
	}

	public static String sjtreeReplace(String str) {

		// replace all for special characters which are used in annotation (sjmorph and sjtree);

		//        System.out.println(str);

		// replace pronoun NP in the corpus into PRON because NP can be a noun phrase in sjtree;
		if (str.contains("/NP")) str = str.replaceAll("/NP", "/PRON");

		while(str.contains("\t") || str.contains("  ") || str.contains(" )")) {
			str = str.replaceAll("\t", " ");
			str = str.replaceAll("  ", " "); 
			str = str.replaceAll(" \\)", "\\)");
		}
		str = str.replaceAll("__SBJ", "-SBJ");
		str = str.replaceAll("_SBJ", "-SBJ");
		str = str.replaceAll("_JAT", "-AJT");
		str = str.replaceAll("_MOD", "-MOD");
		str = str.replaceAll("_MOC", "-MOD");
		str = str.replaceAll("_CMOD", "-MOD");
		str = str.replaceAll("_OBJ", "-OBJ");
		str = str.replaceAll("_BOJ", "-OBJ");
		str = str.replaceAll("_AJT", "-AJT");
		str = str.replaceAll("_ATJ", "-AJT");

		str = str.replaceAll("_CNJ", "-CNJ");
		str = str.replaceAll("_CMP", "-CMP");
		str = str.replaceAll("_PRN", "-PRN");
		str = str.replaceAll("_RPN", "-PRN");

		str = str.replaceAll("_CJN", "-CJN");
		str = str.replaceAll("_CNP", "-CNP");
		str = str.replaceAll("_VNP", "-VNP");

		str = str.replaceAll("_INT", "-INT");
		str = str.replaceAll("_SBU", "-SBJ");

		str = str.replaceAll("_IP" , "-IPP");
		str = str.replaceAll("_CP" , "-CPP");

		str = str.replaceAll("_M", "-MOD");
		str = str.replaceAll("_O", "-OBJ");
		str = str.replaceAll("_A", "-AJT");
		str = str.replaceAll("_P", "-PPP");
		str = str.replaceAll("_U1", "");
		str = str.replaceAll("_U2", "");

		str = str.replaceAll(";Q0", "");
		str = str.replaceAll(";Q1", "");
		str = str.replaceAll(";Q2", "");
		str = str.replaceAll(";Q3", "");
		str = str.replaceAll(";Q4", "");
		str = str.replaceAll(";Q5", "");
		str = str.replaceAll(";Q6", "");
		str = str.replaceAll(";Q7", "");
		str = str.replaceAll(";Q8", "");
		str = str.replaceAll(";Q9", "");

		str = str.replaceAll(";U0", "");
		str = str.replaceAll(";U1", "");
		str = str.replaceAll(";U2", "");
		str = str.replaceAll(";U3", "");
		str = str.replaceAll(";U4", "");
		str = str.replaceAll(";U5", "");
		str = str.replaceAll(";U6", "");
		str = str.replaceAll(";U7", "");
		str = str.replaceAll(";U8", "");
		str = str.replaceAll(";U9", "");

		str = str.replaceAll("S0", "S");
		str = str.replaceAll("S1", "S");
		str = str.replaceAll("S2", "S");
		str = str.replaceAll("S3", "S");
		str = str.replaceAll("S4", "S");
		str = str.replaceAll("S5", "S");
		str = str.replaceAll("S6", "S");
		str = str.replaceAll("S7", "S");
		str = str.replaceAll("S8", "S");
		str = str.replaceAll("S9", "S");

		str = str.replaceAll("VP0", "VP");
		str = str.replaceAll("VP1", "VP");
		str = str.replaceAll("VP2", "VP");
		str = str.replaceAll("VP3", "VP");
		str = str.replaceAll("VP4", "S");
		str = str.replaceAll("VP5", "S");
		str = str.replaceAll("VP6", "S");
		str = str.replaceAll("VP7", "S");
		str = str.replaceAll("VP8", "S");
		str = str.replaceAll("VP9", "S");


		str = str.replaceAll("VNP0", "VP");
		str = str.replaceAll("VNP1", "VP");
		str = str.replaceAll("VNP2", "VP");
		str = str.replaceAll("VNP3", "VP");
		str = str.replaceAll("VNP4", "S");
		str = str.replaceAll("VNP5", "S");
		str = str.replaceAll("VNP6", "S");
		str = str.replaceAll("VNP7", "S");
		str = str.replaceAll("VNP8", "S");
		str = str.replaceAll("VNP9", "S");


		str = str.replaceAll("NP0", "NP");
		str = str.replaceAll("NP1", "NP");
		str = str.replaceAll("NP2", "NP");

		str = str.replaceAll("LP", "L");
		str = str.replaceAll("LP-AJT", "L-AJT");
		str = str.replaceAll("LP-CMP", "L-CMP");
		str = str.replaceAll("LP-CNJ", "L-CNJ");
		str = str.replaceAll("LP-OBJ", "L-OBJ");
		str = str.replaceAll("LP-SBJ", "L-SBJ");




		//	str = str.replaceAll("\\+/SW", "*PLUS*/SW");
		//	str = str.replaceAll("\\//SP", "*SLASH*/SP");
		//	str = str.replaceAll("\\-/SS", "*MINUS*/SS");
		//	str = str.replaceAll("\\_/S", "*UNDERBAR*/S");
		//	str = str.replaceAll("\\_", "*UNDERBAR*");

		//  str = str.replaceAll("\\(/SS", "*LRB*/SS");
		//  str = str.replaceAll("\\)/SS", "*RRB*/SS");
		//	str = str.replaceAll("@", "*AT*");

		//	str = str.replaceAll("\"/SS", "*DQUOTE*/SS");
		//	str = str.replaceAll("'/SS", "*SQUOTE*/SS");
		//	str = str.replaceAll("\"", "*DQUOTE*");
		//	str = str.replaceAll("&", "*AMP*");
		//	str = str.replaceAll("</SS", "*LT*/SS");
		//	str = str.replaceAll(">/SS", "*GT*/SS");

		// TODO: should be added more in sjsem for vowels;
		if (str.contains("ᆫ")) str = str.replaceAll("ᆫ","ㄴ");
		if (str.contains("ᄆ")) str = str.replaceAll("ᄆ","ㅁ");
		if (str.contains("ᆯ")) str = str.replaceAll("ᆯ","ㄹ");
		if (str.contains("ᄇ")) str = str.replaceAll("ᄇ","ㅂ");


		return str;

	}
	public static void main (String[] args) throws IOException {

		String fileName = args[0];

		BufferedReader d
			= new BufferedReader(new InputStreamReader(new FileInputStream(new File (fileName) )));

		String str = new String();
		str = d.readLine();
		while (str != null) {
			//			str = str.trim();
			System.out.println(cleaning(str));

			str = d.readLine();
		}
		d.close(); //out.close();
	}
}
