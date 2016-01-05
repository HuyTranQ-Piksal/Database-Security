package huytranq.databasesecurity;

/**
 * Created by huytr on 04-01-2016.
 */
public class Word {

    public static final String ID = "_id";
    public static final String NAME = "Name";
    public static final String MEANING = "Meaning";
    public static final String EXAMPLE = "Example";
    public static final String PRONOUN = "Pronoun";
    public static final String EXAMPLE_TRANS = "ExampleTrans";

    private int id;
    private String name;
    private String meaning;
    private String example;
    private String pronoun;
    private String exampleTrans;

    public Word(int id, String name, String meaning, String example, String pronoun, String exampleTrans) {
        this.id = id;
        this.name = name;
        this.meaning = meaning;
        this.example = example;
        this.pronoun = pronoun;
        this.exampleTrans = exampleTrans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getPronoun() {
        return pronoun;
    }

    public void setPronoun(String pronoun) {
        this.pronoun = pronoun;
    }

    public String getExampleTrans() {
        return exampleTrans;
    }

    public void setExampleTrans(String exampleTrans) {
        this.exampleTrans = exampleTrans;
    }
}
