/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.awt.Point;

/**
 *
 * @author Santiago Avendaño
 */
public class TerminalRemotaData {

    private Integer trID;
    private Point position;

    public TerminalRemotaData(Integer trID, Point position) {
        this.trID = trID;
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Integer getTrID() {
        return trID;
    }

    public void setTrID(Integer trID) {
        this.trID = trID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TerminalRemotaData other = (TerminalRemotaData) obj;
        if (this.trID != other.trID && (this.trID == null || !this.trID.equals(other.trID))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.position != null ? this.position.hashCode() : 0);
        return hash;
    }
}
