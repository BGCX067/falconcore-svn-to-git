package org.test.dd;
/* *********************** */
/* UPDATE October 8, 2009. */
/*  ALL INPUTS IN YOUR TEST SUITE MUST CONTAIN EXACTLY 3 INTEGERS */
/*  (NO MORE, NO LESS, AND ONLY INTEGERS) */
/* *********************** */


public class TriType {

	public static void main(String args[]){
		int intArr[] = new int[3];
		int triang = 0;
		for(int cnt=0;cnt<args.length;cnt++){
			intArr[cnt] = Integer.parseInt(args[cnt]);
		}
		int i = intArr[0];
		int j = intArr[1];
		int k = intArr[2];

		if (( i <= 0 ) ||  (j <= 0)  ||  (k < 0))
		{
			triang = 4;
		}
		else
		{
			triang = 0;
			if (i == j)
				triang += 1;
			if (i == k)
				triang += 2;
			if (j == k)
				triang += 3;

			if ( triang == 0)
			{
				/*
	       Confirm it's a legal triangle before declaring
	       it to be scalene
				 */

				if ( i+j <= k  || j+k <= i  || i+k < j)
					triang = 4;
				else
					triang = 1;
			}
			else
			{
				/*
	       Confirm it's a legal triangle before declaring
	       it to be isosceles or equilateral
				 */
				if (triang > 3)
					triang = 3;
				else if (triang == 1 && i+j > k)
					triang = 2;
				else if (triang == 2 && i+k > j)
					triang = 2;
				else if (triang == 3 && j+k > i)
					triang = 2;
				else
					triang = 4;
			}
		}
		System.out.println("Triang : " + triang);
	}
}
