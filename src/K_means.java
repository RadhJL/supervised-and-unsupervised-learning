import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class K_means {
	public static void main(String[] args) throws FileNotFoundException {
		
		int data_size = 50; // taille de chaque groupe
		int k = 1; // initialisation de k
		
		while(k++<4) {
			// initialisation du nombre d'essais, pour avoir une meilleure inertie inter-calsse
			int essai = 5;

			ArrayList<Cluster> clusters = null;
			//Initialisation d'une liste qui permet de stocker la meilleure classification, qui a une inertie inter-classe la plus grande
			ArrayList<Cluster> best_clusters = null;
			//initialisation de la meilleure intertie inter classe
			float best_ib = -1;

			while (essai-->0){
					// retourne  data_size * 4   points pour chaque groupes en les mélangeant
					ArrayList<Point> init= Point.initialize(data_size);
			
					//Créer une  liste de cluster pour k classes
					clusters= new ArrayList<Cluster>();
					//intialiser les k clusters avec des points aléatoires de la base initiale
					for(int i=0;i<k;i++)
						clusters.add(new Cluster(init.get((int) (Math.random()*((init.size()))))));	
					// stocker chaque points de la base au cluster le plus proche
					for(int i=0;i<init.size();i++) 
						clusters.get(Cluster.distance(init.get(i),clusters)).points.add(init.get(i));

					boolean changement=true; // variable pour arreter l'algorithme si y'a plus de changement
					while(changement) {
						changement=false;

						//Mise à jour des centres des clusters 
						for(int i=0;i<clusters.size();i++) {
							float sumx = 0,sumy=0;
							//calculer la somme des points du cluster i
							for(int j=0;j<clusters.get(i).points.size();j++) {
								sumx+= clusters.get(i).points.get(j).x;
								sumy+= clusters.get(i).points.get(j).y;
							}
							//diviser la somme par la taille du cluster pour avoir la moyenne
							sumx= (float) sumx/clusters.get(i).points.size();
							sumy= (float) sumy/clusters.get(i).points.size();
							clusters.get(i).center.x=sumx;
							clusters.get(i).center.y=sumy;
						}

						//Mise a jour d'appartenance des points aux clusters
						for(int i=0;i<clusters.size();i++) {
							for(int j=0;j<clusters.get(i).points.size();j++) {
								//calculer la distance du point a un cluster
								//fonction retourn l'indice du plus proche cluster a ce point
								int ind=Cluster.distance(clusters.get(i).points.get(j),clusters);
								//si l'indice du cluster le plus proche  est different de l'indice de son ancien cluster alors changer de cluster
								if(ind!=i) {
									clusters.get(ind).points.add(clusters.get(i).points.get(j));
									clusters.get(i).points.remove(j);
									//il y a changement d'appartenance donc on refaire la boucle while
									changement=true;
								}
							}
						}

					}
					//calcul du centre de gravité
					Point g = new Point(0,0);
					for(int i = 0 ; i < clusters.size() ; ++i){
						g.x += clusters.get(i).center.x;
						g.y += clusters.get(i).center.y;
					}
					g.x /= (float)clusters.size();
					g.y /= (float)clusters.size();
					//calcul de l'intertie inter-calsse
					float ib = 0;
					for(int i = 0; i < clusters.size(); ++i)
						ib += clusters.get(i).points.size() * Point.distance(g, clusters.get(i).center);
					if ( ib > best_ib){
						best_clusters = (ArrayList<Cluster>) clusters.clone();						
						best_ib = ib;
					}
					
				}
			//regrouper les classes trouvé par un k donné dans un fichier pour pouvoir l'afficher avec gnuplot
			String filename="K_Means_"+Integer.toString(k)+".txt";
			PrintWriter pw = new PrintWriter(filename);
			for(int i=0;i<best_clusters.size();i++) {
				for(int j=0;j<best_clusters.get(i).points.size();j++) 
					pw.println(best_clusters.get(i).points.get(j).x+" "+best_clusters.get(i).points.get(j).y);
				pw.println();
			}

			pw.close();
			System.out.println("Fichier du classification k = " + k + " a été enregistré avec succés, copier coller la commande ci-dessous pour l'afficher sur GNUPLOT:");
			System.out.println("plot for [IDX=0:2] 'k_means_"+k+".txt' index IDX u 1:2 with lp lt IDX+1");
			System.out.println();

		}
		
	}
}
