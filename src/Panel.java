import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;


public class Panel extends JPanel{
	
	public void paint(Graphics g){
	 	Graphics2D g2 = (Graphics2D) g;
	 	g2.setColor(Color.white);
        g2.fillRect(0, 0, 700, 700);
        g2.setColor(Color.black);
        g2.drawLine(0, 700, 700, 700); // y
        g2.drawLine(0, 0, 0, 700); // x

        
        for(int i=0; i<Kclustering.data.length; i++)
        {
        	
        	if(Kclustering.data[i].cluster==0)
        		g2.setColor(Color.yellow);
        	if(Kclustering.data[i].cluster==1)
        		g2.setColor(Color.red);
        	if(Kclustering.data[i].cluster==2)
        		g2.setColor(Color.green);
        	Shape l = new Ellipse2D.Double(700 - (Kclustering.data[i].x)*200 - 10, 700 - (Kclustering.data[i].y)*200 - 10, 20, 20);
        	g2.draw(l);
        }
        g2.setColor(Color.black);
        Shape clus = new Ellipse2D.Double(700 - (Kclustering.S[0].x)*200 - 10, 700 - (Kclustering.S[0].y)*200 - 10, 20, 20);
        g2.draw(clus);
        Shape clus2 = new Ellipse2D.Double(700 - (Kclustering.S[1].x)*200 - 10, 700 - (Kclustering.S[1].y)*200 - 10, 20, 20);
        g2.draw(clus2);
        Shape clus3 = new Ellipse2D.Double(700 - (Kclustering.S[2].x)*200 - 10, 700 - (Kclustering.S[2].y)*200 - 10, 20, 20);
        g2.draw(clus3);
        
        
 }
}
