import java.io.*;
import java.util.*;
import java.text.*;
import java.io.File;
import javax.swing.*;
class Dictionary
{
	File folder;
	File[] listOfFiles;
    	LinkedHashSet<String> hs=new LinkedHashSet<String>();
	int res[][],size;
	String ans="";
	Dictionary(File folder)
	{
		this.folder=folder;	
		File[] listOfFiles1=folder.listFiles();
		listOfFiles=listOfFiles1;
		int res1[][]=new int[listOfFiles.length][1000000];
		res=res1;
	}
	String openmake()
	{
		for (int i = 0; i < listOfFiles.length; i++)
 		{
      			if (listOfFiles[i].isFile())
			{
				String fileName =listOfFiles[i].getName() ;
				String fN = listOfFiles[i].getAbsolutePath();

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
								res[i][k]=res[i][k]+1;
							}
							else if(temp.equals("the") || temp.equals("a") || temp.equals("is") || temp.equals("there") || temp.equals("was") || temp.equals("will") || temp.equals("I") || temp.equals("an") || temp.equals("am") || temp.equals("are") || temp.equals("this") || temp.equals("that") ){}
							else if(hs.contains(temp)==false)
							{
         							res[i][hs.size()]=1;
								hs.add(temp);
							}
     						}
					}   
					ans=ans+"\n"+fileName+ "read complete";

