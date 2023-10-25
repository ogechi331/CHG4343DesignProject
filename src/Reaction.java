public class Reaction implements Cloneable{

    private Species[] reactants;
    private Species[] products;
    private Species limitingReactant;
    private double k;


    public Reaction(String rEquation, double k){
        parseReactionEquation(rEquation);
        this.k = k;
    }
    public Reaction(Reaction other){
        this.reactants = new Species[other.reactants.length];
        this.products = new Species[other.products.length];
        for(int i = 0; i < other.reactants.length; i++){
            this.reactants[i] = other.reactants[i].clone();
        }
        for(int i = 0; i < other.products.length; i++){
            this.products[i] = other.products[i].clone();
        }
        this.limitingReactant = other.limitingReactant;
        this.k = other.k;
    }

    @Override
    protected Reaction clone(){
        return new Reaction(this);
    }

    public double getK() {
        return k;
    }

    public Species getLimitingReactant() {
        return limitingReactant;
    }

    public Species[] getProducts() {
        return products.clone();
    }

    public void setK(double k) {
        this.k = k;
    }

    public void setLimitingReactant(Species limitingReactant) {
        this.limitingReactant = limitingReactant;
    }

    public void setProducts(Species[] products) {
        this.products = new Species[products.length];
        for(int i = 0; i < products.length; i++) {
            this.products[i] = products[i].clone();
        }
    }

    public void setReactants(Species[] reactants) {
        this.reactants = new Species[reactants.length];
        for(int i = 0; i < reactants.length; i++) {
            this.reactants[i] = reactants[i].clone();
        }
    }

    private void calculateLimitingReactant(double[] concentrations){
        if (reactants == null || products == null){
            throw new NullPointerException("Reactants/Products cannot be null");
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
    private Species[] parseSpeciesList(String speciesList){
        String[] speciesStrings = speciesList.split("\\+");
        Species[] species = new  Species[speciesStrings.length];

        for(int i=0; i<species.length; i++){
            species[i] = parseSpecies(speciesStrings[i]);
        }
        return species;
    }

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
     */
    public double calculateReactionRate(double[] concentrations) throws NullPointerException{
        if(reactants == null || concentrations == null){
            throw new NullPointerException("Reactants and concentrations cannot be null");
        }
        double prod = 1;

        for(int i = 0; i < reactants.length; i++){
            prod *= Math.pow(concentrations[i],reactants[i].getCoefficient());
        }
        return k*prod;

    }
    public void updateCompositions(){//TBD
        throw new UnsupportedOperationException();
    }


}
