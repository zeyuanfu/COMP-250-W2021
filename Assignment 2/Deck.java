package assignment2;

import java.util.Random;

public class Deck {
 public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
 public static Random gen = new Random();

 public int numOfCards; // contains the total number of cards in the deck
 public Card head; // contains a pointer to the card on the top of the deck


 public Deck(int numOfCardsPerSuit, int numOfSuits) {

	 if (numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13) throw new IllegalArgumentException("Please enter a valid number of cards");
	 if (numOfSuits < 1 || numOfSuits > suitsInOrder.length) throw new IllegalArgumentException("Please enter a valid number of suits");
	 
	 head = null;
	 numOfCards = 0;
	 
	 for (int i = 0; i < numOfSuits; i++) {
		 
		 for (int j = 1; j <= numOfCardsPerSuit; j++) {
			 Card newCard = new PlayingCard(suitsInOrder[i], j);
			 addCard(newCard);
		 }
	 }
	 
	 Card redJoker = new Joker("red");
	 Card blackJoker = new Joker("black");
	 addCard(redJoker);
	 addCard(blackJoker);
 }

 /* 
  * TODO: Implements a copy constructor for Deck using Card.getCopy().
  * This method runs in O(n), where n is the number of cards in d.
  */
 public Deck(Deck d) {
	 
	 head = null;
	 numOfCards = 0;
	 Card current = d.head;
	 
	 for (int i = 0; i < d.numOfCards; i++) {
		 addCard(current.getCopy());
		 current = current.next;
	 }

 }

 /*
  * For testing purposes we need a default constructor.
  */
 public Deck() {}

 /* 
  * TODO: Adds the specified card at the bottom of the deck. This 
  * method runs in $O(1)$. 
  */
 public void addCard(Card c) {
  
	 if (head == null) {
		 head = c;
		 head.next = head;
		 head.prev = head;
	 }
	 
	 else {
		 c.prev = head.prev;
		 head.prev.next = c;
		 c.next = head;
		 head.prev = c;
	 }
	 
	 this.numOfCards++;
 }

 /*
  * TODO: Shuffles the deck using the algorithm described in the pdf. 
  * This method runs in O(n) and uses O(n) space, where n is the total 
  * number of cards in the deck.
  */
 public void shuffle() {

	 Card[] copy = new Card[this.numOfCards];
	 Card current = head;
	 
	 for (int i = 0; i < this.numOfCards; i++) {
		 copy[i] = current;
		 current = current.next;
	 }
	 
	 for (int i = copy.length - 1; i > 0; i--) {
		 
		 int j = gen.nextInt(i+1);
		 Card temp = copy[i];
		 copy[i] = copy[j];
		 copy[j] = temp;
	 }
	 
	 head = null;
	 numOfCards = 0;
	 
	 for (int i = 0; i < copy.length; i++) {
		 addCard(copy[i]);
	 }
	 
 }

 /*
  * TODO: Returns a reference to the joker with the specified color in 
  * the deck. This method runs in O(n), where n is the total number of 
  * cards in the deck. 
  */
 public Joker locateJoker(String color) {

	 Card current = head;

	 if (numOfCards == 0) return null;
	 for (int i = 0; i < numOfCards; i++) {
		 
		 if (current instanceof Joker) {
			if(((Joker) current).getColor().equalsIgnoreCase(color)) {
			 return (Joker) current;
			}
		 }
		 
		 current = current.next;
	 }

	 return null;
 }

 /*
  * TODO: Moved the specified Card, p positions down the deck. You can 
  * assume that the input Card does belong to the deck (hence the deck is
  * not empty). This method runs in O(p).
  */
 public void moveCard(Card c, int p) {

	 Card moveRightOf = c;
	 p = p % (numOfCards-1);
	 
	 if (p != 0) {
	 
	 for (int i=0; i < p; i++) moveRightOf = moveRightOf.next;
	 
	 c.prev.next = c.next;
	 c.next.prev = c.prev;
	 moveRightOf.next.prev = c;
	 c.next = moveRightOf.next;
	 moveRightOf.next = c;
	 c.prev = moveRightOf;
	 }
	 
 }

