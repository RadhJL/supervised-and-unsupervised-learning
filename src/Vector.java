
public class Vector {
	float x,y,z;
	
	Vector (float x, float y, float z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	Vector(Vector v){
		this.x=v.x;
		this.y=v.y;
		this.z=v.z;
	}
	Vector(){
		this.x=0;
		this.y=0;
		this.z=0;
	}
	
	public void randomize(){
			  this.x = (float) ( (Math.random() * ( (1 + 0) + 1)) - 1) ;
			  this.y = (float) ( (Math.random() * ( (1 + 0) + 1)) - 1) ;
			  this.z = (float) ( (Math.random() * ( (1 + 0) + 1)) - 1) ;
	}


	public String toString() {
		return  String.valueOf(x)+" "+String.valueOf(y)+" "+String.valueOf(z);
	}
	public static float dotProduct(Vector v1, Vector v2) {
		return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
	}
	
	public static int threshold(Vector v1, Vector v2) {
		return dotProduct(v1,v2) >= 0 ? 1 : 0;
	}
	
	public static int logistic(Vector v1, Vector v2) {
		return   1/(float)(1+ Math.exp(-dotProduct(v1,v2))) >= 0.5 ? 1 : 0;
	}
	
	
	public static Vector addition(Vector v1, Vector v2) {
		return new Vector(v1.x+v2.x,v1.y+v2.y,v1.z+v2.z);
	}
	
	public static Vector soustraction(Vector v1, Vector v2) {
		return new Vector(v1.x-v2.x,v1.y-v2.y,v1.z-v2.z);
	}
	
	public static Vector multiplication(Vector v1, float x) {
		return new Vector(v1.x * x ,v1.y * x,v1.z * x);
	}
	
	
	
}
