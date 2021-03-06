package ua.com.lhjlbjyjd.sibur;

import android.content.Context;
import android.provider.Settings;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lhjlbjyjd on 25.11.2017.
 */

public class Task implements Serializable{
    private String name;
    private boolean isExecuting = false;
    private String executorID;
    private Goal[] goals;
    private boolean fulfilled = false;
    private int id;

    Task(int id, String name, boolean state, String executorID, Goal[] goals){
        this.id = id;
        this.name = name;
        this.isExecuting = state;
        this.executorID = executorID;
        this.goals = goals;
    }

    public int getId() {
        return id;
    }

    public String getExecutorID() {
        return executorID;
    }

    public String getName() {
        return name;
    }

    public boolean isExecuting() {
        return isExecuting;
    }

    public Goal[] getGoals() {
        return goals;
    }

    public boolean isFulfilled(){
        boolean isFul = true;
        for(Goal g : goals)
            isFul = g.getState();
        return isFul || fulfilled;
    }

    public Date getTaskBegin() {
        return goals[0].getBeginDate();
    }

    public Date getTaskEnd() {
        return goals[goals.length - 1].getEndDate();
    }

    public void setFulfilled() {
        this.fulfilled = true;
    }
}
