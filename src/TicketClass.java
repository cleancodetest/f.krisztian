
public class TicketClass {
	private int ticketNumber;
	private int ticketPrice;
	
	public void setTicketNumber(int ticketNumber){
		this.ticketNumber = ticketNumber;
	}

	public void setTicketPrice(int ticketPrice){
		this.ticketPrice = ticketPrice;
	}

	public int getTicketNumber(){
		return ticketNumber;
	}
	
	public int getTicketPrice(){
		return ticketPrice;
	}
	
	public TicketClass(int ticketNumber, int ticketPrice){
		setTicketNumber(ticketNumber);
		setTicketPrice(ticketPrice);
	}

	public TicketClass(){
		this(-1, -1);
	}

	
}
