package inheritance;

public class MalicousOverridingMethodDemo {
	public static void main(String[] args) {
		LicenseManager licenseManager = new LicenseManager();
		for (int i=0; i<=Integer.MAX_VALUE; i++) {
			Object guessed = licenseManager.getLicenseKey(new CraftedLicenseType());
			if (guessed!=null)
				System.out.println(guessed);  // ABC_DEF-PQR_XYZ
		}
	}
}
