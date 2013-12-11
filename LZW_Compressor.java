import java.util.*;
import java.io.*;

class dict              				// dictionary
{
    public int c;
    public String key;
    public dict()
    {
        this(0,null);
    }
    public dict(int e, String n)
    {
        c=e;
        key=n;
    }
}

public class Compressor {					// compressor class
    static int cd=0;
    static dict[] ar=new dict[5000];
    static String str="";
    static String dctnry="";
    public static String Code(String x,int m,int n) 				// dictionary codes
    {
        while(n<x.length()+1) 
        { 
            if (search(x.substring(m,n))!=-1) 
            {  
                if(n==x.length()) 
                { 
                	str=str+"\n"+Integer.toString(search(x.substring(m,n))); n++; 
                }else
                {
                	n++;
                } 
            }else
            { 
                if(n==x.length())
                {
                	hash(x.substring(m,n)); str=str+"\n"+Integer.toString(search(x.substring(m,n))); n++;
                }else
                {
                	str=str+"\n"+Integer.toString(search(x.substring(m,n-1))); hash(x.substring(m,n));  m=n-1; n++;
                }
            }
        }
        return str;
    }
    
    public static void hash(String k) { 						//  hash table
       int j=0; int h=1;
        for(int i=0;i<k.length();i++) {
            j=j+((int) k.charAt(i));
        } 
        if (ar[j]==null) { ar[j]=new dict(cd,k); cd=cd+1; }
        else {
            while(ar[(j+(h*h))%5000]!=null) {h++;}
            ar[(j+(h*h))%5000]=new dict(cd,k); cd=cd+1; 
        }
    }
    
    public static int search(String k) {
       int j=0; int h=1;
        for(int i=0;i<k.length();i++) {
            j=j+((int) k.charAt(i));
        }
        if(ar[j]==null) { return -1; }
        else if (ar[j].key.contentEquals(k)) { return ar[j].c;  }
        else { 
            while(h<80) {
                if (ar[(j+(h*h))%5000]==null) { return -1; }
                else if(ar[(j+(h*h))%5000].key.contentEquals(k)) { return ar[(j+(h*h))%5000].c;}
                else  { h++;} 
            }
            if(h<80) { return ar[(j+(h*h))%5000].c; }
            else { return -1;}
        }        
    }
            
            
    public static void main(String[] args) throws IOException {
        for(int i=0;i<256;i++) {
            hash(Character.toString((char)i));
        }
        Scanner o = new Scanner(new File("/home/btech/cs1120237/Asgn4/text.txt"));
	BufferedReader obj = new BufferedReader (new InputStreamReader(System.in));
	String file = "";
	while(o.hasNext()) { file=file+"\n"+o.nextLine(); }
        String xx=Code(file.substring(1),0,1);
        FileWriter fstr = new FileWriter("/home/btech/cs1120237/Asgn4/compressed.txt");
        BufferedWriter out = new BufferedWriter(fstr);
        out.write(xx+"\n"+"Code Ends");
        out.close();
        for(int i=0;i<5000;i++) { if(ar[i]!=null) { dctnry=dctnry+"\n"+ar[i].c+"\n"+ar[i].key+"//--"+"\n"; } }
        FileWriter fstream = new FileWriter("/home/btech/cs1120237/Asgn4/dictionary.txt");
        BufferedWriter oust = new BufferedWriter(fstream);
        oust.write(dctnry.substring(1)+"\n"+"Dictionary Ends");
        oust.close();
        System.out.println(xx);
    }
}
