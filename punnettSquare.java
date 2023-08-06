import java.util.*;
class punnettSquare
{
	public static void main (String args[])
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter first parent's genotype, delimiting "
				+ "alleles by / and genes with a space (example: A/a B/b) :");				
		String[] g1 = sc.nextLine().split(" "); 
		//create a 2-dimensional list to hold each of the parent's two alleles for each gene
		String[][] alleles1 = new String[g1.length][2];
		for (int i = 0; i < g1.length; i++)
		{
			//alleles1[i] is a list containing two alleles of the same gene
			alleles1[i] = g1[i].split("/"); 
		}
		
	    //get all possible combinations of genes the parent can transmit
		String[] combos1 = combine(alleles1);
		
		//and do the same thing for the second genotype
		System.out.println("Enter second parent's genotype, delimiting alleles by / and genes by \" \":");
		
		String[] g2 = sc.nextLine().split(" ");		
		String[][] alleles2 = new String[g2.length][2];
		for (int i = 0; i < g2.length; i++)
		{
			alleles2[i] = g2[i].split("/");
		}
		//all combinations of alleles
		String[] combos2 = combine(alleles2); 
		
		String[][] punnett = getpunnett(combos1, combos2);
		printpunnett(punnett, combos1, combos2);
		calculatePercentge(punnett, combos1, combos2);
	}
	
	
	//takes a [n][2] array of n genes and each of the 2 alleles the parent possesses, 
	//and returns a list of all combinations of alleles the parent could give their child
	public static String[] combine (String[][] alleles)
	{
		if (alleles.length == 1) //simplest case
		{
			String[] combos = {alleles[0][0], alleles[0][1]};
			return combos;
		}
		else 
		{
			String[] combos = new String[(int) Math.pow(2, alleles.length)]; //number of combinations.
			//recursive step - only combine n-1 alleles
			String[] other_traits = combine(Arrays.copyOfRange(alleles, 1, alleles.length)); 
			for (int i = 0; i < combos.length; i++)
			{
				combos[i] = other_traits[i%other_traits.length] + alleles[0][i*2/combos.length];
			}
			return combos;
		}
	}
	
	//takes two list of possible genotypes the parents can transmit, 
	//returns a 2-d list of all possibilities a child can inherit
	public static String[][] getpunnett(String[] p1, String[] p2)
	{
		String[][] punnett = new String[p1.length][p2.length];
		for (int i = 0; i < p1.length; i++)
		{
			for (int j = 0; j < p2.length; j++)
			{
				String genotype = "";
				//construct the new genotype
				for (int k = 0; k < p1[i].length(); k++) 
				{
					genotype += p1[i].charAt(k);
					genotype += p2[j].charAt(k);
				}
				punnett[i][j] = genotype;
			}
		}
		return punnett;
	}

	//print a readout of the punnett square
	public static void printpunnett(String[][] punnettInv, String[] combos1, String[] combos2)
	{
		String[][] punnett = new String[combos1.length][combos2.length];;
		for(int i=0;i<combos1.length;i++){    
			for(int j=0;j<combos2.length;j++){    
				punnett[i][j]=punnettInv[j][i];  
			}    
		} 
		System.out.println("Punnett Square:\n" + repeatChar('-', 30));
		String firstParent = repeatChar(' ', punnett[0][0].length()/2 + 1) + "| ";
		for (int i = 0; i < combos1.length; i++) //print first parent's alleles
		{
			firstParent += combos1[i] + repeatChar(' ', punnett[0][0].length()/2 + 1) + "| ";
		}
		System.out.println(firstParent);
		
		for (int i = 0; i < combos2.length; i++)
		{
			String row = combos2[i] + " | ";
			for (int j = 0; j < combos1.length; j++)
			{
				row += punnett[i][j] + " | "; 
			}
			System.out.println(row);
		}
	}
	
	public static void calculatePercentge(String[][] punnett, String[] combos1, String[] combos2){
		Set<String> distinct = new HashSet<String>();
		for(int i=0; i<combos1.length; i++) {
			for(int j=0; j<combos2.length; j++) {
				int counter = 0;
				String currentValue = punnett[i][j];
				char charArray[] = currentValue.toCharArray();
			    Arrays.sort(charArray);
			    String sortedCurrent = new String(charArray);
			    
				for(int k=0; k<combos1.length; k++) {
					for(int l=0; l<combos2.length; l++) {
						char tempArray[] = punnett[k][l].toCharArray();
					    Arrays.sort(tempArray);
					    String sortedTemp = new String(tempArray);
						if(sortedTemp.equals(sortedCurrent)) counter++;
					}
				}
				if(!distinct.contains(sortedCurrent)) {
				System.out.println("The frequency of the " + currentValue + 
					" genotype is " + ((double)counter/(combos1.length*combos2.length))*100 + "%");
				}
				distinct.add(sortedCurrent);
			}
		}
	}

	public static boolean isPresent(String[] distinct, String value){
		
		for(int i=0; i<distinct.length; i++) {
			if(distinct[i] == value) return true;
		}
		
		return false;
	}
	
	public static String repeatChar (char c, int n) 
	{
		String repeated = "";
		for (int i = 0; i < n; i++)
		{
			repeated += c;
		}
		return repeated;
	}

}


