import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Perceptron {

	static class D{
		// vecteur qui caractérise les points
		Vector v;
		//classe desiré du vecteur
		int target;

		D(Vector v, int target){
			this.v= v;
			this.target=target;
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {

		// taille des données d'apprentissage
		int data_size = 50;
		//Génerer les points aléatoires
		ArrayList<Point> initialData = Point.initialize(data_size);
		ArrayList<D> learn = new ArrayList<D>();
		
		//Repartition des points d'entrainement sur les differents classes 0 = classe 1 et  1 = classe 2
		for(Point p:initialData) 
			if(  p.x >=1 && p.x <= 2  &&  ( p.y >=1 && p.y <= 2 ||   p.y >=-2 && p.y <= -1)  )
				learn.add(new D(new Vector(1,p.x,p.y),1)); // ajouter 1 au vecteur du point pour la multiplicaiton avec le biais
			else
				learn.add(new D(new Vector(1,p.x,p.y),0)); // ajouter 1 au vecteur du point pour la multiplicaiton avec le biais
		
		//training rate
		float rate = 0.1f;

		//le vecteur weight (premier valeur = biais)
		Vector weight= new Vector(); 
			weight.randomize();
		
		System.out.println("Vecteur weight avant l'apprentissage");
		System.out.println(weight.toString());
		System.out.println();
		int h;
		for(D i:learn)
		{	
			// Choisissez entre ces deux fonction d'evaluation
			
			//Threshold function
			// h=Vector.threshold(weight, i.v);
			
			// Logistic regression function
			h=Vector.logistic(weight, i.v);
			
			// si le resultat désiré est differente du resultat de l'evaluation alors adapter le vecteur weight
			if(i.target != h) 
				weight=Vector.addition(weight,  Vector.multiplication(i.v, rate * (i.target - h) ));
		}
		
		//Affichage du vecteur weight aprés l'apprentissage 
		System.out.println("Vecteur weight aprés apprentissage");
		System.out.println(weight.toString());
		System.out.println();
				
				
		// Generation du base de test
		ArrayList<Point> new_data = Point.initialize(data_size);
		ArrayList<D> test_data = new ArrayList<D>();
		
		for(Point p:new_data)
		{	
			// Choisir entre Threshold function ou bien Logistic regression
			// test_data.add(new D(new Vector(1,p.x,p.y),Vector.threshold(weight, new Vector(1,p.x,p.y)))); 
			test_data.add(new D(new Vector(1,p.x,p.y),Vector.logistic(weight, new Vector(1,p.x,p.y)))); 
		}
		
		// calcul d'erreur
		int mal_classe=0;
		for(D d: test_data)
			if(  d.v.y >= 1 && d.v.y <= 2  &&  ( d.v.z >=1 && d.v.z <= 2 ||   d.v.z >=-2 && d.v.z <= -1) && d.target == 0 )
				mal_classe++;
			else if (d.v.y >= -2 && d.v.y <= -1  &&  ( d.v.z >=1 && d.v.z <= 2 ||   d.v.z >=-2 && d.v.z <= -1) && d.target == 1 )
				mal_classe++;
		
		
		System.out.println("Nombre de points mal classes = "+mal_classe+" sur "+data_size * 4+" points");
		System.out.println("Taux d'erreur = "+mal_classe/(float) test_data.size()+"\n");
		
		/* Enregistrement des data dans des fichier pour les afficher avec GNUPLOT*/
		String firstclass="perceptron_classe1.txt";
		String secondclass="perceptron_classe2.txt";
		String weightLine="perceptron_weight.txt";
		
		//Souvegarde du vecteur weight
		PrintWriter pw3 = new PrintWriter(weightLine);
		
		float x = -1.0f;
		float y = (weight.y*(x)+ weight.x)/(-weight.z); 
		Point p1 = new Point(x,y);
		x = 1.0f;
		y = (weight.y*(x)+ weight.x)/(-weight.z); 
		Point p2 = new Point(x,y);
		pw3.println(p1.x+" "+p1.y);
		pw3.println(p2.x+" "+p2.y);
		pw3.close();
		
		//Souvegarde des deux classes
		PrintWriter pw1 = new PrintWriter(firstclass);
		PrintWriter pw2 = new PrintWriter(secondclass);

		for(D d:test_data) 
			if(d.target == 0)
				pw1.println(d.v.y+" "+d.v.z);
			else if(d.target == 1)
				pw2.println(d.v.y+" "+d.v.z);
		
		pw1.close();
		pw2.close();
		System.out.println("Copier coller pour afficher les resultats avec GNUPLOT");
		System.out.println("plot \"perceptron_classe1.txt\"  using  1:2 lt 1 lc 5,  \"perceptron_classe2.txt\" using 1:2 lt 1 lc 3, \"perceptron_weight.txt\" u 1:2 w l lc 7");

	}

}
