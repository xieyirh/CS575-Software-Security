package inheritance;

public class CraftedLicenseType extends LicenseType{
	private static int guessedHashCode =0;

	//override
	public int hashCode(){
		// return a new hashCode to test every time get() is called
		guessedHashCode++;
		return guessedHashCode;
	}
	
	//override parent class
	public boolean equals(Object arg){
		return true; 
	}

}
