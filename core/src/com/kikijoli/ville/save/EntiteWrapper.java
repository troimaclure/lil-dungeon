package com.kikijoli.ville.save;

import java.io.Serializable;

/**
 *
 * @author ajosse
 */
public class EntiteWrapper implements Serializable {

    public float x, y;
    public String classDestination, methodDestination, className;

    public EntiteWrapper() {
    }

    public EntiteWrapper(float x, float y, String classDestination, String methodDestination, String className) {
        this.x = x;
        this.y = y;
        this.classDestination = classDestination;
        this.methodDestination = methodDestination;
        this.className = className;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getClassDestination() {
        return classDestination;
    }

    public void setClassDestination(String classDestination) {
        this.classDestination = classDestination;
    }

    public String getMethodDestination() {
        return methodDestination;
    }

    public void setMethodDestination(String methodDestination) {
        this.methodDestination = methodDestination;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
