import java.util.*;
import java.io.*;

public class Decompress {
    static String[] dict=new String[5000];
    
    public static void main(String[] args) throws IOException {
        Scanner o = new Scanner(new File("/home/btech/cs1120237/Asgn4/dictionary.txt"));
	String dcn = "";
	while(o.hasNext()) { dcn=dcn+"\n"+o.nextLine(); }
        int i=0;
        int j=0;
        while(j!=-1 && j<dcn.length() && i<5000) { 
            j=dcn.indexOf("\n",j);
            if(i==0) {
                while(i<256) { dict[i]=(Character.toString((char)i)); i++;}
                j=dcn.indexOf("256"); i=256;
            }
            else {
                dict[i]=dcn.substring(j+1,dcn.indexOf("//--",j+1)); 
                i++; String jk=Integer.toString(i); j=dcn.indexOf(jk); } 
        } 
        Scanner lo = new Scanner(new File("/home/btech/cs1120237/Asgn4/compressed.txt"));
	String code = "";
	while(lo.hasNext()) { code=code+"\n"+lo.nextLine(); } 
        String str="";
        int n=0; code=code.substring(1);
        while(n<code.length() && code.indexOf("\n",n+1)!=-1) {
            n=code.indexOf("\n",n);
            Integer h=Integer.parseInt(code.substring(n+1,code.indexOf("\n",n+1)));
            n=code.indexOf("\n",n+1); str=str+dict[h];
        }
        FileWriter fstr = new FileWriter("/home/btech/cs1120237/Asgn4/decompressed.txt");
        BufferedWriter out = new BufferedWriter(fstr);
        out.write(str);
        out.close();
        System.out.println(str);
    }
}