			            	// Always close files.
        		       		bufferedReader.close();         
        			}
 	       			catch(FileNotFoundException ex)
	       			{
               				ans=ans+"\nUnable to open file '" + fileName + "'";                
  	       			}
               			catch(IOException ex)
 				{
        				ans=ans+"\nError reading file '"+ fileName + "'";                  
        			}
			}
  			if (listOfFiles[i].isDirectory())
			{
				File folder1 = new File(listOfFiles[i].getAbsolutePath());
    				File[] listOfFiles1 = folder1.listFiles();	
	   			for (int j = 0; j < listOfFiles1.length; j++)
				{
      					if (listOfFiles1[j].isFile())
					{
						String fileName1 =listOfFiles1[j].getName() ;
						String fN1 = listOfFiles1[j].getAbsolutePath();
        					String line1 = null;
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
										res[i][k]=res[i][k]+1;
									}
									else if(temp.equals("the") || temp.equals("a") || temp.equals("is") || temp.equals("there") || temp.equals("was") || temp.equals("will") || temp.equals("I") || temp.equals("an") || temp.equals("am") || temp.equals("are") || temp.equals("this") || temp.equals("that") ){}
									else if(hs.contains(temp)==false)
									{
         									res[i][hs.size()]=1;
										hs.add(temp);
									}
     								}
							}
							bufferedReader1.close(); 
							ans=ans+"\n"+fileName1;
		            			}        
        					catch(FileNotFoundException ex)
						{
            						ans=ans+"\nUnable to open file '" + fileName1 + "'";                
        					}
		        			catch(IOException ex)
						{
		        				ans=ans+"\nError reading file '" + fileName1 + "'";                 
        					}
					}
				}
				ans=ans+"\n---------------------------------------------------------------------------------------------------------";
				ans=ans+"\n"+folder1.getName()+" read complete";
				ans=ans+"\n---------------------------------------------------------------------------------------------------------";
			}
		}
		size=hs.size();
		return ans;
	}
}
class Idf extends Dictionary
{
	double idf[];
	Idf(File folder)
	{
		super(folder);
	}
	void calculateidf()
	{
		double idf1[]=new double[super.size];
		idf=idf1;
		for(int i=0;i<hs.size();i++)
		{
			for(int j=0;j<listOfFiles.length;j++)
			{
				idf[i]=idf[i]+(double)res[j][i];
			}
		}
		for(int i=0;i<hs.size();i++)
		{
			idf[i]=(Math.log((double)(listOfFiles.length/idf[i])))/(Math.log(2));
		}	
	}	
}
class Tfidf extends Idf
{
	double tfidf[][];
	Tfidf(File folder)
	{
		super(folder);
	}
	void calculatetfidf()
	{
		double tfidf1[][]=new double[listOfFiles.length][hs.size()];
		tfidf=tfidf1;
		for(int i=0;i<listOfFiles.length;i++)
		{
			for(int j=0;j<hs.size();j++)
			{
				tfidf[i][j]=res[i][j]*idf[j];
			}
		}	
	}
}
class D extends Tfidf
{
	double d[];
	D(File folder)
	{
		super(folder);
	}
	void calculated()
	{
		double d1[]=new double[listOfFiles.length];
		d=d1;
		for(int i=0;i<listOfFiles.length;i++)
		{
			for(int j=0;j<hs.size();j++)
			{
				d[i]=d[i]+tfidf[i][j]*tfidf[i][j];	
			}
			d[i]=Math.sqrt(d[i]);
		}
	}
}
class NewFile extends D
{
	String fN1;
	String line1=null;
	String ans="";
	double q[];
	NewFile(String fN1,File folder)
	{
		super(folder);
		this.fN1=fN1;
	}
	String openq()
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
            		ans=ans+"\nUnable to open file '" + fN1 + "'";                
        	}
		catch(IOException ex)
		{
		       ans=ans+"\nError reading file '" + fN1 + "'";                
        	}		
		return ans;
	}
}
class Q extends NewFile
{
	double max=0;
	Q(String fN1,File folder)
	{
		super(fN1,folder);
	}
	void calculateq()
	{
		for(int i=0;i<hs.size();i++)
		{
			if(max<q[i])
			{
				max=q[i];
			}
		}
		for(int j=0;j<hs.size();j++)
		{
			q[j]=(q[j]/max)*idf[j];
		}	
	}	
}
class Query extends Q
{
	double query=0;
	Query(String fN1,File folder)
	{
		super(fN1,folder);
	}
	void calculatequery()
	{
		for(int i=0;i<hs.size();i++)
		{
			query=query+q[i]*q[i];
		}
		query=Math.sqrt(query);	
	}
}
class CosineSim extends Query
{
	double cosSim[];
	String ans="";
	CosineSim(String fN1,File folder)
	{
		super(fN1,folder);
	}
	String calculatecossim()
	{
		double cosSim1[]=new double[listOfFiles.length];
		cosSim=cosSim1;
		for(int i=0;i<listOfFiles.length;i++)
		{
			for(int k=0;k<hs.size();k++)
			{
				cosSim[i]=cosSim[i]+tfidf[i][k]*q[k];
			}
			cosSim[i]=cosSim[i]/(d[i]*query);
		}
		System.out.print("\n");
		DecimalFormat decFor = new DecimalFormat("0.00");
		max=0;
		int ind=0;
		ans=ans+"\n---------------------------------------------------------------------------------------------------------";
		ans=ans+"\nRelation Factor:";
		ans=ans+"\n---------------------------------------------------------------------------------------------------------\n";
		for(int i=0;i<listOfFiles.length;i++)
		{
			ans=ans+listOfFiles[i].getName()+"->"+decFor.format(cosSim[i])+" ";
			ans=ans+"\n";
			if(max<cosSim[i])
			{
				max=cosSim[i];
				ind=i;
			}
		}
		ans=ans+"\n---------------------------------------------------------------------------------------------------------";
		ans=ans+"\nDOCUMENT RELATED TO FOLDER:"+listOfFiles[ind].getName();
		ans=ans+"\n---------------------------------------------------------------------------------------------------------";
		return ans;
	}	
}
public class Fileclassification1
{ 
	public static void main(String[] args)
	{
		File folder = new File("G:\\HS\\challenging ass\\20news-18828");
		String fN1 = "G:\\HS\\challenging ass\\20news-18828\\alt.atheism\\49960.txt";
		CosineSim cob=new CosineSim(fN1,folder);
	String ans=cob.openmake();
	cob.calculateidf();
	cob.calculatetfidf();
	cob.calculated();
	String ans1=cob.openq();
	cob.calculateq();
	cob.calculatequery();
	String ans2=cob.calculatecossim();
	System.out.println(ans2);
	}
}