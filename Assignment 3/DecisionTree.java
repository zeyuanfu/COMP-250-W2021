import java.io.Serializable;
import java.util.ArrayList;
import java.text.*;
import java.lang.Math;

public class DecisionTree implements Serializable {

	DTNode rootDTNode;
	int minSizeDatalist; //minimum number of datapoints that should be present in the dataset so as to initiate a split
	
	// Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
	public static final long serialVersionUID = 343L;
	
	public DecisionTree(ArrayList<Datum> datalist , int min) {
		minSizeDatalist = min;
		rootDTNode = (new DTNode()).fillDTNode(datalist);
	}

	class DTNode implements Serializable{
		//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
		public static final long serialVersionUID = 438L;
		boolean leaf;
		int label = -1;      // only defined if node is a leaf
		int attribute; // only defined if node is not a leaf
		double threshold;  // only defined if node is not a leaf

		DTNode left, right; //the left and right child of a particular node. (null if leaf)

		DTNode() {
			leaf = true;
			threshold = Double.MAX_VALUE;
		}

		
		// this method takes in a datalist (ArrayList of type datum). It returns the calling DTNode object 
		// as the root of a decision tree trained using the datapoints present in the datalist variable and minSizeDatalist.
		// Also, KEEP IN MIND that the left and right child of the node correspond to "less than" and "greater than or equal to" threshold
		DTNode fillDTNode(ArrayList<Datum> datalist) {

			if(datalist.size() >= minSizeDatalist) {
				
				int label = datalist.get(0).y;
				boolean identical = true;
				
				for(Datum d: datalist) {
					if(d.y != label) {
						identical = false;
						break;
					}
				}
				
				if(identical) {
					this.leaf = true;
					this.label = label;
					return this;
				}
				
				else {
					
					double bestEntropy = 999;
					int bestAttribute = -1;
					double bestThreshold = -1;
					
					for(int i=0; i < datalist.get(0).x.length; i++) {
						
						for(Datum d1: datalist) {
						
							ArrayList<Datum> tempLeft = new ArrayList<Datum>();
							ArrayList<Datum> tempRight = new ArrayList<Datum>();
							
							for(Datum d2: datalist) {
								if(d2.x[i] < d1.x[i]) tempLeft.add(d2);
								else tempRight.add(d2);
							}
							
							double currentEntropy = (tempLeft.size()*calcEntropy(tempLeft) + tempRight.size()*calcEntropy(tempRight))/datalist.size();
								
							if(currentEntropy < bestEntropy) {
								bestEntropy = currentEntropy;
								bestAttribute = i;
								bestThreshold = d1.x[i];
									
							}														
						}
					}
					
					if(bestEntropy < calcEntropy(datalist)) {
					
						this.leaf = false;
						this.attribute = bestAttribute;
						this.threshold = bestThreshold;
						
						ArrayList<Datum> toLeftLeaf = new ArrayList<Datum>();
						ArrayList<Datum> toRightLeaf = new ArrayList<Datum>();
					
						for(Datum d3: datalist) {
							if(d3.x[bestAttribute] < bestThreshold) toLeftLeaf.add(d3);
							else toRightLeaf.add(d3);
						}
					
						this.left = (new DTNode()).fillDTNode(toLeftLeaf);
						this.right = (new DTNode()).fillDTNode(toRightLeaf);
					
						return this;
					}
					
					else {
						
						this.leaf = true;
						this.label = findMajority(datalist);
										
						return this;
					}
				}
			}
			
			else {
				
				this.leaf = true;
				this.label = findMajority(datalist);
								
				return this;
			}
		}



		// This is a helper method. Given a datalist, this method returns the label that has the most
		// occurrences. In case of a tie it returns the label with the smallest value (numerically) involved in the tie.
		int findMajority(ArrayList<Datum> datalist) {
			
			int [] votes = new int[2];

			//loop through the data and count the occurrences of datapoints of each label
			for (Datum data : datalist)
			{
				votes[data.y]+=1;
			}
			
			if (votes[0] >= votes[1])
				return 0;
			else
				return 1;
		}




		// This method takes in a datapoint (excluding the label) in the form of an array of type double (Datum.x) and
		// returns its corresponding label, as determined by the decision tree
		int classifyAtNode(double[] xQuery) {
		
			if(this.leaf == true) {
				return this.label;
			}
			
			else {
				
				DTNode child;
				
				if(xQuery[this.attribute] < this.threshold) {
					child = this.left;
				}
				
				else child = this.right;
				
				return child.classifyAtNode(xQuery);
			}
		}


		//given another DTNode object, this method checks if the tree rooted at the calling DTNode is equal to the tree rooted
		//at DTNode object passed as the parameter
		public boolean equals(Object dt2){

			if (dt2 == null) return false;
			
			if(dt2 instanceof DTNode) {
				
				DTNode temp = (DTNode) dt2;
				
				if((this.leaf && temp.leaf) && this.label == temp.label) return true;
				
				if((this.left == null && temp.left != null) || (this.left != null && temp.left == null) || (this.right == null && temp.right != null) || (this.right != null && temp.right == null)) return false;
				
				else if ((!this.leaf && !temp.leaf) && this.attribute == temp.attribute && this.threshold == temp.threshold) {
					
					if (this.left == null && temp.left == null) return this.right.equals(temp.right);
					
					else if (this.right == null && temp.right == null) return this.left.equals(temp.left);
					
					else return this.left.equals(temp.left) && this.right.equals(temp.right);
				}
			}

			return false;
		}
	}



	//Given a dataset, this returns the entropy of the dataset
	double calcEntropy(ArrayList<Datum> datalist) {
		double entropy = 0;
		double px = 0;
		float [] counter= new float[2];
		if (datalist.size()==0)
			return 0;
		double num0 = 0.00000001,num1 = 0.000000001;

		//calculates the number of points belonging to each of the labels
		for (Datum d : datalist)
		{
			counter[d.y]+=1;
		}
		//calculates the entropy using the formula specified in the document
		for (int i = 0 ; i< counter.length ; i++)
		{
			if (counter[i]>0)
			{
				px = counter[i]/datalist.size();
				entropy -= (px*Math.log(px)/Math.log(2));
			}
		}

		return entropy;
	}


	// given a datapoint (without the label) calls the DTNode.classifyAtNode() on the rootnode of the calling DecisionTree object
	int classify(double[] xQuery ) {
		return this.rootDTNode.classifyAtNode( xQuery );
	}

	// Checks the performance of a DecisionTree on a dataset
	// This method is provided in case you would like to compare your
	// results with the reference values provided in the PDF in the Data
	// section of the PDF
	String checkPerformance( ArrayList<Datum> datalist) {
		DecimalFormat df = new DecimalFormat("0.000");
		float total = datalist.size();
		float count = 0;

		for (int s = 0 ; s < datalist.size() ; s++) {
			double[] x = datalist.get(s).x;
			int result = datalist.get(s).y;
			if (classify(x) != result) {
				count = count + 1;
			}
		}

		return df.format((count/total));
	}


	//Given two DecisionTree objects, this method checks if both the trees are equal by
	//calling onto the DTNode.equals() method
	public static boolean equals(DecisionTree dt1,  DecisionTree dt2)
	{
		boolean flag = true;
		flag = dt1.rootDTNode.equals(dt2.rootDTNode);
		return flag;
	}

}
