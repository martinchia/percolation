import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	   private WeightedQuickUnionUF cell;
	   private int[] site;
	   private int size;
	   private int n;
	   private int numOpen = 0;
	   private WeightedQuickUnionUF backwash; 
	   
	   public Percolation(int n) {
		   if(n<=0) {
			   throw new IllegalArgumentException();
		   }
		   this.size = n*n+2;
		   this.n = n;
		   site = new int[size];
		   this.cell = new WeightedQuickUnionUF(size);
		   this.backwash = new WeightedQuickUnionUF(size-1);
		   for(int i = 0;i<size;i++) {
			   this.site[i] = 0;
		   }
	   }               // create n-by-n grid, with all sites blocked
	   public    void open(int row, int col) {
		   if(row<1 || col<1 || row>n || col >n) {
	    		throw new IllegalArgumentException();
	    	}
		   if(!isOpen(row,col)) {
			   this.numOpen++;
			   this.site[(row-1)*n+col] = 1;
			   int[] l;
			   if(row == 1 && col == 1) {
				   l = new int[2];
				   l[0] = 2;
				   l[1] = 1+n;
				   this.cell.union(1, 0);
				   this.backwash.union(1, 0);
				   if(n==1) {
					   l = new int[1];
					   l[0] = 1;
					   this.cell.union(1, 2);
				   }
				   
			   }
			   else if(row == 1 && col == n) {
				   l = new int[2];
				   l[0] = col-1;
				   l[1] = (row-1)*n+col+n;
				   this.cell.union(col, 0);
				   this.backwash.union(col, 0);
			   }
			   else if(row == 1) {
				   l = new int[3];
				   l[0] = col+1;
				   l[1] = col-1;
				   l[2] = col+n;
				   this.cell.union(col, 0);
				   this.backwash.union(col, 0);
			   }
			   else if(row == n && col == n) {
				   l = new int[2];
				   l[0] = n*n-1;
				   l[1] = n*n-n;
				   this.cell.union(n*n, this.size-1);
			   }
			   else if(row == n && col == 1) {
				   l = new int[2];
				   l[0] = (row-1)*n+col-n;
				   l[1] = (row-1)*n+col+1;
				   this.cell.union((row-1)*n+col, this.size-1);
			   }
			   else if(row == n) {
				   l = new int[3];
				   l[0] = (row-1)*n+col+1;
				   l[1] = (row-1)*n+col-1;
				   l[2] = (row-1)*n+col-n;
				   this.cell.union(this.size - 1, (row-1)*n+col);
				   
			   }
			   else if(col == 1) {
				   l = new int[3];
				   l[0] = (row-1)*n+col-n;
				   l[1] = (row-1)*n+col+n;
				   l[2] = (row-1)*n+col+1;
			   }
			   else if(col == n) {
				   l = new int[3];
				   l[0] = (row-1)*n+col-n;
				   l[1] = (row-1)*n+col+n;
				   l[2] = (row-1)*n+col-1;
			   }
			   else {
				   l = new int[4];
				   l[0] = (row-1)*n+col+1;
				   l[1] = (row-1)*n+col-1;
				   l[2] = (row-1)*n+col-n;
				   l[3] = (row-1)*n+col+n;
			   }
			   for(int i = 0;i<l.length;i++) {
				   int j = l[i];
				   int c = ((j%n == 0) ? n:j%n); 
				   int r = ((j%n == 0) ? (j/n):((j-j%n)/n+1));
				   if(isOpen(r,c)) {
					   if(!this.backwash.connected(j, (row-1)*n+col)) {
						   this.cell.union(j, (row-1)*n+col);
						   this.backwash.union(j, (row-1)*n+col);
//						   if(j == 313) {
//							   System.out.print("*************");
//							   System.out.println(this.backwash.find(313));
//						   }
					   }
				   }
			   }
//			   System.out.print(row);
//			   System.out.print(",");
//			   System.out.print(col);
//			   System.out.print("\n");
//			   System.out.println(this.backwash.find(313));
			   
		   }
		   
	   }   // open site (row, col) if it is not open already
	   
	   public boolean isOpen(int row, int col) {
		   int num = (row-1)*n+col;
		   if(row<1 || col<1 || row>n || col >n) {
	    		throw new IllegalArgumentException();
	    	}
		   if(this.site[num]==1) {
			   return true;
		   }
		   return false;
	   }// is site (row, col) open?
	   
	   public boolean isFull(int row, int col) {
		   if(row<1 || col<1 || row>n || col >n) {
	    		throw new IllegalArgumentException();
	    	}
		   if(isOpen(row,col)) {
			   return this.backwash.connected(0, (row-1)*n+col);
		   }
		   return false;
	   } // is site (row, col) full?
	   
	   public int numberOfOpenSites() {
		   return this.numOpen;
	   }    // number of open sites
	   
	   public boolean percolates() {
		   if(this.cell.connected(0, n*n+1)) {
			   return true;
		   }
		   return false;
	   }              // does the system percolate?
}
