/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

/**
 *
 * @author Dylan
 */
public class VaccinationCentre  implements CsvInterface {

    private String centreName;
    private String centreEircode;

    public VaccinationCentre( String centreEircode, String centreName) {
        this.centreName = centreName;
        this.centreEircode = centreEircode;
    }

    /* legacy
    public VaccinationCentre(String[] heading, String[] lineOfCsv) {
        for(int i = 0; i < heading.length; i++){
            if(heading[i].equalsIgnoreCase("centreEircode")){
                this.centreEircode = lineOfCsv[i];
            } else if(heading[i].equals("centreName")){
                this.centreName = lineOfCsv[i];
            }
        }
    }
     */

    public VaccinationCentre() {

    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }

    public String getCentreEircode() {
        return centreEircode;
    }

    public void setCentreEircode(String centreEircode) {
        this.centreEircode = centreEircode;
    }

    public String getListed(){
        return this.centreName + ", "+this.centreEircode;
    }

    @Override
    public String toCsvString() {
        return centreEircode+","+centreName;
    }

    @Override
    public String toString() {
        return centreEircode;
    }
}
