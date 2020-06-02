class Matrix {
  int l,c;
  float[][] mat;
  
  Matrix(int l, int c, float[] array){
	 this.l = l;
	 this.c = c;
	 mat = new float[l][c];
	 int k =0;
	  for(int i = 0 ; i < l; i++) 
		  for(int j = 0 ; j < c; j++) 
			  mat[i][j] = array[k++];
  }
  
  Matrix(int l, int c)
  {
		 this.l = l;
		 this.c = c;
		 mat = new float[l][c];
  }
  
  Matrix(Matrix m)
  {
	 this.l = m.l;
	 this.c = m.c;
	 mat = m.mat;
  }
  
  
  
  static Matrix add(Matrix m1, Matrix m2 )
  {
	  if(m1.l != m2.l || m1.c != m2.c) 
		  throw new NullPointerException("Matrix add should have the same number of line/column");

	  Matrix result = new Matrix(m1.l,m1.c);
	  for(int i = 0; i < m1.l; i++)
		  for(int j = 0; j < m1.c; j++)
			  result.mat[i][j] = m1.mat[i][j] + m2.mat[i][j];
			  
	  return result;
  }
  
  static Matrix subs(Matrix m1, Matrix m2 )
  {
	  if(m1.l != m2.l || m1.c != m2.c) 
		  throw new NullPointerException("Matrix soustraction should have the same number of line/column");

	  Matrix result = new Matrix(m1.l,m1.c);
	  for(int i = 0; i < m1.l; i++)
		  for(int j = 0; j < m1.c; j++)
			  result.mat[i][j] = m1.mat[i][j] - m2.mat[i][j];
			  
	  return result;
  }
 
  static Matrix subs(float x, Matrix m )
  {

	  Matrix result = new Matrix(m.l,m.c);
	  for(int i = 0; i < m.l; i++)
		  for(int j = 0; j < m.c; j++)
			  result.mat[i][j] = x - m.mat[i][j]; 
			  
	  return result;
  }
  static Matrix div(Matrix m , float x )
  {

	  Matrix result = new Matrix(m.l,m.c);
	  for(int i = 0; i < m.l; i++)
		  for(int j = 0; j < m.c; j++)
			  result.mat[i][j] = m.mat[i][j] / (float) x; 
			  
	  return result;
  }
  
  static Matrix mult(Matrix m1, float x) 
  {
	  Matrix result = new Matrix(m1.l,m1.c);

	  for(int i = 0; i < m1.l; i++)
		  for(int j = 0; j < m1.c; j++)
			  result.mat[i][j] = m1.mat[i][j] * x;
			  
	  return result;
  }
  
  static Matrix mult(float x, Matrix m1) 
  {
	  Matrix result = new Matrix(m1.l,m1.c);

	  for(int i = 0; i < m1.l; i++)
		  for(int j = 0; j < m1.c; j++)
			  result.mat[i][j] = m1.mat[i][j] * x;
			  
	  return result;
  }
  
  static Matrix e_mult(Matrix m1, Matrix m2) {
	  
	  if(m1.l != m2.l || m1.c != m2.c) 
		  throw new NullPointerException("Matrix e_mult should have the same number of line/column");

	  Matrix result = new Matrix(m1.l,m1.c);
	  for(int i = 0; i < m1.l; i++)
		  for(int j = 0; j < m1.c; j++)
			  result.mat[i][j] = m1.mat[i][j] * m2.mat[i][j];
			  
	  return result;
  }
  static Matrix mult(Matrix m1, Matrix m2 )
  {
	  if(m1.c != m2.l) 
		  throw new NullPointerException("Matrix mult should have the same number of line/column");

	  Matrix result = new Matrix(m1.l,m2.c);
	  float sum = 0;
	  for (int c = 0; c < m1.l; c++) 
        for (int d = 0; d < m2.c; d++) {
          for (int k = 0; k < m2.l; k++)
            sum = sum + m1.mat[c][k] * m2.mat[k][d];
 
          result.mat[c][d] = sum;
          sum = 0;
        }
	  
	  return result;
  }
  
  static Matrix transpose(Matrix m) {
	  
	  Matrix result = new Matrix(m.c,m.l);
	 
	  for(int i = 0; i<m.c ; i++) 
		  for(int j = 0; j<m.l ; j++) 
			    result.mat[i][j] = m.mat[j][i]; 
		 
	  return result;
  }

  void randomize()
  {
	  
	  for(int i = 0 ; i < l; i++)
		  for(int j = 0 ; j < c; j++) 
			  mat[i][j] = (float) ( (Math.random() * ( (1 + 0) + 1)) - 1) ;
  }
  
  public String toString() {
	
	  String ch = "[";
	  System.out.print("");
	  for(int i = 0 ; i < l; i++) {
		  for(int j = 0 ; j < c; j++) 
			  ch += String.valueOf(mat[i][j]) + " " ;
		 if(i != l -1 ) 
		 	ch+="\n";
	  }
	 ch +="]\n"; 
	  return ch;
	
  }
  static public float sig(float x) {
	  return (float) (1/(1+Math.exp(-x))) ;
  }
  
  static public float dsig(float x) {
	  return (float) x * (1-x) ;
  }
 
  static public Matrix sigmoid(Matrix m) {
	  
	  for(int i = 0; i < m.l; i++) 
		  for(int j = 0; j < m.c; j++) 
		    m.mat[i][j] = sig(m.mat[i][j]);
	  
	  return new Matrix(m);
  
  }
  
   static public Matrix dsigmoid(Matrix m) {
	  
	  for(int i = 0; i < m.l; i++) 
		  for(int j = 0; j < m.c; j++) 
		    m.mat[i][j] = dsig(m.mat[i][j]);
	  
	  return new Matrix(m);
  
  }
  
  
  public int size() {
	  int sum = 0;
	  for(int i = 0; i < l; i++) 
		  for(int j = 0; j < c; j++) 
			    sum += mat[i][j];
	  return sum;
  }

  	
  
  
  
  
  
  
  
  
  
  
}
