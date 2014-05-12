import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class PaymentMachineClass {

	private int [][] denominations = new int[][]{
			{20000, 0},
			{10000, 0},
			{5000, 0},
			{2000, 0},
			{1000, 0},
			{500, 0},
			{200, 0},
			{100, 0},
			{50, 0},
			{20, 0},
			{10, 0},
			{5, 0},
			{2, 0},
			{1, 0}
	};

	private Set<TicketClass> paidTickets = new HashSet<>(); 
	
	private boolean CheckTicketAlreadyPaid(int ticketNumber){
		boolean result = false;
		for (TicketClass ticket : paidTickets){
			if (ticket.getTicketNumber() == ticketNumber) {result = true;}
		}
		if (result) System.out.println("Ticket number already exists in set (= already paid)");
		return result;
	}
	
	private boolean IsDenominationValid(int input){
		boolean result = false;
		for (int[] element: denominations){
			if (element[0] == input) result = true;
		}
		return result;
	}
	
	private void IncreaseDenominationByOne(int denomination){
		for (int[] element : denominations){
			if (element[0] == denomination) element[1]++;
		}		
	}
	
	private void ParseMoniesInserted(String input){
		for (String element : input.split(" ")){
			if (element != "") this.IncreaseDenominationByOne(Integer.parseInt(element));
		}
	}

	private boolean GiveChange(int amount){
		boolean result = true;
		int amountRemaining = amount; 
		String changeSequence = "";
		
		for (int x = 0; x < this.denominations.length; x++){
			
			// use the same denomination until it is smaller than the remaining amount
			while ( (this.denominations[x][0] < amountRemaining) && (this.denominations[x][1] > 0) ) {
				if (this.denominations[x][1] > 0) {
					changeSequence += (changeSequence == "") ? String.valueOf(this.denominations[x][0]) : " " + String.valueOf(this.denominations[x][0]);
					this.denominations[x][1]--;
					amountRemaining -= this.denominations[x][0];
					System.out.println("changesequence: " + changeSequence);
				}
			}
		}
		if (amountRemaining > 0) result = false;
		else System.out.println("List of change given: " + changeSequence);;
		return result;
	}
	

	public static void main(String[] args) {
		PaymentMachineClass paymentMachine = new PaymentMachineClass();
		int ticketNumber; 
		int ticketPrice;
		int nextCoinOrNote;
		int totalMoneyPaid;
		String moneySequence;
		Random random = new Random(); 
		Scanner sc = new Scanner(System.in);
	
		// read a ticket number (until a non numeric value entered)
		do {
		    System.out.print("\nEnter ticket number: ");
		    ticketNumber = sc.hasNextInt() ? sc.nextInt() : -1;
		    
		    if ((ticketNumber > -1) && !(paymentMachine.CheckTicketAlreadyPaid(ticketNumber))) {
		    	ticketPrice = random.nextInt(10000);
	    	   	TicketClass currentTicket = new TicketClass(ticketNumber, ticketPrice);

	    	   	// read monies were put into the machine
	    	   	System.out.println("Put " + ticketPrice + " HUFs into the machine");
	    	   	totalMoneyPaid = 0;
	    	   	moneySequence = "";
	    	   	do {
	    	   		nextCoinOrNote = sc.hasNextInt() ? sc.nextInt() : 0;
	    	   		if (nextCoinOrNote <= 0) {
	    	   			System.out.println("Invalid amount");
	    	   			if (sc.hasNext()) sc.next(); // skip the invalid item
	    	   		}
	    	   		else if (!paymentMachine.IsDenominationValid(nextCoinOrNote))
	    	   			System.out.println("Invalid denomination"); 
	    	   		else{
	    	   			totalMoneyPaid += nextCoinOrNote;
	    	   			System.out.println(totalMoneyPaid + " HUFs inserted so far");
	    	   			moneySequence +=  (moneySequence == "") ? nextCoinOrNote : " " + nextCoinOrNote;
	    	   		}
	    	   	} while (totalMoneyPaid < ticketPrice);
		    	
		    	paymentMachine.paidTickets.add(currentTicket);

		    	paymentMachine.ParseMoniesInserted(moneySequence);
		    	
		    	if (paymentMachine.GiveChange(totalMoneyPaid - ticketPrice)) {
		    		System.out.println("Please take your change");
		    	}
		    	else {
		    		System.out.println("Not enough coins or banknotes in the machine \n" + 
		    	                       "Please make contact with customer support");
		    	}	    		    	
		    }

		} while (ticketNumber > -1);
		
		sc.close();
		System.out.println("Finished");
	}
}
