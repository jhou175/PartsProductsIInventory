package Model;

/**
 * This is the Outsourced class that is a subclass of superclass Part. <br>This class sets up a new object with an added String
 * variable of companyName. <br>This class is chosen for a new Part when the radio button Outsourced is selected.
 */
public class Outsourced extends Part{

    /**
     * Variable that contains the name of an Outsourced company.
     */
    private String companyName;

    /**
     * This method is the constructor for Outsourced that extends superclass Part to take in the companyName variable.
     * @param id - The Id for part.
     * @param name - The name for part.
     * @param price - The price for part.
     * @param stock - The stock for part.
     * @param min - The min stock for part.
     * @param max - The max stock for part.
     * @param companyName - The company name for part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    /**
     * This method passes in the companyName variable and sets it to the private companyName variable.
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This method returns the companyName variable.
    *@return the companyName
    */
    public String getCompanyName() {
        return companyName;
    }
}
