package resource.util;

/**
 * @author Anirban
 *
 */
public class Order {
	private int numberOfBurgers=1;
	private int numberofFries=0;
	private int cokes=0;
	
	public int getNumberOfBurgers() {
		return numberOfBurgers;
	}
	public void setNumberOfBurgers(int numberOfBurgers) {
		this.numberOfBurgers = numberOfBurgers;
	}
	public int getNumberofFries() {
		return numberofFries;
	}
	public void setNumberofFries(int numberofFries) {
		this.numberofFries = numberofFries;
	}
	public int getNumberOfCokes() {
		return cokes;
	}
	public void setNumberOfCokes(int cokes) {
		this.cokes = cokes;
	}
	
}
