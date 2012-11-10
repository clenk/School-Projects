// By Christopher Lenk for COS 210

import java.util.Scanner;

public class Lab1 {
	
	public static void main(String args[]) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the first binary number: ");
		int a = Integer.parseInt(sc.nextLine(), 2);
		
		System.out.print("Enter the second binary number: ");
		int b = Integer.parseInt(sc.nextLine(), 2);
		
		System.out.println("");

		int and = a & b;
		int or = a ^ b;
		int xor = a | b;
		
		System.out.println("AND: " + Integer.toBinaryString(and));
		System.out.println("XOR: " + Integer.toBinaryString(xor));
		System.out.println("OR: " + Integer.toBinaryString(or));
	}
}
