import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Writer;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Kclustering{
	
	static Point [] data; // set of all data points
	static Point [] data_new; // set of all data points
	static Point [] S; // set of data points representing cluster centers
	static String data2;
	static Point temp;
	static int n;
	BufferedReader reader = null;
	LineNumberReader lnr;
	int k;
	
	// k is the number of clusters
	public Kclustering (String path, int number_of_clusters) throws IOException
	{
		n = 0;
		k = number_of_clusters;
		
		// count the number of data points
		lnr = new LineNumberReader(new FileReader(new File(path)));
		lnr.skip(Long.MAX_VALUE);
		//System.out.println(lnr.getLineNumber() + 1); //Add 1 because line index starts at 0
		n = lnr.getLineNumber() + 1;
		// Finally, the LineNumberReader object should be closed to prevent resource leak
		lnr.close();
		
		data_new = new Point [n];
		int i = 0;
		int count = 0;
		
		try {
		    File file = new File(path);
		    reader = new BufferedReader(new FileReader(file));

		    String line= reader.readLine();
		   
		    while (line != null) {
		    	//System.out.println(line);
		    	String[] parts = line.split("\\s+");
		    	//System.out.println(parts[0] + " " + parts[1]);
		    	double x_val = Double.parseDouble(parts[0]); // x
		    	//System.out.println(x_val);
		    	
		    	double y_val = Double.parseDouble(parts[1]); // y

		    	data_new[i] = new Point(x_val, y_val);
		    	//System.out.println("Loading point "+ data[i]);
		    	i++;
		    	line = reader.readLine();
		    }

		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		// initializing the S array
		S = new Point [k];
		randomCluster();
		
		System.out.println("First Cluster - "+ S[0]);
		System.out.println("Second Cluster - "+ S[1]);
		System.out.println("Third Cluster - "+ S[2]);
		
		Frame draw1 = new Frame("K Cluster Frame");
		do 
		{
			data = data_new;
			
			// initiating a new cluster
			
			//System.out.println(S[0] + "  " + S[1] + "  " + S[2]);
			
			cluster_assign();
			// updating the cluster
			for (int m = 0; m < k; m++)
			{
				centroid(m);
			}
			cluster_assign();
			count++;
			
		} while (count != 1000);
		//Frame draw2 = new Frame("New Frame");
		save("output.txt");
		System.out.println(count);
		
		System.out.println("First Cluster - "+ S[0]);
		System.out.println("Second Cluster - "+ S[1]);
		System.out.println("Third Cluster - "+ S[2]);
		
		distortionFunc ();
		
	}
	
	public void distortionFunc ()
	{
		double total = 0;
		
		for (int m = 0; m < k; m++)
		{
			for (int p = 0; p < n; p++)
			{
				//System.out.println(data[p].cluster + "  " + S[m].cluster);
				if (data[p].cluster == m)
				{
					//System.out.println("hello");
					total += (Math.pow(data[p].x-S[m].x, 2) + Math.pow(data[p].y-S[m].y, 2));
				}	
			}
			System.out.println("Total " + total);
			//total = 0;
		}
	}
	
	// This is specifically for 3 clusters - needs to be adjusted to work for any number of clusters
	public void randomCluster () 
	{
		Random rand = new Random();

		int  random = rand.nextInt(n); // the first random x1
		Point x1 = new Point ();
		x1 = data_new[random];
		
		
		// x2 is the furthest from x1 
		Point x2 = new Point ();
		x2 = x1;
		double dist,dist_temp;
		dist=distance(x2,x1);
		
		//System.out.println("FUCKING INSANEEEE: " + data[0] + "  " + data[1] + "  " + data[2]);
		for (int m = 0; m < n; m++)
		{
			//System.out.println("Current point - " + data[m]);
			dist_temp = distance(x1, data_new[m]);
			//System.out.println("Temp distance: "+ dist_temp);
			if (dist_temp > dist)
			{
				x2 = data_new[m];
				dist=dist_temp;
			}
		}
		
		// x3 is the furthest from both x1 and x2
		Point x3 = new Point ();
		x3 = x1;
		for (int m = 0; m < n; m++)
		{
			if ((distance(x1, data_new[m]) + distance(x2, data_new[m])) > (distance(x1, x3) + distance(x2, x3)))
			{
				x3 = data_new[m];	
			}
		}
			
		S[0] = x1;
		S[1] = x2;
		S[2] = x3;		
		
	}
	
	public double distance (Point p1, Point p2)
	{
		double distance_val = Math.sqrt(Math.pow(p2.x-p1.x, 2) + Math.pow(p2.y-p1.y, 2));
		//System.out.println("Point 1 - "+p1 + ", Point 2 - " + p2);
		//System.out.println(distance_val);
		return distance_val;
	}
	
	public void printList() 		// expected runtime: O(N)
	{
		System.out.print("List: ");
		
		for (int m = 0; m < (n); m++)
		{
			System.out.print(data[m].x + "     " + data[m].y + "\n");
		}
		
	}
	
	public void cluster_assign()
	{
		double distance=Double.MAX_VALUE, temp_distance=0;
		for(int i=0; i<data.length; i++)
		{
			for(int j=0; j<S.length; j++)
			{
				temp_distance = distance(data[i], S[j]);
				if(temp_distance < distance)
				{
					distance=temp_distance;
					data[i].cluster=j;
				}
			}
			distance=Double.MAX_VALUE;
		}
	}
	
	public void centroid(int cluster)
	{
		Point center = new Point();
		int count=0;
		for(int m=0; m<data.length; m++)
		{
			if(data[m].cluster==cluster){
				center.addPoint(data[m]);
				count++;
			}
		}
		center.divide(count);
		S[cluster] = center;
	}
	
	 public void save(String fileName) throws IOException {
		    BufferedWriter writer = null;
		    try {

		        writer = new BufferedWriter(new FileWriter(fileName));
		        for ( int m = 0; m < n; m++)
		        {      
		          writer.write(data[m].x + " " + data[m].y + " " + data[m].cluster);
		          writer.newLine();
		      writer.flush();
		        }

		    } catch(IOException ex) {
		        ex.printStackTrace();
		    } finally{
		        if(writer!=null){
		            writer.close();
		        }  
		    }
		}
}

	 


