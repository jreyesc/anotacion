package views;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import models.Field;

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
    private JButton btnTemp;
    private Map<String, Field> charFields = new HashMap<>();
    private Map<String, Field> contentFields = new HashMap<>();
    private Map<String, Field> contentDataFields = new HashMap<>();
    private Field temp;
    private Date date;

    public Map<String, Field> getCharFields() {
        return charFields;
    }

    public void setCharFields(Map<String, Field> charFields) {
        this.charFields = charFields;
    }

    public Map<String, Field> getContentFields() {
        return contentFields;
    }

    public void setContentFields(Map<String, Field> contentFields) {
        this.contentFields = contentFields;
    }

    public Map<String, Field> getContentDataFields() {
        return contentDataFields;
    }

    public void setContentDataFields(Map<String, Field> contentDataFields) {
        this.contentDataFields = contentDataFields;
    }

    /**
     * Creates new form SaveImage
     */
    public SaveImage() {
        initComponents();
        if (Ontology.loadOntology("ontologies/futbol.owl")) {
            drawCharImage();
            drawContentImage();
        } else {
            this.dispose();
        }
    }

    private void drawCharImage() {
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
        for (Iterator<OntProperty> i = oClass.listDeclaredProperties(true); i.hasNext();) {
            OntProperty prop = i.next();
            temp = new Field();
            for (NodeIterator j = prop.listPropertyValues(Ontology.getOntModel().getProperty(Ontology.getNameSpace() + "lbl_netbeans")); j.hasNext();) {
                lblTemp = new JLabel(j.next().toString());
                txtTemp = new JTextField();
                temp.setField(txtTemp);
                charFields.put(prop.toString(), temp);

                groupLabels.addComponent(lblTemp);
                groupFields.addComponent(txtTemp);
                groupRows.addGroup(layout.createParallelGroup().addComponent(lblTemp).addComponent(txtTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
            }
        }
    }

    private void drawContentImage() {
        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        Group groupLabels = layout.createParallelGroup();
        Group groupFields = layout.createParallelGroup();
        Group groupButtons = layout.createParallelGroup();
        Group groupRows = layout.createSequentialGroup();

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addGroup(groupLabels)
                .addGroup(groupFields)
                .addGroup(groupButtons)
        );
        layout.setVerticalGroup(groupRows);

        OntClass oClass = Ontology.getOntModel().getOntClass(Ontology.getNameSpace() + "Media_Card");
        for (ExtendedIterator i = oClass.listDeclaredProperties(true); i.hasNext();) {
            for (ExtendedIterator j = ((OntProperty) i.next()).listRange(); j.hasNext();) {
                OntClass res = (OntClass) j.next();
                if (res.getNameSpace().equals(oClass.getNameSpace())) {
                    for (ExtendedIterator k = res.listSubClasses(); k.hasNext();) {
                        final OntClass subClass = (OntClass) k.next();
                        if (subClass.getNameSpace() != null) {
                            temp = new Field();
                            for (NodeIterator l = subClass.listPropertyValues(Ontology.getOntModel().getProperty(Ontology.getNameSpace() + "lbl_netbeans")); l.hasNext();) {
                                lblTemp = new JLabel(l.next().toString());
                                txtTemp = new JTextField();
                                txtTemp.setEditable(false);
                                final JTextField txt = txtTemp;
                                txtTemp.getDocument().addDocumentListener(new DocumentListener() {

                                    @Override
                                    public void insertUpdate(DocumentEvent de) {
                                        change();
                                    }

                                    @Override
                                    public void removeUpdate(DocumentEvent de) {
                                    }

                                    @Override
                                    public void changedUpdate(DocumentEvent de) {
                                    }

                                    public void change() {
                                        // made inference
                                        madeInference(subClass);
                                    }
                                });
                                btnTemp = new JButton("Buscar");
                                btnTemp.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent ae) {
                                        new Search(SaveImage.this, true, subClass, txt).setVisible(true);
                                    }
                                });
                                temp.setField(txtTemp);
                                contentFields.put(subClass.toString(), temp);

                                groupLabels.addComponent(lblTemp);
                                groupFields.addComponent(txtTemp);
                                groupButtons.addComponent(btnTemp);
                                groupRows.addGroup(
                                        layout.createParallelGroup()
                                        .addComponent(lblTemp)
                                        .addComponent(txtTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
                            }

                            for (Iterator<OntProperty> l = subClass.listDeclaredProperties(true); l.hasNext();) {
                                final OntProperty prop = l.next();
                                temp = new Field();
                                for (NodeIterator m = prop.listPropertyValues(Ontology.getOntModel().getProperty(Ontology.getNameSpace() + "lbl_netbeans")); m.hasNext();) {
                                    boolean isData = true;
                                    btnTemp = null;
                                    lblTemp = new JLabel(m.next().toString());
                                    txtTemp = new JTextField();
                                    txtTemp.setEditable(true);
                                    final JTextField txt = txtTemp;
                                    for (ExtendedIterator n = prop.listRange(); n.hasNext();) {
                                        final OntClass range = (OntClass) n.next();
                                        if (range.getNameSpace() != null && range.getNameSpace().equals(subClass.getNameSpace())) {
                                            System.out.println(range);
                                            btnTemp = new JButton("Buscar");
                                            btnTemp.addActionListener(new ActionListener() {

                                                @Override
                                                public void actionPerformed(ActionEvent ae) {
                                                    new Search(SaveImage.this, true, range, prop, txt).setVisible(true);
                                                }
                                            });
                                            isData = false;
                                            txtTemp.setEditable(false);
                                            break;
                                        }
                                    }
                                    temp.setField(txtTemp);
                                    if (isData) {
                                        contentDataFields.put(prop.toString(), temp);
                                    } else {
                                        contentFields.put(prop.toString(), temp);
                                    }

                                    groupLabels.addComponent(lblTemp);
                                    groupFields.addComponent(txtTemp);
                                    Group g = layout.createParallelGroup().addComponent(lblTemp).addComponent(txtTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
                                    if (btnTemp != null) {
                                        groupButtons.addComponent(btnTemp);
                                        g.addComponent(btnTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
                                    }

                                    groupRows.addGroup(g);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void madeInference(OntClass ontClass) {
        Field temp = contentFields.get(ontClass.toString());
        Field t;
        Individual ind;
        for (StmtIterator i = temp.getIndividual().listProperties(); i.hasNext();) {
            Statement st = i.next();
            if (st.getPredicate().getNameSpace().equals(Ontology.getNameSpace())) {
                ind = st.getObject().as(Individual.class);
                for (ExtendedIterator ite = ind.listOntClasses(true); ite.hasNext();) {
                    OntClass range = (OntClass) ite.next();
                    if (range.getNameSpace() != null && range.getNameSpace().equals(Ontology.getNameSpace())) {
                        t = contentFields.get(range.toString());
                        t.setIndividual(ind);
                        t.getField().setText(ind.getLocalName());
                        break;
                    }
                }
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

        btn_import = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        charPanel = new javax.swing.JPanel();
        contentPanel = new javax.swing.JPanel();
        btn_save = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btn_import.setText("Import...");
        btn_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_importActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        charPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Características"));

        javax.swing.GroupLayout charPanelLayout = new javax.swing.GroupLayout(charPanel);
        charPanel.setLayout(charPanelLayout);
        charPanelLayout.setHorizontalGroup(
            charPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        charPanelLayout.setVerticalGroup(
            charPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 103, Short.MAX_VALUE)
        );

        contentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Contenido"));
        contentPanel.setName(""); // NOI18N

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btn_save.setText("Guardar");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_import)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(charPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_save)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_save))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_import)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(charPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_importActionPerformed
        JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("jpg", "jpg");
        fc.setFileFilter(filtro);

        int respuesta = fc.showOpenDialog(this);

        if (respuesta == JFileChooser.APPROVE_OPTION) {
            File destinoTPK;
            try {
                File archivoElegido = fc.getSelectedFile();
                File dest = new File(Constants.PATH_PHOTOS);
                if (!dest.exists()) {
                    dest.mkdir();
                }

                date = new Date();
                destinoTPK = new File(dest, String.valueOf(date.getTime()) + ".jpg");

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
    }//GEN-LAST:event_btn_importActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        //crear individuo Image_Element
        OntModel modelTemp = (OntModel) Ontology.getModel();

        OntClass imageClass = modelTemp.getOntClass(Ontology.getNameSpace() + "Image_Element");
        Individual image = modelTemp.createIndividual(Ontology.getNameSpace() + String.valueOf(date.getTime()), imageClass);
        OutputStream file;

        Iterator it = charFields.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (!charFields.get(key).getField().getText().equals("")) {
                DatatypeProperty data = modelTemp.getDatatypeProperty(key);
                image.setPropertyValue(data, modelTemp.createTypedLiteral(charFields.get(key).getField().getText()));
            }
            System.out.println("Clave: " + key + " -> Valor: " + charFields.get(key).getField().getText());
        }

        OntClass cardClass = modelTemp.getOntClass(Ontology.getNameSpace() + "Media_Card");
        Individual media_card = modelTemp.createIndividual(Ontology.getNameSpace() + "card_" + String.valueOf(date.getTime()), cardClass);
        ObjectProperty object = modelTemp.getObjectProperty(Ontology.getNameSpace() + "mediaCard");
        image.setPropertyValue(object, media_card);
        object = modelTemp.getObjectProperty(Ontology.getNameSpace() + "instances");
            System.out.println(object);
        ArrayList IndList = new ArrayList();

        Iterator it2 = contentFields.keySet().iterator();
        while (it2.hasNext()) {
            String key = (String) it2.next();
            System.out.println("Key");
            System.out.println(key);
            if (contentFields.get(key).getIndividual() != null) {
                Individual contentInd = contentFields.get(key).getIndividual();
                contentInd = modelTemp.createIndividual(contentInd.getURI(), contentInd.getOntClass());
                for (ExtendedIterator ite = contentInd.listOntClasses(true); ite.hasNext();) {
                    OntClass range = (OntClass) ite.next();
                    System.out.println("Range...");
                    System.out.println(range);
                    if (range.getNameSpace() != null && range.getNameSpace().equals(Ontology.getNameSpace())) {
                        for (Iterator<OntProperty> i = range.listDeclaredProperties(true); i.hasNext();) {
                            OntProperty prop = i.next();
                            System.out.println(prop);
                            for (ExtendedIterator n = prop.listRange(); n.hasNext();) {
                                OntClass rangeClass = (OntClass) n.next();
                                if (rangeClass.getNameSpace() != null && rangeClass.getNameSpace().equals(range.getNameSpace())) {
                                    System.out.println(rangeClass);
                                    Individual contentIndRange = contentFields.get(rangeClass.toString()).getIndividual();
                                    System.out.println("Indd");
                                    System.out.println(contentIndRange);
                                    contentInd.setPropertyValue(prop, contentIndRange);
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
                System.out.println("Clave: " + key + " -> Valor: " + contentFields.get(key).getIndividual().getLocalName());
                IndList.add(contentInd);
            }
        }
        
        for (Iterator iterator = IndList.iterator(); iterator.hasNext();) {
            Individual next = (Individual) iterator.next();
            media_card.addProperty(object, next);
        }

        try {
            file = new FileOutputStream("ontologies/futbol.owl");
            Ontology.getModel().write(file, "RDF/XML");
            file.close();
            this.dispose();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveImage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveImage.class.getName()).log(Level.SEVERE, null, ex);
        }

        Iterator it3 = contentDataFields.keySet().iterator();
        while (it3.hasNext()) {
            String key = (String) it3.next();
            System.out.println("Clave: " + key + " -> Valor: " + contentDataFields.get(key).getField().getText());
            if (contentDataFields.get(key).getIndividual() != null) {
                System.out.println("Clave: " + key + " -> Valor: " + contentDataFields.get(key).getIndividual().getLocalName());
            }
        }
    }//GEN-LAST:event_btn_saveActionPerformed

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
    private javax.swing.JButton btn_import;
    private javax.swing.JButton btn_save;
    private javax.swing.JPanel charPanel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
