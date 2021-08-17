package model;

public enum Search {
    ID,
    NUMBER,
    PIN,
    BALANCE;
    @Override
    public String toString() {
        switch (this) {
            case ID:
                return "id";
            case NUMBER:
                return "number";
            case PIN:
                return "pin";
            case BALANCE:
                return "balance";
            default:
                return "unspecified";
        }
    }
}
