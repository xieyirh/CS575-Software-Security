
package inheritance;

public class LicenseType {
	private String type;
	
	public String getType(){
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	//override
	public int hashCode() {
		int res =17; 
		res= res *31 + type == null? 0: type.hashCode();
		return res;
	}
	
	// override
	public boolean equals(Object arg) {
		if (arg==null || !(arg instanceof LicenseType)) {
			return false;
		}
		return type.equals(((LicenseType) arg).getType());  
	}
}
