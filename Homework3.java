import java.util.*;
import java.io.*;

public class Homework3 {

	public static String translateEmphasis(String text) {

		// System.out.println("in translateEmphasis");

		text = text.replaceAll("\\*(.*?)\\*", "<em>$1</em>");

		// System.out.println("after" + text);

		return text;
	}

	public static String translateStrongEmphasis(String text) {

		// System.out.println("in translateStrongEmphasis");

		text = text.replaceAll("\\*\\*(.*?)\\*\\*", "<strong>$1</strong>");

		// System.out.println(text);

		return text;
	}

	public static String translateHyperlink(String link, String url) {
		// return "<a href=\\" + "\"" +  URL +  "\\" + "\"" +  ">" + link + "</a>";

		// System.out.println("in translateHyperlink");

		// System.out.println("link:" + link + "url:" + url);

		// String value = " \"ROM\" ";
		String str = String.format("<a href=\"%s\">%s</a>", url, link); 

		// System.out.println("str:" + str);

		return str;
	}

	public static String translateImage(String text, String path, String title) {
		
		// System.out.println("in translateHyperlink");

		// System.out.println("text:" + text + "path:" + path + "title:" + title);

		String str = String.format("<img src=\"%s\" alt=\"%s\" title=\"%s\">", path, text, title);

		// System.out.println("in str:" + str);

		return str;
		// return "<img src=\\" + "\"" + path + "\\" + "\" " + "alt=\\" + "\"" + text + "\\" + "\" " + "title=\\" + "\"" + title + "\\" + "\"" + ">";
		
	}

	public static String translateCode(String code) {
		return "<code>" + code + "</code>";
	}

	public static String translateListItem(String input) {
		return "<li>" + input + "</li>";
	}
	
	public static void main(String [] args) {

		String out = "output.html";
		File convert = null;
		Scanner text = null;
		PrintWriter writer = null;
		boolean isFirst = true;
		int isPara = 0;
		boolean isInserted = false;

		try {
			convert = new File("index.txt");
			text = new Scanner(convert);
			writer = new PrintWriter(out);
		}
		catch (Exception e) {
			// do nothing so far
		}

		// format the html first
		writer.println("<!DOCTYPE html>");
		writer.println("<html>");
		writer.println("<head>");
		writer.println("<title>Results of Markdown Translation</title>");
		writer.println("</head>");
		writer.println("<body>");
		
		while (text.hasNextLine()) {

			String line = text.nextLine();
			String str; 
			// if(line.endsWith(".")) {
			// 	line = line + "</p>";
			// }

			// scan through the string

			// if (line.isEmpty()) {

			// 	if (isInserted) {
			// 		writer.println("</p>");
			// 		isInserted = false;
			// 		isPara = 0;
			// 	}

			// 	isPara++;

			// 	if (isPara >= 2) {
			// 		writer.println("<p>");
			// 		isInserted = true;
			// 	}

			// 	continue;
			// }

			if (line.isEmpty()) {
				if (isInserted) {
					writer.println("</p>");
					isInserted = false;
					isPara = 0;
					continue;
				}

				isPara++;
				continue;
			}
			else {
				if(isPara < 2) {
					isPara = 0;
				}

				if (isPara >= 2) {
					writer.println("<p>");
					isInserted = true;
				}
			}

			if (line.startsWith("+")) {

				if(isFirst)
					writer.println("<ul>");

				String s = line.substring(line.indexOf("+"));
				String tmp = line.substring(line.indexOf("+") + 1);

				// System.out.println("s: " + s);	

				str = translateListItem(tmp);
				line = line.replace(s, str);
				isFirst = false;
				writer.println(line);

				continue;
			}

			if(!isFirst) {
				writer.println("</ul>");
				isFirst = true;
			}

			// System.out.println(line);

			if (line.contains("**")) {
				line = translateStrongEmphasis(line);
			}
			else if (line.contains("*")) {
				line = translateEmphasis(line);
			}

			// // System.out.println(line);
			// // System.out.println("end with translateEmphasis");

			if (!line.contains("!") && line.contains("[") && line.contains("]") && line.contains("(") && line.contains(")") ) {
				
				String url = line.substring(line.lastIndexOf("]") + 1, line.lastIwndexOf(")"));
				String txt = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
				String s = line.substring(line.indexOf("["), line.lastIndexOf(")") + 1);

				// System.out.println("s: " + s);
				// System.out.println("url: " + url);
				// System.out.println("txt: " + txt);

				str = translateHyperlink(txt, url);

				// System.out.println("str: " + str);

			    line = line.replace(s, str);

				// System.out.println("line: " + line);

			}

			// // System.out.println(line);
			// // System.out.println("end with translateHyperlink");

			if (line.contains("![") && line.contains("]") && line.contains("(") && line.contains(")") ) {
				
				String path = line.substring(line.indexOf("(") + 1, line.indexOf("\"") - 1);
				String alt = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
				String title = line.substring(line.indexOf("\"") + 1, line.indexOf(")") - 1);
				String s = line.substring(line.indexOf("!"), line.lastIndexOf(")") + 1);

				str = translateImage(alt, path, title);

				line = line.replace(s, str);
			}

			// // System.out.println(line);
			// // System.out.println("end with translateImage");

			if (line.contains("`")) {

				String s = line.substring(line.indexOf("`"), line.lastIndexOf("`") + 1);
				String tmp = line.substring(line.indexOf("`") + 1, line.lastIndexOf("`"));

				str = translateCode(tmp);

				line = line.replace(s, str);
			}

			// // System.out.println(line);

			writer.println(line);

		}

		if(!isFirst) {
			writer.println("</ul>");
			isFirst = true;
		}

		if (isInserted) {
			writer.println("</p>");
			isInserted = false;
			isPara = 0;
		}

		writer.println("</body>");
		writer.println("</html>");

		writer.close();
	}


	
}
