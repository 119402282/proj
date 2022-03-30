package bll;

import java.util.Date;

public class InfectiousDiseases {  

    private String name;
    private String information;
    private String symptoms;
    private String remedies;
    private PHA author;
    private Date edited;

    public InfectiousDiseases(String name, String information, String symptoms, String remedies, PHA author, Date edited) {
        this.name = name;
        this.information = information;
        this.symptoms = symptoms;
        this.remedies = remedies;
        this.author = author;
        this.edited = edited;
    }

    public InfectiousDiseases(String name, PHA author, Date edited) {
        this.name = name;
        this.author = author;
        this.edited = edited;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the symptoms
     */
    public String getSymptoms() {
        return symptoms;
    }

    /**
     * @param symptoms the symptoms to set
     */
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    /**
     * @return the remedies
     */
    public String getRemedies() {
        return remedies;
    }

    /**
     * @param remedies the remedies to set
     */
    public void setRemedies(String remedies) {
        this.remedies = remedies;
    }
    

    /**
     * @return the information
     */
    public String getInformation() {
        return information;
    }

    /**
     * @param information the information to set
     */
    public void setInformation(String information) {
        this.information = information;
    }
    
     /**
     * @return the author
     */
    public PHA getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(PHA author) {
        this.author = author;
    }

    /**
     * @return the edited
     */
    public Date getEdited() {
        return edited;
    }

    /**
     * @param edited the edited to set
     */
    public void setEdited(Date edited) {
        this.edited = edited;
    }
    
        @Override
    public String toString(){
        return name;  
    }
    
   
   
}