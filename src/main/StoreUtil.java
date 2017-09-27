package main;

public class StoreUtil {

    public enum Months {
        JANUARY("01"),
        FEBRUARY("02"),
        MARCH("03"),
        APRIL("04"),
        MAY("05"),
        JUNE("06"),
        JULY("07"),
        AUGUST("08"),
        SEPTEMBER("09"),
        OCTOBER("10"),
        NOVEMBER("11"),
        DECEMBER("12");

        private final String text;

        private Months(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
    
    public enum Countries {
        FRANCE("France"),
        USA("USA"),
        UK("UK");

        private final String text;

        private Countries(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
    
    public enum ProductNames {
        CESSNA152("Cessna 152"),
        CESSNA172("Cessna 172"),
        CESSNA182("Cessna 182");

        private final String text;

        private ProductNames(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
