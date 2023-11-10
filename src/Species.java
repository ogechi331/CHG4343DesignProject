/** Chemical species class
 * @author Ogechi
 * @author Dylan
 * @version 1.3
 */
public class Species implements Cloneable{
    private String name;
    private int coefficient;

    /** Constructor for species object
     *
     * @param name string name for species i.e A, Na or Cl
     * @param coefficient reaction equation coefficient for the species
     * @author Ogechi
     */
    public Species(String name, int coefficient){
        this.name = name;
        this.coefficient = coefficient;
    }

    /** Copy constructor for species object
     *
     * @param source species object to copy
     * @throws IllegalArgumentException cannot copy null object
     * @author Ogechi
     */
    public Species(Species source){
        if(source == null ){throw new IllegalArgumentException("Cannot copy null Species");}
        this.name = source.name;
        this.coefficient = source.coefficient;
    }

    /** Clone method to copy species object
     *
     * @return copy of species object
     * @throws IllegalArgumentException cannot copy null object
     * @author Ogechi
     * @author Dylan
     */
    @Override
    public Species clone(){
        try{
            return new Species(this);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Failed to clone Species: "+ e.getMessage());
        }
    }

    /** Accessor method for species coefficient
     *
     * @return species coefficient
     * @author Ogechi
     */
    public int getCoefficient() {
        return coefficient;
    }

    /** Accessor method for species name
     *
     * @return species name
     * @author Ogechi
     */
    public String getName() {
        return name;
    }

    /** Mutator method for species coefficient
     *
     * @param coefficient takes species coefficient
     * @return true when updated
     * @author Ogechi
     * @author Dylan
     */
    public boolean setCoefficient(int coefficient) {
        this.coefficient = coefficient;
        return true;
    }

    /** Mutator method for species name
     *
     * @param name takes species name
     * @return true when updated
     * @author Ogechi
     * @author Dylan
     */
    public boolean setName(String name) {
        this.name = name;
        return true;
    }

    /** Equals method for species object
     *
     * @param comparator Object to compare
     * @return true if all instant variables and class type are equal, otherwise returns false
     * @author Dylan
     */
    public boolean equals(Object comparator) {
        if (comparator==null) return false;
        if (comparator.getClass()!=this.getClass()) return false;
        Species specificComparator = (Species)comparator;
        if (!(specificComparator.name.equals(this.name))) return false;
        if (specificComparator.coefficient != this.coefficient) return false;
        return true;
    }

}
