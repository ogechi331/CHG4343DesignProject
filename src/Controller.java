public interface Controller {
    //TODO: fix variable inputs
    //TODO: verify completeness of interface

    double calculateControlOutput();
    void setConstants();
    void applyCohenCoonTuning();
    //void tunePIDParameters(); -> bonus
}
