/** Reaction class
 * @author Ogechi
 * @author Dylan
 * @version 1.2
 */
public class Reaction implements Cloneable{

    private Species[] reactants;
    private Species[] products;
    private Species limitingReactant;
    private double k;

    /** Constructor for reaction object
     *
     * @param rEquation string version of the reaction equation with a -> deliminator
     * @param k rate constant k
     * @author Ogechi
     */
    public Reaction(String rEquation, double k){
        parseReactionEquation(rEquation);
        this.k = k;
    }

    /** Copy constructor for reaction object
     *
     * @param source reaction object to copy
     * @throws IllegalArgumentException cannot copy null reaction object
     * @author Ogechi
     */
    public Reaction(Reaction source){
        if (source==null) throw new IllegalArgumentException("Error, copy of null reaction object");

        this.reactants = new Species[source.reactants.length];
        this.products = new Species[source.products.length];
        for(int i = 0; i < source.reactants.length; i++){
            this.reactants[i] = source.reactants[i].clone();
        }
        for(int i = 0; i < source.products.length; i++){
            this.products[i] = source.products[i].clone();
        }
        this.limitingReactant = source.limitingReactant;
        this.k = source.k;
    }

    /** Clone method for reaction object
     *
     * @return a copy of reaction object
     * @throws IllegalArgumentException cannot clone null object
     * @author Ogechi
     * @author Dylan
     */
    @Override
    public Reaction clone(){
        try{
            return new Reaction(this);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Failed to clone reaction: "+ e.getMessage());
        }
    }

    /** Accessor method for reaction rate constant k
     *
     * @return reaction rate constant k
     * @author Ogechi
     */
    public double getK() {
        return k;
    }

    /** Accessor method for limiting reactant
     *
     * @return limiting reactant species
     * @author Ogechi
     */
    public Species getLimitingReactant() {
        return limitingReactant;
    }

    /** Accessor method for products species
     *
     * @return array of product species
     * @author Ogechi
     */
    public Species[] getProducts() {
        return products.clone();
    }

    /** Mutator method for reaction rate constant k
     *
     * @param k reaction rate constant k
     * @return true when updated
     * @author Ogechi
     * @author Dylan
     */
    public boolean setK(double k) {
        this.k = k;
        return true;
    }

    /** Mutator method for limiting reactant
     *
     * @param limitingReactant limiting reactant species
     * @return true if updated or false if not updated
     * @author Ogechi
     * @author Dylan
     */
    public boolean setLimitingReactant(Species limitingReactant) {
        if (limitingReactant==null) return false;
        this.limitingReactant = limitingReactant;
        return true;
    }

    /** Mutator method for products species
     *
     * @param products array of products species
     * @return true if updated or false if not updated
     * @author Ogechi
     * @author Dylan
     */
    public boolean setProducts(Species[] products) {
        if (products==null) return false;
        for (int i=0; i< products.length; i++) {
            if(products[i]==null) return false;
        }
        this.products = new Species[products.length];
        for(int i = 0; i < products.length; i++) {
            this.products[i] = products[i].clone();
        }
        return true;
    }

    /** Mutator method for reactants species
     *
     * @param reactants array of reactants species
     * @return true if updated or false if not updated
     * @author Ogechi
     * @author Dylan
     */
    public boolean setReactants(Species[] reactants) {
        if (reactants==null) return false;
        for (int i=0; i< reactants.length; i++) {
            if(reactants[i]==null) return false;
        }
        this.reactants = new Species[reactants.length];
        for(int i = 0; i < reactants.length; i++) {
            this.reactants[i] = reactants[i].clone();
        }
        return true;
    }

    /** Private method to calculate the limiting reactant
     *
     * @param concentrations concentrations array
     * @throws IllegalArgumentException reactants and products must not be null
     * @author Ogechi
     */
    private void calculateLimitingReactant(double[] concentrations) throws IllegalArgumentException {
        if (reactants == null || products == null){
            throw new IllegalArgumentException("Reactants/Products cannot be null");
        }
        //assumes that the order of species in concentration array matches the order of species in the reactants array
        double mol = Double.MAX_VALUE;
        for(int i = 0; i < reactants.length; i++){
            double reactantConcentration = concentrations[i];
            double coefficient = reactants[i].getCoefficient();
            double curr_mol = reactantConcentration / coefficient;

            if(curr_mol < mol){
                mol = curr_mol;
                limitingReactant = reactants[i];
            }

        }
    }

    /** Private method to parse reaction equation into products and reactants
     *
     * @param rEquation takes reaction equation string
     * @author Ogechi
     */
    private void parseReactionEquation(String rEquation) {
        String[] reactionParts = rEquation.split("->");

        if (reactionParts.length != 2){
            throw new IllegalArgumentException("Reaction Equation is not in proper format");
        }
        String reactantParts = reactionParts[0].trim();
        String productParts = reactionParts[1].trim();

        reactants = parseSpeciesList(reactantParts);
        products = parseSpeciesList(productParts);
    }

    /** Private method to parse reactants or products list into individual species
     *
     * @param speciesList takes array of species
     * @return array of individual species
     * @author Ogechi
     */
    private Species[] parseSpeciesList(String speciesList){
        String[] speciesStrings = speciesList.split("\\+");
        Species[] species = new  Species[speciesStrings.length];

        for(int i=0; i<species.length; i++){
            species[i] = parseSpecies(speciesStrings[i]);
        }
        return species;
    }

    /** Private method to parse species into coefficient and name
     *
     * @param speciesString takes species string
     * @return individual species object
     * @author Ogechi
     */
    private Species parseSpecies(String speciesString) {
        speciesString = speciesString.trim();
        int coefficient = 1;
        if (speciesString.matches(".*\\d.*")) {
            coefficient = Integer.parseInt(speciesString.split(" ")[0]);
            speciesString = speciesString.replaceAll(".*\\d.*", "").trim(); // Remove coefficients
        }
        return new Species(speciesString, coefficient);
    }


    /**
     * Calculates the reaction rate with the specified concentrations.
     * Assumes the order of species in the concentrations array matches the order of species in the reactants array.
     * @param concentrations  array of concentrations
     * @return reaction rate
     * @throws NullPointerException if the reactants array or concentration array is null
     * @author Ogechi
     * @author Dylan
     */
    public double calculateReactionRate(double[] concentrations, int currentSpecies) throws NullPointerException{
        if(reactants == null || concentrations == null){
            throw new NullPointerException("Reactants and concentrations cannot be null");
        }
        double prod = 1;

        for(int i = 0; i < reactants.length; i++) {
            prod *= Math.pow(concentrations[i], reactants[i].getCoefficient());
        }

        //added check to determine if consumption or generation
        if ((currentSpecies+1)<=this.reactants.length) {
            prod=prod*(-1);
        }
        return k*prod;
    }

}
