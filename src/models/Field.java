/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.hp.hpl.jena.ontology.Individual;
import javax.swing.JTextField;

/**
 *
 * @author JUAN
 */
public class Field {
    private Individual individual;
    private JTextField field;

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual name) {
        this.individual = name;
    }

    public JTextField getField() {
        return field;
    }

    public void setField(JTextField field) {
        this.field = field;
    }
}
