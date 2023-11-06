/** Chemical species class
 * @author Ogechi
 * @version 1.0
 */
public class Species implements Cloneable{
    private String name;
    private int coefficient;

    /**
     *
     * @param name
     * @param coefficient
     * @author Ogechi
     */
    public Species(String name, int coefficient){
        this.name = name;
        this.coefficient = coefficient;
    }

    /**
     *
     * @param other
     * @author Ogechi
     */
    public Species(Species other){
        if(other == null ){throw new IllegalArgumentException("Cannot copy null Species");}
        this.name = other.name;
        this.coefficient = other.coefficient;
    }

    /**
     *
     * @return
     * @author Ogechi
     */
    @Override
    protected Species clone(){
        try{
            return new Species(this);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Failed to clone Species: "+ e.getMessage());
        }
    }

    /**
     *
     * @return
     * @author Ogechi
     */
    public int getCoefficient() {
        return coefficient;
    }

    /**
     *
     * @return
     * @author Ogechi
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param coefficient
     * @author Ogechi
     */
    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    /**
     *
     * @param name
     * @author Ogechi
     */
    public void setName(String name) {
        this.name = name;
    }

}
