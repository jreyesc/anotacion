/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.swing.JTextField;

/**
 *
 * @author JUAN
 */
public class Field {
    private String name;
    private JTextField field;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JTextField getField() {
        return field;
    }

    public void setField(JTextField field) {
        this.field = field;
    }
}
