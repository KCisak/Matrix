package app;
 
public enum Option {
    EXIT(0, "Wyjście z programu"),
    ADD_BAR(1, "Dodanie preta"),
    ADD_FORCE(2,"Dodanie sily"),
    ADD_SUPPORT(3,"Dodanie podpory"),  
    PRINT(4, "Wyświetlenie danych"),
    CALCULATE(5, "Oblicz"),
	CORRECT(6, "Napraw");
 
    private int value;
    private String description;
 
    public int getValue() {
        return value;
    }
 
    public String getDescription() {
        return description;
    }
 
    Option(int value, String desc) {
        this.value = value;
        this.description = desc;
    }
     
    @Override
    public String toString() {
        return value + " - " + description;
    }
     
    public static Option createFromInt(int option) {
        return Option.values()[option];
    }
}