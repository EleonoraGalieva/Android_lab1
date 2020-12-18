package com.example.tabataapplication.Models;

import android.graphics.drawable.Drawable;

import com.example.tabataapplication.Action;

public class Phase {
    private int id;
    private int id_sequence;
    private Action actionName;
    private int time;
    private String description;
    private Drawable actionImage;
    //private int setsAmount;

    public Phase(int id_sequence, Action actionName, Drawable actionImage, int time, String description) {
        this.id_sequence = id_sequence;
        this.actionName = actionName;
        this.actionImage = actionImage;
        this.time = time;
        this.description = description;
        //this.setsAmount = setsAmount;
    }

    public Phase(int id, int id_sequence, Action actionName, int time, String description, Drawable actionImage) {
        this.id = id;
        this.id_sequence = id_sequence;
        this.actionName = actionName;
        this.time = time;
        this.description = description;
        this.actionImage = actionImage;
    }

    public int getId() {
        return id;
    }

    public int getId_sequence() {
        return id_sequence;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_sequence(int id_sequence) {
        this.id_sequence = id_sequence;
    }

    public void setActionImage(Drawable actionImage) {
        this.actionImage = actionImage;
    }

    public Drawable getActionImage() {
        return actionImage;
    }

    public void setActionName(Action actionName) {
        this.actionName = actionName;
    }

    public void setTime(int time) {
        this.time = Math.max(time, 0);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionName() {
        String res;
        switch (actionName) {
            case PREPARATION:
                res = "Preparation";
                break;
            case WORK:
                res = "Work";
                break;
            case RELAX:
                res = "Relax";
                break;
            case RELAX_BETWEEN_SETS:
                res = "Relax between sets";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + actionName);
        }
        return res;
    }

    public int getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public static Action stringToEnumValue(String actionName) {
        Action resAction;
        switch (actionName) {
            case "Preparation":
                resAction = Action.PREPARATION;
                break;
            case "Work":
                resAction = Action.WORK;
                break;
            case "Relax":
                resAction = Action.RELAX;
                break;
            case "Relax between sets":
                resAction = Action.RELAX_BETWEEN_SETS;
                break;
            default:
                resAction = null;
                break;
        }
        return resAction;
    }
}
