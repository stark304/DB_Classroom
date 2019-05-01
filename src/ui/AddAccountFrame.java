/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import db.DBManager;
import db.DBManager.Record;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author kuhail
 */
public class AddAccountFrame extends javax.swing.JFrame {

    /**
     * Creates new form AddAccountFrame
     */
    DBManager DB;
    String employee_id;
    AccountsFrame parent;
    public AddAccountFrame(AccountsFrame parent,DBManager DB,String employee_id) {
        this.setTitle("Add Frame");
        this.parent=parent;
        this.DB=DB;
        this.employee_id=employee_id;
        initComponents();
        populate_account_types();
    }
    
    private void populate_account_types(){
        LinkedList<Record> account_types=DB.getAccountTypes();
        this.type_combo.removeAllItems();
        for(Record account_type:account_types){
            this.type_combo.addItem(account_type);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        add_account_button = new javax.swing.JButton();
        customer_id_textbox = new javax.swing.JTextField();
        status_combo = new javax.swing.JComboBox();
        branch_id_textbox = new javax.swing.JTextField();
        type_combo = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Customer ID:");

        jLabel2.setText("Status:");

        jLabel3.setText("Branch ID:");

        jLabel4.setText("Type:");

        add_account_button.setText("Add Account");
        add_account_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_account_buttonActionPerformed(evt);
            }
        });

        status_combo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ACTIVE", "CLOSED", "FROZEN" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(customer_id_textbox)
                            .addComponent(status_combo, 0, 124, Short.MAX_VALUE)
                            .addComponent(branch_id_textbox)
                            .addComponent(type_combo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(add_account_button)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(customer_id_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(status_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(branch_id_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(type_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(add_account_button)
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void add_account_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_account_buttonActionPerformed
        // TODO add your handling code here:
        String customer_id=this.customer_id_textbox.getText();
        String status=this.status_combo.getSelectedItem().toString();
        String branch_id=this.branch_id_textbox.getText();
        String type_id=((Record)this.type_combo.getSelectedItem()).ID;
        if(customer_id.trim().equals("")){
             JOptionPane.showMessageDialog(this,
    "Customer ID is empty",
    "Error",
    JOptionPane.ERROR_MESSAGE);  
             return;
        }
        boolean result=DB.addAccount(customer_id, status, branch_id, employee_id, type_id);
                if(result){
             JOptionPane.showMessageDialog(this,
    "A new account was added correctly",
    "Confirmation",
    JOptionPane.INFORMATION_MESSAGE);
             parent.populate_accounts_table();
        }
    }//GEN-LAST:event_add_account_buttonActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_account_button;
    private javax.swing.JTextField branch_id_textbox;
    private javax.swing.JTextField customer_id_textbox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox status_combo;
    private javax.swing.JComboBox type_combo;
    // End of variables declaration//GEN-END:variables
}