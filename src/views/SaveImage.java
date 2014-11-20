package views;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import config.Constants;
import java.awt.image.BufferedImage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import models.Ontology;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.PropertyNotFoundException;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL2;
import com.hp.hpl.jena.vocabulary.RDF;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JUAN
 */
public class SaveImage extends javax.swing.JFrame {

    private JLabel lblTemp;
    private JTextField txtTemp;
    /**
     * Creates new form SaveImage
     */
    public SaveImage() {
        initComponents();
        if (Ontology.loadOntology("ontologies/futbol.owl")){
            drawCharImage();
            
//            OntClass oClass = Ontology.getOntModel().getOntClass(Ontology.getNameSpace() + "Player");
            Individual ind = Ontology.getOntModel().getIndividual(Ontology.getNameSpace() + "Ruiz");
            System.out.println(ind);
            Set<Property> owlAnnotationProperties = new HashSet<Property>() {{
                add( RDF.type );
                add( OWL2.annotatedProperty );
                add( OWL2.annotatedSource );
                add( OWL2.annotatedTarget );
            }};
            ResIterator axioms = Ontology.getOntModel().listSubjectsWithProperty( RDF.type, OWL2.Axiom );
            while ( axioms.hasNext() ) {
                Resource axiom = axioms.next();
                RDFNode indAxiom = axiom.getProperty(OWL2.annotatedSource).getObject();

                System.out.println(axiom.getProperty(OWL2.annotatedTarget).getObject());
                if (ind.equals(ind)){
                    System.out.println("done");
                }
                StmtIterator stmts = axiom.listProperties();
                while ( stmts.hasNext() ) {
                    Statement stmt = stmts.next();
                    if ( !owlAnnotationProperties.contains( stmt.getPredicate() )) {
                        System.out.println( stmt );
                        System.out.println(stmt.getObject());
                    }
                }
            }
            
            
            for (StmtIterator i = ind.listProperties(Ontology.getOntModel().getProperty(Ontology.getNameSpace() + "playerOf")); i.hasNext();){
//                Property p = (Property) ;
                Statement s = i.next();
                System.out.println(s);
                for (RSIterator j = s.listReifiedStatements(); j.hasNext();){
                    ReifiedStatement rs = j.next();
                    System.out.println(rs);
                }
//                    Statement p = s.getProperty(Ontology.getOntModel().getProperty(Ontology.getNameSpace() + "begin"));
//                    System.out.println(p);
                Resource r = s.getObject().asResource();
                System.out.println(r);
//                for (StmtIterator j = p.listProperties(); j.hasNext();){
//                    System.out.println(j.next());
//                }
            } 
            
        }else{
            this.dispose();
        }
    }
    
    private void drawCharImage(){
        GroupLayout layout = new GroupLayout(charPanel);
        charPanel.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        Group groupLabels = layout.createParallelGroup();
        Group groupFields = layout.createParallelGroup();
        Group groupRows = layout.createSequentialGroup();
        
        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(groupLabels).addGroup(groupFields));
        layout.setVerticalGroup(groupRows);
        
        OntClass oClass = Ontology.getOntModel().getOntClass(Ontology.getNameSpace() + "Image_Element");
        for (Iterator<OntProperty> i = oClass.listDeclaredProperties(true); i.hasNext();){
            for (NodeIterator j = i.next().listPropertyValues(Ontology.getOntModel().getProperty(Ontology.getNameSpace() + "lbl_netbeans")); j.hasNext();){
                lblTemp = new JLabel(j.next().toString());
                txtTemp = new JTextField();
                
                groupLabels.addComponent(lblTemp);
                groupFields.addComponent(txtTemp);
                groupRows.addGroup(layout.createParallelGroup().addComponent(lblTemp).addComponent(txtTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        charPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Import...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        charPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Características"));

        javax.swing.GroupLayout charPanelLayout = new javax.swing.GroupLayout(charPanel);
        charPanel.setLayout(charPanelLayout);
        charPanelLayout.setHorizontalGroup(
            charPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
        );
        charPanelLayout.setVerticalGroup(
            charPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 103, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .addComponent(charPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(charPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("jpg", "jpg");
        fc.setFileFilter(filtro);

        int respuesta = fc.showOpenDialog(this);

        if (respuesta == JFileChooser.APPROVE_OPTION) {
            File destinoTPK;
            try {
                File archivoElegido = fc.getSelectedFile();
                File dest = new File(Constants.PATH_PHOTOS);
                if (!dest.exists()){
                    dest.mkdir();
                }
                destinoTPK = new File(dest, archivoElegido.getName());
                
                mueveArchivos(archivoElegido, destinoTPK);
                
                BufferedImage img = ImageIO.read(destinoTPK);
                
                ImageIcon icon = new ImageIcon(img);
                jLabel1.setIcon(icon);
            } catch (IOException ex) {
                Logger.getLogger(SaveImage.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un archivo");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private static void mueveArchivos(File origen, File destino) throws FileNotFoundException, IOException {
        OutputStream out;
        try (InputStream in = new FileInputStream(origen)) {
            out = new FileOutputStream(destino);
            byte[] buffer = new byte[1024];
            int tamanho;
            while ((tamanho = in.read(buffer)) > 0) {
                out.write(buffer, 0, tamanho);
            }
        }
        out.close();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SaveImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SaveImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SaveImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SaveImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SaveImage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel charPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
