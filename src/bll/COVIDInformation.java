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
public class COVIDInformation {

   protected String generalInfo;
   protected String symptoms;
   protected String rollout;
   protected String timeline;
   protected String fig;
   
   public COVIDInformation(String generalInfo, String symptoms, String rollout, String timeline, String fig) {
       this.generalInfo = generalInfo;
       this.symptoms = symptoms;
       this.rollout = rollout;
       this.timeline = timeline;
       this.fig = fig;
   }
      /**
     * @return the generalInfo
     */
    public String getGeneralInfo() {
        return generalInfo;
    }

    /**
     * @param generalInfo the generalInfo to set
     */
    public void setGeneralInfo(String generalInfo) {
        this.generalInfo = generalInfo;
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
     * @return the rollout
     */
    public String getRollout() {
        return rollout;
    }

    /**
     * @param rollout the rollout to set
     */
    public void setRollout(String rollout) {
        this.rollout = rollout;
    }

    /**
     * @return the timeline
     */
    public String getTimeline() {
        return timeline;
    }

    /**
     * @param timeline the timeline to set
     */
    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    /**
     * @return the fig
     */
    public String getFig() {
        return fig;
    }

    /**
     * @param fig the fig to set
     */
    public void setFig(String fig) {
        this.fig = fig;
    }
}


