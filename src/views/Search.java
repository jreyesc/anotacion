/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL2;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import models.Field;
import models.Ontology;

/**
 *
 * @author JUAN
 */
public class Search extends javax.swing.JDialog {

    private SaveImage parent;
    private JTextField txtField;
    private OntClass ontClass;
    private AbstractTableModel model;
    private ArrayList<Individual> individuals;
    private ArrayList<Individual> all_individuals;
    private Field temp;

    public JTextField getTxtField() {
        return txtField;
    }

    public void setTxtField(JTextField txtField) {
        this.txtField = txtField;
    }
    
    /**
     * Creates new form Search
     * @param parent
     * @param modal
     * @param ontClass
     * @param txtField
     */
    public Search(final java.awt.Frame parent, boolean modal, final OntClass ontClass, final JTextField txtField) {
        super(parent, modal);
        initComponents();
        this.parent = (SaveImage) parent;
        this.ontClass = ontClass;
        individuals = new ArrayList<>();
        all_individuals = new ArrayList<>();
        for (NodeIterator i = ontClass.listPropertyValues(Ontology.getOntModel().getProperty(Ontology.getNameSpace() + "lbl_netbeans")); i.hasNext();){
            title_class.setText(i.next().toString());
        }
        this.txtField = txtField;
        model = new InstancesTable();
        tbl_individuals.setModel(model);
        for (ExtendedIterator<? extends OntResource> i = ontClass.listInstances();i.hasNext();){
            Individual inst = (Individual) i.next();
            if (inst.getNameSpace() != null){
                individuals.add(inst);
                all_individuals.add(inst);
            }
        }
        
        tbl_individuals.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    temp = ((SaveImage) parent).getContentFields().get(ontClass.toString());
                    temp.setIndividual(individuals.get(row));
                    temp.getField().setText(individuals.get(row).getLocalName());
                }
            }
});
    }
    
    public Search(final java.awt.Frame parent, boolean modal, final OntClass ontClass, final OntProperty ontTarget, final JTextField txtField) {
        super(parent, modal);
        initComponents();
        this.parent = (SaveImage) parent;
        this.ontClass = ontClass;
        individuals = new ArrayList<>();
        all_individuals = new ArrayList<>();
        for (NodeIterator i = ontClass.listPropertyValues(Ontology.getOntModel().getProperty(Ontology.getNameSpace() + "lbl_netbeans")); i.hasNext();){
            title_class.setText(i.next().toString());
        }
        this.txtField = txtField;
        model = new InstancesTable();
        tbl_individuals.setModel(model);
        for (ExtendedIterator<? extends OntResource> i = ontClass.listInstances();i.hasNext();){
            Individual inst = (Individual) i.next();
            if (inst.getNameSpace() != null){
                individuals.add(inst);
                all_individuals.add(inst);
            }
        }
        
        tbl_individuals.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    temp = ((SaveImage) parent).getContentFields().get(ontTarget.toString());
                    temp.setIndividual(individuals.get(row));
                    temp.getField().setText(individuals.get(row).getLocalName());
                }
            }
});
    }
    
    public class InstancesTable extends AbstractTableModel{
        @Override
        public int getRowCount() {
            return individuals.size();
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public Object getValueAt(int row, int column) {
            String individual = individuals.get(row).getLocalName();
            return individual;
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

        txt_search = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_individuals = new javax.swing.JTable();
        title_class = new javax.swing.JLabel();
        btn_create = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });

        btn_search.setText("Buscar");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        tbl_individuals.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Individuo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl_individuals);

        btn_create.setText("Crear");
        btn_create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_createActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(0, 8, Short.MAX_VALUE)
                                .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_create, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(title_class)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title_class)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_search))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_create))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        if (txt_search.equals("")){
            individuals = all_individuals;
        }else{
            individuals = new ArrayList<>();
            for (int i = 0; i < all_individuals.size(); i++){
                if (all_individuals.get(i).getLocalName().contains(txt_search.getText())){
                    individuals.add(all_individuals.get(i));
                }
            }
        }
        model.fireTableDataChanged();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_createActionPerformed
        System.out.println(ontClass);
        Individual temp = Ontology.getOntModel().createIndividual(Ontology.getNameSpace() + txt_search.getText(), ontClass);
        all_individuals.add(temp);
        individuals.add(temp);
        model.fireTableDataChanged();
    }//GEN-LAST:event_btn_createActionPerformed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        if (txt_search.equals("")){
            individuals = all_individuals;
        }else{
            individuals = new ArrayList<>();
            for (int i = 0; i < all_individuals.size(); i++){
                if (all_individuals.get(i).getLocalName().contains(txt_search.getText())){
                    individuals.add(all_individuals.get(i));
                }
            }
        }
        model.fireTableDataChanged();
    }//GEN-LAST:event_txt_searchKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_create;
    private javax.swing.JButton btn_search;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_individuals;
    private javax.swing.JLabel title_class;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables
}
