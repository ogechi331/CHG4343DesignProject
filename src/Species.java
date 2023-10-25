public class Species implements Cloneable{
    private String name;
    private int coefficient;

    public Species(String name, int coefficient){
        this.name = name;
        this.coefficient = coefficient;
    }

    public Species(Species other){
        if(other == null ){throw new IllegalArgumentException("Cannot copy null Species");}
        this.name = other.name;
        this.coefficient = other.coefficient;
    }
    @Override
    protected Species clone(){
        try{
            return new Species(this);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Failed to clone Species: "+ e.getMessage());
        }
    }
    public int getCoefficient() {
        return coefficient;
    }

    public String getName() {
        return name;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public void setName(String name) {
        this.name = name;
    }

}
