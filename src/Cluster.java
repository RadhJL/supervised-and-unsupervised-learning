import java.util.ArrayList;

class Cluster{
	//centre du cluster
	Point center;
	//liste de points stocker dans le cluster
	ArrayList<Point> points=new ArrayList<Point>();
	
	Cluster(Point center) {
		this.center=new Point(center);
	}
	
	// retourne l'indice du cluster qui a une distance minimale avec un point  
	public static int distance(Point p1, ArrayList<Cluster> clusters) {
		float min=999999999.f;
		int index_min = 0;
		for(int i=0;i<clusters.size();i++){
			float test_val =Point.distance(clusters.get(i).center,p1);	
			if(test_val<min) {
				min=test_val;
				index_min=i;
			}
		}
		return index_min;
	}
}