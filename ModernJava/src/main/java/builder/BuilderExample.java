package builder;

public class BuilderExample {

    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;

    public static class Builder {

        private int servingSize;
        private int servings;

        private int calories = 0;
        private int fat = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public BuilderExample build() {
            return new BuilderExample(this);
        }

    }

    private BuilderExample(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
    }

    public void runExample() {
        BuilderExample be = new Builder(100, 5).calories(400).fat(50).build();

        System.out.println("Calories: " + be.calories);
        System.out.println("Servings: " + be.servings);


    }

}
