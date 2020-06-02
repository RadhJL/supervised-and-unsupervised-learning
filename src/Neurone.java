import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Neurone {

	static class Training
	{
		Matrix m;
		int target;
		Training(Matrix m, int target)
		{
			this.m = m;
			this.target = target;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		int data_size = 100;
		/* Enregistrement des data dans des fichier pour les afficher avec GNUPLOT*/
		String firstclass="neurone_classe1.txt";
		String secondclass="neurone_classe2.txt";

		//Souvegarde des deux classes
		PrintWriter pw1 = new PrintWriter(firstclass);
		PrintWriter pw2 = new PrintWriter(secondclass);

		ArrayList<Point> training_points= Point.initialize(data_size);
		ArrayList<Training> training_data= new ArrayList<Training>();
		//stockage des données d'entrainement
		for(Point p:training_points) 
			if(  (p.x >=1 && p.x <= 2  && p.y >= -2 && p.y <= -1) ||  (p.x >=-2 && p.x <= -1  && p.y >= 1 && p.y <= 2) )
				training_data.add(new Training(new Matrix(2,1,new float[] {p.x,p.y}),1));
			else if(  (p.x >=1 && p.x <= 2  && p.y >= 1 && p.y <= 2) ||  (p.x >=-2 && p.x <= -1  && p.y >= -2 && p.y <= -1) )
				training_data.add(new Training(new Matrix(2,1,new float[] {p.x,p.y}),0));
		
		
		float learn_rate = 0.1f;
		
		//initialiser les weights et les biais aléatoirement entre -1 et 1
		Matrix input_weight = new Matrix(2,2);
		Matrix hidden_weight = new Matrix(1,2);
		input_weight.randomize();		
		hidden_weight.randomize();
		
		Matrix input_biais = new Matrix(2,1);
		Matrix hidden_biais = new Matrix(1,1);
		input_biais.randomize();
		hidden_biais.randomize();
		
		
		System.out.println("Input weight avant l'entrainement ");
		System.out.println(input_weight);
		System.out.println("Hidden weight avant l'entrainement");
		System.out.println(hidden_weight);
		
		System.out.println("Entrainement en cours ...\n");
		int essai = 10;
		while(essai-->0){ 
			for(Training data:training_data)
			{
				//propagation
				Matrix input_value = data.m; 
				
				Matrix  hidden_value = Matrix.mult(input_weight,input_value); 
						hidden_value = Matrix.add(input_biais,hidden_value);
						hidden_value = Matrix.sigmoid(hidden_value);
						
				Matrix output_value = Matrix.mult(hidden_weight,hidden_value);
					output_value = Matrix.add(hidden_biais,output_value);
					output_value = Matrix.sigmoid(output_value);
				
				Matrix output_error = Matrix.subs(data.target , output_value);   
				//backpropagation
				Matrix gradient = Matrix.dsigmoid(output_value);
					gradient = Matrix.e_mult(gradient,output_error);
					gradient= Matrix.mult(gradient,learn_rate);
					
				hidden_weight = Matrix.add(hidden_weight , Matrix.mult( gradient , Matrix.transpose(hidden_value)) );
				hidden_biais = Matrix.add(hidden_biais,gradient);
					
				Matrix hidden_error = Matrix.mult(Matrix.transpose(hidden_weight), output_error) ;
				
				Matrix hidden_gradient = Matrix.dsigmoid(hidden_value);
					hidden_gradient = Matrix.e_mult(hidden_gradient,hidden_error);
					hidden_gradient= Matrix.mult(hidden_gradient,learn_rate);
					
				input_weight = Matrix.add(input_weight , Matrix.mult(hidden_gradient, Matrix.transpose(input_value) ) );
				input_biais = Matrix.add(input_biais,hidden_gradient);
				
			}
		}
		System.out.println("Input weight aprés l'entrainement");
		System.out.println(input_weight);
		System.out.println("Hidden weight aprés l'entrainement");
		System.out.println(hidden_weight);
		
		/*******************  Testing phase *******************/
		ArrayList<Point> test_data= Point.initialize(data_size);
		
		int missed = 0;
		for(Point data:test_data)
		{
			Matrix input_value =new Matrix(2,1, new float[] {data.x,data.y}); 
			
			Matrix  hidden_value = Matrix.mult(input_weight,input_value); 
					hidden_value = Matrix.add(input_biais,hidden_value);
					hidden_value = Matrix.sigmoid(hidden_value);
					
			Matrix output_value = Matrix.mult(hidden_weight,hidden_value);
				output_value = Matrix.add(hidden_biais,output_value);
				output_value = Matrix.sigmoid(output_value);
			//enregistrer la classification dans les fichiers		
			if(((data.x >=1 && data.x <= 2  && data.y >= -2 && data.y <= -1) ||  (data.x >=-2 && data.x <= -1  && data.y >= 1 && data.y <= 2)) && (output_value.mat[0][0] >= 0.5)){
					pw1.println(data.x+" "+data.y);
			}else if(  ((data.x >=1 && data.x <= 2  && data.y >= 1 && data.y <= 2) ||  (data.x >=-2 && data.x <= -1  && data.y >= -2 && data.y <= -1)) && (output_value.mat[0][0] < 0.5)){
					pw2.println(data.x+" "+data.y);
			}
			else {
				missed ++ ;
				if(output_value.mat[0][0] >= 0.5)
					pw1.println(data.x+" "+data.y);
				else 
					pw2.println(data.x+" "+data.y);
			}
		}
		//calcul d'erreur
		System.out.println("Nombre de points mal classes = "+ missed+" sur "+data_size * 4+" points");
		System.out.println("taux d'erreur = "+missed/(float) test_data.size()+"\n");
		
		pw1.close();
		pw2.close(); 
		System.out.println("Copier coller pour afficher le resultat avec GNUPLOT");
		System.out.println("plot \"neurone_classe1.txt\"  using  1:2 lt 1 lc 5,  \"neurone_classe2.txt\" using 1:2 lt 1 lc 3");


				
	}
}
