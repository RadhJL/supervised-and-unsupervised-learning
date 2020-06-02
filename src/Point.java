import java.util.ArrayList;
import java.util.Collections;

public class Point {
	float x,y;
	
	Point (float x , float y){
		this.x=x;
		this.y=y;
	}
	
	Point (Point p){
		this.x=p.x;
		this.y=p.y;
	}
	
	public String toString(){
		return this.x+" "+this.y;
	}
	
	//distance euclidienne entre deux points
	public static  Float distance (Point p1, Point p2) {
		return (float) Math.sqrt( (p2.x-p1.x)*(p2.x-p1.x)+(p2.y-p1.y)*(p2.y-p1.y));
	};
	
	// retourn une liste de points aléatoire qui sont répartis sur les 4 groupes, size * 4
	public static ArrayList<Point> initialize(int size){
		ArrayList<Point> init = new ArrayList<Point>();
		
		for(int i=0;i<size;i++) {
			Point p1= new Point(	(float)((Math.random() * ((2 - 1) )) + 1)   , (float)((Math.random() * ((2 - 1) )) + 1)  );
			Point p2= new Point(	(float)((Math.random() * ((2 - 1) )) + 1)   , (float)((Math.random() * (( - 1 - 1) + 1)) - 1)  );
			Point p3= new Point(	(float)((Math.random() * (( - 1 - 1) + 1)) - 1)     , (float)((Math.random() * ((2 - 1))) + 1)  );
			Point p4= new Point(	(float)((Math.random() * (( - 1 - 1) + 1)) - 1)  , (float)((Math.random() * (( - 1 - 1) + 1)) - 1)  );
			init.add(p1);
			init.add(p2);
			init.add(p3);
			init.add(p4);
		}
		//melangez les points
		Collections.shuffle(init); 
		return init;
			 
	}
	
	
	
}

