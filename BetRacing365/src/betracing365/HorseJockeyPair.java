package betracing365;

/**
 * Model representing a matched Pair of a Horse and its Jockey.
 * Keeping them in a single object ensures they remain linked during sorting.
 */
public class HorseJockeyPair {
    private String horseName;
    private String jockeyName;

    public HorseJockeyPair(String horseName, String jockeyName) {
        this.horseName = horseName != null ? horseName.trim() : "";
        this.jockeyName = jockeyName != null ? jockeyName.trim() : "";
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName != null ? horseName.trim() : "";
    }

    public String getJockeyName() {
        return jockeyName;
    }

    public void setJockeyName(String jockeyName) {
        this.jockeyName = jockeyName != null ? jockeyName.trim() : "";
    }

    public boolean isValid() {
        return !horseName.isEmpty() && !jockeyName.isEmpty();
    }

    @Override
    public String toString() {
        return horseName + " (Ridden by: " + jockeyName + ")";
    }
}