 /*
  * TODO: Performs a triple cut on the deck using the two input cards. You 
  * can assume that the input cards belong to the deck and the first one is 
  * nearest to the top of the deck. This method runs in O(1)
  */
 public void tripleCut(Card firstCard, Card secondCard) {

	 if(firstCard == head) {
		 head = secondCard.next;
	 }
	 
	 else if (secondCard == head.prev) {
		 head = firstCard;
	 }
	 
	 else {
		 
	 Card tempHead = head;
	 Card tempTail = head.prev;
	 head = secondCard.next;
	 head.prev = firstCard.prev;
	 head.prev.next = head;
	 tempTail.next = firstCard;
	 firstCard.prev = tempTail;
	 secondCard.next = tempHead;
	 tempHead.prev = secondCard;
	 }
 }

 /*
  * TODO: Performs a count cut on the deck. Note that if the value of the 
  * bottom card is equal to a multiple of the number of cards in the deck, 
  * then the method should not do anything. This method runs in O(n).
  */
 public void countCut() {

	 int numToCount = head.prev.getValue() % numOfCards;
	 
	 if(numToCount == 0 || numToCount == numOfCards-1) return;
	 
	 Card current = head;
	 
	 	for(int i=0; i < numToCount - 1; i++) {
	 		current = current.next;
	    }
	    
	 Card beforeHead = head.prev.prev;
	 Card tempLast = head.prev;
	 Card afterTempLast = current.next;
	 beforeHead.next = head;
	 head.prev = beforeHead;
	 current.next = tempLast;
	 tempLast.prev = current;
	 head = afterTempLast;
	 head.prev = tempLast;
	 tempLast.next = head;
 }

 /*
  * TODO: Returns the card that can be found by looking at the value of the 
  * card on the top of the deck, and counting down that many cards. If the 
  * card found is a Joker, then the method returns null, otherwise it returns
  * the Card found. This method runs in O(n).
  */
 public Card lookUpCard() {

	 int numToCount = head.getValue() % this.numOfCards;
	 
	 Card endOfCount = head;
	 
	 for (int i=0; i < numToCount; i++) endOfCount = endOfCount.next;
	 
	 if (endOfCount instanceof Joker) return null;
	 
	 else return endOfCount;
 }

 /*
  * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
  * using this deck. This method runs in O(n).
  */
 public int generateNextKeystreamValue() {

	 Joker redJoker = locateJoker("red");
	 Joker blackJoker = locateJoker("black");
	 
	 moveCard(redJoker, 1);
	 moveCard(blackJoker, 2);
	 
	 Card current = head;
	 while (!(current instanceof Joker)) current = current.next;
	 Joker joker1 = (Joker) current;
	 current = current.next;
	 while (!(current instanceof Joker)) current = current.next;
	 Joker joker2 = (Joker) current;
	 tripleCut(joker1, joker2);
	 
	 countCut();
	 
	 Card result = lookUpCard();
	 
	 if (result != null) {
		 int keystreamValue = result.getValue();
		 return keystreamValue;
	 }
	 else return generateNextKeystreamValue();

 }

 public abstract class Card { 
  public Card next;
  public Card prev;

  public abstract Card getCopy();
  public abstract int getValue();

 }

 public class PlayingCard extends Card {
  public String suit;
  public int rank;

  public PlayingCard(String s, int r) {
   this.suit = s.toLowerCase();
   this.rank = r;
  }

  public String toString() {
   String info = "";
   if (this.rank == 1) {
    //info += "Ace";
    info += "A";
   } else if (this.rank > 10) {
    String[] cards = {"Jack", "Queen", "King"};
    //info += cards[this.rank - 11];
    info += cards[this.rank - 11].charAt(0);
   } else {
    info += this.rank;
   }
   //info += " of " + this.suit;
   info = (info + this.suit.charAt(0)).toUpperCase();
   return info;
  }

  public PlayingCard getCopy() {
   return new PlayingCard(this.suit, this.rank);   
  }

  public int getValue() {
   int i;
   for (i = 0; i < suitsInOrder.length; i++) {
    if (this.suit.equals(suitsInOrder[i]))
     break;
   }

   return this.rank + 13*i;
  }

 }

 public class Joker extends Card{
  public String redOrBlack;

  public Joker(String c) {
   if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
    throw new IllegalArgumentException("Jokers can only be red or black"); 

   this.redOrBlack = c.toLowerCase();
  }

  public String toString() {
   //return this.redOrBlack + " Joker";
   return (this.redOrBlack.charAt(0) + "J").toUpperCase();
  }

  public Joker getCopy() {
   return new Joker(this.redOrBlack);
  }

  public int getValue() {
   return numOfCards - 1;
  }

  public String getColor() {
   return this.redOrBlack;
  }
 }

}
