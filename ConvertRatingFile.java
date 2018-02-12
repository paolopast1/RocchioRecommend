package indexing;
import java.io.*;


//Il main si occupa di convertire un file di testo contenente i rating degli utenti in una scala da 1 a 5
//in un file in cui i rating sono in formato binario (1 -> like, 0 -> dislike)
//args[0]-> File su cui scrivere i ratings modificati
//args[1]-> File da cui leggere i ratings
public class ConvertRatingFile {

	public static void main(String[] args) throws IOException
	{
		File f = new File(args[0]);
		FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        File r = new File(args[1]);
        FileReader fr = new FileReader(r);
        BufferedReader br = new BufferedReader(fr);
        String s;
        while((s = br.readLine()) != null)
        {
        	String[] ratings = s.split("\t");
        	if(Integer.parseInt(ratings[2]) > 3)
        	{
        		bw.write(ratings[0]+"\t"+ratings[1]+"\t"+"1\n");
        	}
        	if(Integer.parseInt(ratings[2]) < 3)
        	{
        		bw.write(ratings[0]+"\t"+ratings[1]+"\t"+"0\n");
        	}
        }
        
        bw.close();
        br.close();
        
	}
}
