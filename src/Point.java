
public class Point {
	double x, y, cluster;
	
	public Point()
	{
		x=0;
		y=0;
		cluster=-1;
	}
	public Point (double x, double y)
	{
		this.x=x;
		this.y=y;
		cluster=-1;
	}
	public void addPoint(Point p2)
	{
		x+=p2.x;
		y+=p2.y;
	}
	public void divide(int num)
	{
		x = x/num;
		y = y/num;
	}
	public String toString()
	{
		return "x-coord: " + x + ", y-coord: "+ y;
	}
}
