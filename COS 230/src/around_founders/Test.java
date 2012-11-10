package around_founders;

//Christopher Lenk for Around Founders Fall 2011
//Inspiration from Zach Smith
//Prints out random characters to look like "cool hacker code"

import java.util.Random;

public class Test {

	
	public static void main(String args[]) {
		Random rando = new Random();
		int num;
		int num2;
		Character c;
		
		while (true) {
			num = rando.nextInt(127-32) +32;
			c = (char) num;
			num2 = rando.nextInt(333);
			
			System.out.print(c);
			try {
				if (num > 115) {
					Thread.currentThread().sleep(100);
				}
				if (num2 == 120) {
					System.out.print("Brethren is awesome!");
				} else if (num2 == 121) {
					System.out.print("Hey Cutie!");
				} else if (num2 == 122) {
					System.out.print("Memento");
				} else if (num2 == 123) {
					System.out.print("\n");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
