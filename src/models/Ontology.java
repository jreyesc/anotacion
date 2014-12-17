/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import java.io.IOException;

/**
 *
 * @author JUAN
 */
public class Ontology {
    private static String nameSpace = "";
    private static Model model = ModelFactory.createOntologyModel();
    private static OntModel ontModel = null;

    public static String getNameSpace() {
        return nameSpace;
    }

    public static void setNameSpace(String nameSpace) {
        Ontology.nameSpace = nameSpace;
    }

    public static Model getModel() {
        return model;
    }

    public static void setModel(Model model) {
        Ontology.model = model;
    }

    public static OntModel getOntModel() {
        return ontModel;
    }

    public static void setOntModel(OntModel ontModel) {
        Ontology.ontModel = ontModel;
    }
    
    public static Boolean loadOntology(String filename) {
        InputStream in;
        try {
            in = new FileInputStream(filename);
            model.read(in, "");
            in.close();
        } catch (IOException e) {
            System.out.println("Fallo");
            return false;
        }
        nameSpace = model.getNsPrefixURI("futbol");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(model);
        OntModelSpec ontModelSpec = OntModelSpec.OWL_MEM_MICRO_RULE_INF;
        ontModelSpec.setReasoner(reasoner);
        ontModel = ModelFactory.createOntologyModel(ontModelSpec);
        return true;
    }
}
