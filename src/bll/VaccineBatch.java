/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import java.util.Date;

import static bll.CsvInterface.*;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;


/**
 *
 * @author Henry Cullen
 */
public class VaccineBatch extends Vaccine {

    private int batchNo;
    private int orderQty;
    private int qtyUsed;
    private Date expiryDate;

    public VaccineBatch(int vaccId, String vaccName, boolean vaccAuthorized, String vaccTypeDesc, int reqDoses, double vaccineTemp, double efficacy, int batchNo, int orderQty, int qtyUsed, Date dateEx) {
        super(vaccId, vaccName, vaccAuthorized, vaccTypeDesc, reqDoses, vaccineTemp, efficacy);
        this.setBatchNo(batchNo);
        this.setOrderQty(orderQty);
        this.setQtyUsed(qtyUsed);
        this.setExpiryDate(dateEx);
    }

    /* Legacy
    public VaccineBatch( Vaccine theVaccine, String[] heading, String[] lineOfCsv) {
        this.setVaccId(theVaccine.getVaccId());
        this.setVaccName(theVaccine.getVaccName());
        this.setVaccAuthorized(theVaccine.isVaccAuthorized());
        this.setVaccTypeDesc(theVaccine.getVaccTypeDesc());
        this.setReqDoses(theVaccine.getReqDoses());
        this.setVaccineTemp(theVaccine.getVaccineTemp());
        this.setEfficacy(theVaccine.getEfficacy());

        for(int i = 0; i < heading.length; i++){

            if(heading[i].equals("batchNo")){
                this.batchNo = parseInt(lineOfCsv[i]);
            } else if(heading[i].equals("orderQty")){
                this.orderQty = parseInt(lineOfCsv[i]);
            } else if(heading[i].equals("qtyUsed")){
                this.qtyUsed = parseInt(lineOfCsv[i]);
            } else if (heading[i].equals("expiryDate")){
                this.setExpiryDate(lineOfCsv[i]);
            }
        }
    }
     */
    public VaccineBatch( Vaccine theVaccine, int batchNo, int orderQty, int qtyUsed, Date dateEx) {
        this.setVaccId(theVaccine.getVaccId());
        this.setVaccName(theVaccine.getVaccName());
        this.setVaccAuthorized(theVaccine.isVaccAuthorized());
        this.setVaccTypeDesc(theVaccine.getVaccTypeDesc());
        this.setReqDoses(theVaccine.getReqDoses());
        this.setVaccineTemp(theVaccine.getVaccineTemp());
        this.setEfficacy(theVaccine.getEfficacy());
        this.setBatchNo(batchNo);
        this.setOrderQty(orderQty);
        this.setQtyUsed(qtyUsed);
        this.setExpiryDate(dateEx);
    }
    public int getBatchNo() {
        return batchNo;
    }
    
    public String getBatchNoAsString() {
        return valueOf(batchNo);
    }

    public void setBatchNo(int batchNo) {
        this.batchNo = batchNo;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public int getQtyUsed() {
        return qtyUsed;
    }

    public void setQtyUsed(int qtyUsed) {
        this.qtyUsed = qtyUsed;
    }

    public void incrementUsed() {
        this.qtyUsed += 1;
    }
    
    public int getQtyRemaining() {
        return orderQty-qtyUsed;
    }
    
    public String getQtyRemainingAsString() {
        return String.valueOf(getQtyRemaining());
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public String getExpiryDateString() {
        return dateToString(this.getExpiryDate());
    }

    public String getExpiryDateShort() {
        return dateToStringShort(this.getExpiryDate());
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        setExpiryDate(toDate(expiryDate));
    }


    @Override
    public String toCsvString() {
        return batchNo+","+this.getVaccIdString()+","+orderQty+","+qtyUsed+","+getExpiryDateString();
    }

    @Override
    public String toString() {
        return String.valueOf(batchNo);
    }
}
