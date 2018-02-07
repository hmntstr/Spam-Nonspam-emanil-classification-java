import java.io.*;
import java.util.*;
import java.text.*;
class SpamRead
{
	File folderspam;
	File[] listOfFilesspam;
	LinkedHashSet<String> hs=new LinkedHashSet<String>();
	int resspam[][];
	double spam;
	SpamRead(File folderspam)
	{
		this.folderspam=folderspam;
		listOfFilesspam = folderspam.listFiles();
		int resspam1[][]=new int[listOfFilesspam.length][100000]; 	
		resspam=resspam1;
		spam=listOfFilesspam.length;
	}
	void readSpam()
	{
		System.out.println("SPAM READ BEGINS:");
    		for (int i = 0; i < listOfFilesspam.length; i++)
 		{
				String fileName =listOfFilesspam[i].getName() ;
				String fN = listOfFilesspam[i].getAbsolutePath();

	        		// This will reference one line at a time
        			String line = null;
	
 		      	 	try
				{
        	    			//  FileReader reads text files in the default encoding.
            				FileReader fileReader = 
                			new FileReader(fN);
	             			// Always wrap FileReader in BufferedReader.
         	        		BufferedReader bufferedReader = 
                       			new BufferedReader(fileReader);
	
        	  	        	while((line = bufferedReader.readLine()) != null)
					{
                				StringTokenizer st = new StringTokenizer(line);
    						while (st.hasMoreTokens())
						{
							String temp=st.nextToken();
							if(hs.contains(temp)==true)
							{
								Iterator it=hs.iterator();
								int k=0;
								while(it.hasNext())
								{
									if(it.next().equals(temp))
									{
										break;
									}
									k++;
								}
								resspam[i][k]=resspam[i][k]+1;
							}
							else if(temp.equals("the") || temp.equals("a") || temp.equals("is") || temp.equals("there") || temp.equals("was") ||
							temp.equals("will") || temp.equals("I") || temp.equals("an") || temp.equals("am") || temp.equals("are") || temp.equals("this") ||
							temp.equals("that")||(temp.startsWith("<")&&temp.endsWith(">"))||temp.endsWith(".")||
							temp.endsWith(":")||temp.endsWith("...")||temp.endsWith(",")||temp.endsWith("--")){}
							else if(hs.contains(temp)==false)
							{
         							resspam[i][hs.size()]=1;
								hs.add(temp);
							}
     						}
					}   
					System.out.println(fileName);

			            	// Always close files.
        		       		bufferedReader.close();         
        			}
 	       			catch(FileNotFoundException ex)
	       			{
               				System.out.println("Unable to open file '" + fileName + "'");                
  	       			}
               			catch(IOException ex)
 				{
        				System.out.println("Error reading file '"+ fileName + "'");                  
        			}
		}		
		System.out.println("---------------------------------------------------------------------------------------------------------");
		System.out.println(folderspam.getName()+" read complete");
		System.out.println("---------------------------------------------------------------------------------------------------------");	
	}
}
class NonSpamRead extends SpamRead
{
	File foldernonspam;
	File[] listOfFilesnonspam; 
	int resnonspam[][];
	double nonspam;
	double total;
	double probs;
	double probn;
	NonSpamRead(File foldernonspam,File folderspam)
	{
		super(folderspam);
		this.foldernonspam=foldernonspam;
		listOfFilesnonspam = foldernonspam.listFiles();	
		int resnonspam1[][]=new int[listOfFilesnonspam.length][100000]; 	
		resnonspam=resnonspam1;
		nonspam=listOfFilesnonspam.length;
	}
	void readNonSpam()
	{
		total=spam+nonspam;
		probs=(double)spam/total;
		probn=(double)nonspam/total;
		System.out.println("NONSPAM READ BEGINS:");
    		for (int i = 0; i < listOfFilesnonspam.length; i++)
 		{
				String fileName =listOfFilesnonspam[i].getName() ;
				String fN = listOfFilesnonspam[i].getAbsolutePath();

	        		// This will reference one line at a time
        			String line = null;
	
 		      	 	try
				{
        	    			//  FileReader reads text files in the default encoding.
            				FileReader fileReader = 
                			new FileReader(fN);
	             			// Always wrap FileReader in BufferedReader.
         	        		BufferedReader bufferedReader = 
                       			new BufferedReader(fileReader);
	
        	  	        	while((line = bufferedReader.readLine()) != null)
					{
                				StringTokenizer st = new StringTokenizer(line);
    						while (st.hasMoreTokens())
						{
							String temp=st.nextToken();
							if(hs.contains(temp)==true)
							{
								Iterator it=hs.iterator();
								int k=0;
								while(it.hasNext())
								{
									if(it.next().equals(temp))
									{
										break;
									}
									k++;
								}
								resnonspam[i][k]=resnonspam[i][k]+1;
							}
							else if(temp.equals("the") || temp.equals("a") || temp.equals("is") || temp.equals("there") || temp.equals("was") || temp.equals("will") || temp.equals("I") || temp.equals("an") || temp.equals("am") || temp.equals("are") || temp.equals("this") || temp.equals("that") ){}
							else if(hs.contains(temp)==false)
							{
         							resnonspam[i][hs.size()]=1;
								hs.add(temp);
							}
     						}
					}   
					System.out.println(fileName);

			            	// Always close files.
        		       		bufferedReader.close();         
        			}
 	       			catch(FileNotFoundException ex)
	       			{
               				System.out.println("Unable to open file '" + fileName + "'");                
  	       			}
               			catch(IOException ex)
 				{
        				System.out.println("Error reading file '"+ fileName + "'");                  
        			}
		}		
		System.out.println("---------------------------------------------------------------------------------------------------------");
		System.out.println(foldernonspam.getName()+" read complete\n"+hs.size());
		System.out.println("---------------------------------------------------------------------------------------------------------");	
	}
}
class Idf extends NonSpamRead
{
	double idfspam[];
	double idfnonspam[];
	double idfs[];
	double idfn[];
	Idf(File foldernonspam,File folderspam)
	{
		super(foldernonspam,folderspam);
	}
	void calculateIdf()
	{
		double idfspam1[]=new double[hs.size()];
		idfspam=idfspam1;
		double idfnonspam1[]=new double[hs.size()];
		idfnonspam=idfnonspam1;
		double idfs1[]=new double[hs.size()];
		idfs=idfs1;
		double idfn1[]=new double[hs.size()];
		idfn=idfn1;
		for(int i=0;i<hs.size();i++)
		{
			for(int j=0;j<listOfFilesspam.length;j++)
			{
				idfspam[i]=idfspam[i]+(double)resspam[j][i];
			}
		}
		for(int i=0;i<hs.size();i++)
		{
			for(int j=0;j<listOfFilesnonspam.length;j++)
			{
				idfnonspam[i]=idfnonspam[i]+(double)resnonspam[j][i];
			}
		} 
		for(int i=0;i<hs.size();i++)
		{
			idfs[i]=(double)idfspam[i]/spam;
			idfn[i]=(double)idfnonspam[i]/nonspam;
		}
	}
}
class TestMailRead extends Idf
{
	String fN1;
	String line1 = null;
	double q[];
	TestMailRead(File foldernonspam,File folderspam,String fN1)
	{
		super(foldernonspam,folderspam);
		this.fN1=fN1;
	}
	void readTestMail()
	{
		double q1[]=new double[hs.size()];
		q=q1;
		try
		{
	        	FileReader fileReader1 = new FileReader(fN1);
        	    	BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
	            	while((line1 = bufferedReader1.readLine()) != null)
			{
        	        	StringTokenizer st = new StringTokenizer(line1);
	    			while (st.hasMoreTokens())
				{
					String temp=st.nextToken();
					if(hs.contains(temp)==true)
					{
						Iterator it=hs.iterator();
						int k=0;
						while(it.hasNext())
						{
							if(it.next().equals(temp))
							{
								break;
							}
							k++;
						}
						q[k]=q[k]+1;
					}
				}
			}
		}
		catch(FileNotFoundException ex)
		{
            		System.out.println("Unable to open file '" + fN1 + "'");                
        	}
		catch(IOException ex)
		{
		        System.out.println("Error reading file '" + fN1 + "'");                
        	}
	}
}
class Probability extends TestMailRead
{
	double ps=1;
	double pn=1;
	double px;
	double py;
	String result="";
	Probability(File foldernonspam,File folderspam,String fN1)
	{
		super(foldernonspam,folderspam,fN1);
	}
	void calculateProbability()
	{
		for(int i=0;i<hs.size();i++)
		{
			if(q[i]==0)
			{
				if(idfs[i]!=1)
				ps=ps*(1-idfs[i]);		
			}
			else
			{
				if(idfs[i]!=0)
				ps=ps*idfs[i];
			}
		}
		for(int i=0;i<hs.size();i++)
		{
			if(q[i]==0)
			{
				if(idfn[i]!=1)
				pn=pn*(1-idfn[i]);		
			}
			else
			{
				if(idfn[i]!=0)
				pn=pn*idfn[i];
			}
		}
		px=(ps*probs)/(ps*probs+probn*pn);
		py=(pn*probn)/(pn*probn+probs*ps);
	}
	void displayResult()
	{
		if(px>py)
		{
			result="spam";
		}
		else
		{
			result="nonspam";
		}
		System.out.println("---------------------------------------------------------------------------------------------------------");
		System.out.println(px+" "+py);
		System.out.println("\nEmail is:"+result);
		System.out.println("---------------------------------------------------------------------------------------------------------");
	}
}
public class Emailclassification
{ 
	public static void main(String[] args)
	{
		File folderspam = new File("H://challenging assignment//traning//spam");
		File foldernonspam = new File("H://challenging assignment//traning//nonspam");
		String fN1 = "H:/VIRALI/challenging assignment/traning/spmsgc141.txt";
		Probability pob=new Probability(foldernonspam,folderspam,fN1);
		pob.readSpam();
		pob.readNonSpam();
		pob.calculateIdf();
		pob.readTestMail();
		pob.calculateProbability();
		pob.displayResult();
	}
}