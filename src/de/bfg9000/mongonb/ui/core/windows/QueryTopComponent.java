package de.bfg9000.mongonb.ui.core.windows;

import de.bfg9000.mongonb.core.Collection;
import java.awt.BorderLayout;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.text.Document;
import org.netbeans.api.editor.DialogBinding;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.text.CloneableEditorSupport;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays an editor to enter JSON objects and a result table.
 */
@ConvertAsProperties(dtd = "-//de.bfg9000.mongonb.ui.core.windows//Query//EN", autostore = false)
@TopComponent.Description(
        preferredID = "QueryTopComponent",
      //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@Messages({
    "CTL_QueryTopComponent=Query Window",
    "HINT_QueryTopComponent=This is a Query window"
})
public final class QueryTopComponent extends TopComponent {

    private static final ResourceBundle bundle = NbBundle.getBundle(QueryTopComponent.class);

    private Collection collection;
    private final ResultTable resultTable;

    public QueryTopComponent() {
        initComponents();

        setName(Bundle.CTL_QueryTopComponent());
        setToolTipText(Bundle.HINT_QueryTopComponent());

        pnlResultArea.add(resultTable = new ResultTable(), BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spltSplitter = new javax.swing.JSplitPane();
        pnlResultArea = new javax.swing.JPanel();
        scrEditor = new javax.swing.JScrollPane();
        epEditor = new javax.swing.JEditorPane();
        tbToolBar = new javax.swing.JToolBar();
        lblConnectionInfo = new javax.swing.JLabel();
        lblConnection = new javax.swing.JLabel();
        btnRunQuery = new javax.swing.JButton();
        btnAddDocument = new javax.swing.JButton();
        btnRemoveDocument = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        spltSplitter.setDividerLocation(200);
        spltSplitter.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlResultArea.setLayout(new java.awt.BorderLayout());
        spltSplitter.setRightComponent(pnlResultArea);

        scrEditor.setViewportView(epEditor);

        spltSplitter.setTopComponent(scrEditor);

        add(spltSplitter, java.awt.BorderLayout.CENTER);

        tbToolBar.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(lblConnectionInfo, org.openide.util.NbBundle.getMessage(QueryTopComponent.class, "QueryTopComponent.lblConnectionInfo.text")); // NOI18N
        tbToolBar.add(lblConnectionInfo);

        org.openide.awt.Mnemonics.setLocalizedText(lblConnection, org.openide.util.NbBundle.getMessage(QueryTopComponent.class, "QueryTopComponent.lblConnection.text")); // NOI18N
        tbToolBar.add(lblConnection);

        btnRunQuery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/media-playback-start.png"))); // NOI18N
        btnRunQuery.setToolTipText(org.openide.util.NbBundle.getMessage(QueryTopComponent.class, "QueryTopComponent.btnRunQuery.toolTipText")); // NOI18N
        btnRunQuery.setFocusable(false);
        btnRunQuery.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRunQuery.setMargin(new java.awt.Insets(0, 5, 0, 0));
        btnRunQuery.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRunQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunQueryActionPerformed(evt);
            }
        });
        tbToolBar.add(btnRunQuery);

        btnAddDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/list-add.png"))); // NOI18N
        btnAddDocument.setToolTipText(org.openide.util.NbBundle.getMessage(QueryTopComponent.class, "QueryTopComponent.btnAddDocument.toolTipText")); // NOI18N
        btnAddDocument.setFocusable(false);
        btnAddDocument.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddDocument.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDocumentActionPerformed(evt);
            }
        });
        tbToolBar.add(btnAddDocument);

        btnRemoveDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/list-remove.png"))); // NOI18N
        btnRemoveDocument.setToolTipText(org.openide.util.NbBundle.getMessage(QueryTopComponent.class, "QueryTopComponent.btnRemoveDocument.toolTipText")); // NOI18N
        btnRemoveDocument.setFocusable(false);
        btnRemoveDocument.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemoveDocument.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveDocumentActionPerformed(evt);
            }
        });
        tbToolBar.add(btnRemoveDocument);

        add(tbToolBar, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunQueryActionPerformed
        final QueryWorker w = new QueryWorker(collection, epEditor.getText(), getName(), resultTable.getPageSize());
        w.setResultTable(resultTable);
        w.execute();
    }//GEN-LAST:event_btnRunQueryActionPerformed

    private void btnAddDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDocumentActionPerformed
        final InsertWorker w = new InsertWorker(collection, epEditor.getText(), getName(), resultTable.getPageSize());
        w.setResultTable(resultTable);
        w.execute();
    }//GEN-LAST:event_btnAddDocumentActionPerformed

    private void btnRemoveDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveDocumentActionPerformed
        final RemoveWorker w = new RemoveWorker(collection, epEditor.getText(), getName(), resultTable.getPageSize());
        w.setResultTable(resultTable);
        w.execute();
    }//GEN-LAST:event_btnRemoveDocumentActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDocument;
    private javax.swing.JButton btnRemoveDocument;
    private javax.swing.JButton btnRunQuery;
    private javax.swing.JEditorPane epEditor;
    private javax.swing.JLabel lblConnection;
    private javax.swing.JLabel lblConnectionInfo;
    private javax.swing.JPanel pnlResultArea;
    private javax.swing.JScrollPane scrEditor;
    private javax.swing.JSplitPane spltSplitter;
    private javax.swing.JToolBar tbToolBar;
    // End of variables declaration//GEN-END:variables

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        // String version = p.getProperty("version");
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
        resultTable.setCollection(collection);
    }

    @Override
    protected void componentOpened() {
        initWindowName();
        initEditor();
        initConnectionLabel();
    }

    private void initConnectionLabel() {
        final StringBuilder builder = new StringBuilder();
        builder.append(collection.getConnection().getHost())
               .append(":")
               .append(collection.getConnection().getPort())
               .append("/")
               .append(collection.getDatabase())
               .append("/")
               .append(collection.getName());
        lblConnection.setText(builder.toString());
    }

    private void initWindowName() {
        final String nameTemplate = bundle.getString("QueryTopComponent.name");
        final Mode editorMode = WindowManager.getDefault().findMode("editor");
        final TopComponent[] openedTopComponents = WindowManager.getDefault().getOpenedTopComponents(editorMode);

        String name = "";
        int counter = 0;
        boolean found = true;
        while(found) {
            found = false;
            name = MessageFormat.format(nameTemplate, ++counter);
            for(TopComponent tc: openedTopComponents)
                if(name.equals(tc.getName())) {
                    found = true;
                    break;
                }
        }

        setName(name);
        resultTable.setName(name);
    }

    private void initEditor() {
        epEditor.setEditorKit(CloneableEditorSupport.getEditorKit("text/x-javascript"));
        try {
            final FileObject fob = FileUtil.createMemoryFileSystem().getRoot().createData("tmp", "js");
            epEditor.getDocument().putProperty(Document.StreamDescriptionProperty, DataObject.find(fob));
            DialogBinding.bindComponentToFile(fob, 0, 0, epEditor);
            epEditor.setText("{\n\t\n}");
        } catch(IOException ex) {
        }
    }

}