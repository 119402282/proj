/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import dal.DataAccess;

/**
 *
 * @author Henry
 */
public class PHA extends User{

    public PHA(String userId, String fName, String lName, String password) {
        super(userId, fName, lName, password, "PHA");

    }

    public PHA(User chad) {
        super(chad.getUserId(),
                chad.getFName(),
                chad.getLName(),
                chad.getPassword(),
                "PHA");
    }

    @Override
    public String toCsvString() {
        return this.getUserId()+",";
    }
    public String superCsvString() {
        return this.getUserId() + "," + this.getFName() + "," + this.getLName() + "," + this.getPassword() + "," + this.getAccountType();
    }
    @Override
    public String toString() {
        return "PHA{" +
                   "} " + super.toString();
    }
}
