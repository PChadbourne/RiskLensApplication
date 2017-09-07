import java.util.*;

/*
 * Monte Carlo Simulation for RiskLens application process.
 * 
 * Simulates drawing the first N cards of a shuffled deck, checking if they are all the same suit.
 * Runs this simulation repeatedly (Default: 1000 times) so as to estimate a probability.
 */

public class Main{
	
	public static void main(String[] args){
		
		int numToDraw = 1;
		int iterations = 1000;
		
		//Parse arguments to determine number of cards to draw and iterations to simulate
		if (args.length > 0){
		    try{
		        numToDraw = Integer.parseInt(args[0]);
		    } catch (NumberFormatException e){
		        System.err.println("Argument" + args[0] + " must be an integer.");
		        System.exit(1);
		    }
		    if(numToDraw > 52 || numToDraw <= 0){
		    	System.err.println("First argument must be an integer between 1 and 52 (Inclusive).");
		    }
		    if (args.length > 1){
			    try{
			        iterations = Integer.parseInt(args[1]);
			    } catch (NumberFormatException e){
			        System.err.println("Argument" + args[1] + " must be an integer.");
			        System.exit(1);
			    }
			}
		}
		else{
			System.err.println("Program requires at least one argument.");
			System.exit(1);
		}
		
		MonteCarlo m = new MonteCarlo(numToDraw, iterations);
		
		System.out.println(m.simulate());
	}
}

class Card{
	final char suit; // {C:Clubs, D:Diamonds, H:Hearts, S:Spades}
	final int rank; // Ranks 1 through 13, Ace low, King high.
	
	Card(char suit, int rank){
		this.suit = suit;
		this.rank = rank;
	}
	
	Card(int index){
		switch(index / 13){
			case 0: this.suit = 'C';
			break;
			
			case 1: this.suit = 'D';
			break;
			
			case 2: this.suit = 'H';
			break;
			
			case 3: this.suit = 'S';
			break;
			
			default: System.err.println("Invalid card index attempted");
			this.suit = 'X';
			break;
		}
		this.rank = (index % 13) + 1;
	}
	
	public char getSuit(){
		return this.suit;
	}
	
	public int getRank(){
		return this.rank;
	}
}

class Deck{
	ArrayList<Card> cards;
	
	Deck(){
		cards = new ArrayList<Card>(52);
		for(int i = 0; i < 52; i++){
			cards.add(new Card(i));
		}
	}
	
	Card getCard(int index){
		return cards.get(index);
	}
	
	void shuffle(){
		Collections.shuffle(cards);
	}
}

class MonteCarlo{
	int iterations;
	int firstCards;
	
	Deck d = new Deck();
	
	MonteCarlo(int firstCards, int iterations){
		this.firstCards = firstCards;
		this.iterations = iterations;
	}
	
	float simulate(){
		//Return early if only one card is drawn.
		if(firstCards == 1){
			return 1.0f;
		}
		
		float count = 0.0f;
		
		for(int i = 0; i < iterations; i++){
			d.shuffle();
			boolean sameSuit = true;
			for(int j = 1; j < firstCards; j++){
				if (d.getCard(j-1).getSuit() != d.getCard(j).getSuit()){
					sameSuit = false;
					break;
				}
			}
			if (sameSuit){
				count += 1.0f;
			}
		}
		
		count = count / iterations;
		return count;
	}
}