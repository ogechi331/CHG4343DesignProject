public interface CSTR {
    //TODO:  fix variable inputs
    //TODO: verify completeness of interface

    void simulateTransient();
    void respondToSetPointChange();
    void respondToInputChange();
}
