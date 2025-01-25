package Model;

/**
 * This is the InHouse class that is a subclass of superclass Part.<br> This class sets up a new object with an added integer
 * variable of machineId.<br> This class is chosen for a new Part when the radio button InHouse is selected.<br>
 */
public class InHouse extends Part{

    /**
     * Variable that contains the machine Id for that specific InHouse Part.
     */
    private int machineId;

    /**
     * This method is a constructor for InHouse that extends Parts to take in the machineId variable.
     * @param id - The Id for part.
     * @param name - The name for part.
     * @param price - The price for part.
     * @param stock - The stock for part.
     * @param min - The min stock for part.
     * @param max - The max stock for part.
     * @param machineId - The machine Id for part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    /**
     * This method passes in the machine Id variable and sets it to the private machineId variable.
     * @param machineId the machineId to set.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * This method returns the machineId variable.
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }
}
