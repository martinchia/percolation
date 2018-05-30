import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;


public class PercolationStats {
	private double [] attemps;
	private double m;
	private double dev;
	
    public PercolationStats(int n, int trials) { // perform trials independent experiments on an n-by-n grid
    	if(n<=0 || trials<=0) {
    		throw new IllegalArgumentException();
    	}
    	attemps = new double[trials];
    	for(int i = 0;i<trials;i++) {
    		Percolation perc = new Percolation(n);
    		int steps = 0;
    		while(!perc.percolates()) {
    			int row = StdRandom.uniform(1,n+1);
    			int col = StdRandom.uniform(1,n+1);
    			if(perc.isOpen(row, col)) {
    				continue;
    			}
    			perc.open(row, col);
    			steps++;
    		}
    		attemps[i] = (double)steps/(n*n);
    	}
    	this.m = StdStats.mean(attemps);
    	this.dev = StdStats.stddev(attemps);
    	
    }   
    public double mean() { // sample mean of percolation threshold 	
    	return m;
    }                         
    public double stddev() { // sample standard deviation of percolation threshold
    	return dev;
    }                       
    public double confidenceLo() {// low  endpoint of 95% confidence interval
    	return this.m-((1.96*this.dev)/Math.sqrt(attemps.length));
    }                 
    public double confidenceHi() {// high endpoint of 95% confidence interval
    	return this.m+((1.96*this.dev)/Math.sqrt(attemps.length)); 
    	
    }                 
//    public static void main(String[] args) { // test client (described below)
//    	PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
//    	//PercolationStats ps = new PercolationStats(200,100);
//    	StdOut.print("mean = "+ps.mean()+"\n");
//        StdOut.print("stddev = "+ps.stddev()+"\n");
//        StdOut.print("95% confidence interval = "+ps.confidenceLo()+", "+ps.confidenceHi());
//    }       
}
