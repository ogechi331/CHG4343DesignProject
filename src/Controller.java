public interface Controller {
    //TODO: Remove or use

    double calculateControlOutput();
    void setConstants();
    void applyCohenCoonTuning();
    //void tunePIDParameters(); -> bonus
}
