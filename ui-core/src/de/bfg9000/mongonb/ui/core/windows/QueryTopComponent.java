package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBCursor;
import de.bfg9000.mongonb.core.Collection;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javax.swing.SwingWorker;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import lombok.Setter;
import org.netbeans.api.editor.DialogBinding;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.text.CloneableEditorSupport;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.Mode;
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
    
    @Setter private Collection collection;
    private DataCache dataCache;
    
    public QueryTopComponent() {
        initComponents();                
        
        setName(Bundle.CTL_QueryTopComponent());
        setToolTipText(Bundle.HINT_QueryTopComponent());
        
        tblData.setComponentFactory(new ComponentFactory());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spltSplitter = new javax.swing.JSplitPane();
        pnlResultArea = new javax.swing.JPanel();
        tbDataNavigation = new javax.swing.JToolBar();
        btnReload = new javax.swing.JButton();
        btnGoFirst = new javax.swing.JButton();
        btnGoPrevious = new javax.swing.JButton();
        btnGoNext = new javax.swing.JButton();
        btnGoLast = new javax.swing.JButton();
        lblPageSize = new javax.swing.JLabel();
        txtPageSize = new javax.swing.JTextField();
        lblTotalRowsInfo = new javax.swing.JLabel();
        lblTotalRows = new javax.swing.JLabel();
        scrData = new javax.swing.JScrollPane();
        tblData = new com.jidesoft.grid.HierarchicalTable();
        scrEditor = new javax.swing.JScrollPane();
        epEditor = new javax.swing.JEditorPane();
        tbToolBar = new javax.swing.JToolBar();
        lblConnectionInfo = new javax.swing.JLabel();
        lblConnection = new javax.swing.JLabel();
        btnRunQuery = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        spltSplitter.setDividerLocation(200);
        spltSplitter.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlResultArea.setLayout(new java.awt.BorderLayout());

        tbDataNavigation.setRollover(true);

        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/view-refresh.png"))); // NOI18N
        btnReload.setFocusable(false);
        btnReload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReload.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbDataNavigation.add(btnReload);

        btnGoFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/go-first.png"))); // NOI18N
        btnGoFirst.setFocusable(false);
        btnGoFirst.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGoFirst.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbDataNavigation.add(btnGoFirst);

        btnGoPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/go-previous.png"))); // NOI18N
        btnGoPrevious.setFocusable(false);
        btnGoPrevious.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGoPrevious.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbDataNavigation.add(btnGoPrevious);

        btnGoNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/go-next.png"))); // NOI18N
        btnGoNext.setFocusable(false);
        btnGoNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGoNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbDataNavigation.add(btnGoNext);

        btnGoLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/go-last.png"))); // NOI18N
        btnGoLast.setFocusable(false);
        btnGoLast.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGoLast.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbDataNavigation.add(btnGoLast);

        lblPageSize.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(lblPageSize, org.openide.util.NbBundle.getMessage(QueryTopComponent.class, "QueryTopComponent.lblPageSize.text")); // NOI18N
        lblPageSize.setPreferredSize(new java.awt.Dimension(65, 16));
        tbDataNavigation.add(lblPageSize);

        txtPageSize.setText(org.openide.util.NbBundle.getMessage(QueryTopComponent.class, "QueryTopComponent.txtPageSize.text")); // NOI18N
        txtPageSize.setMaximumSize(new java.awt.Dimension(30, 28));
        txtPageSize.setPreferredSize(new java.awt.Dimension(30, 28));
        tbDataNavigation.add(txtPageSize);

        lblTotalRowsInfo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(lblTotalRowsInfo, org.openide.util.NbBundle.getMessage(QueryTopComponent.class, "QueryTopComponent.lblTotalRowsInfo.text")); // NOI18N
        lblTotalRowsInfo.setPreferredSize(new java.awt.Dimension(80, 16));
        tbDataNavigation.add(lblTotalRowsInfo);
        tbDataNavigation.add(lblTotalRows);

        pnlResultArea.add(tbDataNavigation, java.awt.BorderLayout.NORTH);

        scrData.setViewportView(tblData);

        pnlResultArea.add(scrData, java.awt.BorderLayout.CENTER);

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

        add(tbToolBar, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunQueryActionPerformed
        runQuery();
    }//GEN-LAST:event_btnRunQueryActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGoFirst;
    private javax.swing.JButton btnGoLast;
    private javax.swing.JButton btnGoNext;
    private javax.swing.JButton btnGoPrevious;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnRunQuery;
    private javax.swing.JEditorPane epEditor;
    private javax.swing.JLabel lblConnection;
    private javax.swing.JLabel lblConnectionInfo;
    private javax.swing.JLabel lblPageSize;
    private javax.swing.JLabel lblTotalRows;
    private javax.swing.JLabel lblTotalRowsInfo;
    private javax.swing.JPanel pnlResultArea;
    private javax.swing.JScrollPane scrData;
    private javax.swing.JScrollPane scrEditor;
    private javax.swing.JSplitPane spltSplitter;
    private javax.swing.JToolBar tbDataNavigation;
    private javax.swing.JToolBar tbToolBar;
    private com.jidesoft.grid.HierarchicalTable tblData;
    private javax.swing.JTextField txtPageSize;
    // End of variables declaration//GEN-END:variables

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");        
    }

    void readProperties(java.util.Properties p) {
        // String version = p.getProperty("version");        
    }

    @Override
    protected void componentOpened() {
        initWindowName();
        initEditor();
        initConnectionLabel();
    }

    private void runQuery() {
        new QueryWorker().execute();        
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
    
    private void updateTable() {        
        tblData.setModel(new QueryTableModel(dataCache));
        if(0 < tblData.getColumnCount()) {
            final TableColumn column = tblData.getColumnModel().getColumn(0);            
            column.setMaxWidth(60);
            column.setMinWidth(60);
            column.setPreferredWidth(60);
        }
    }
    
    /**
     * Executes the query asynchronously, then updates the UI.
     */
    private final class QueryWorker extends SwingWorker<DBCursor, Void> {

        private long durationInMillis = 0;
        private String errorMessage = null;
        
        @Override
        protected DBCursor doInBackground() throws Exception {
            final long start = System.currentTimeMillis();
            try {
                return collection.executeQuery(epEditor.getText());                
            } catch(Exception ex) {
                errorMessage = ex.getMessage();
                return null;
            } finally {
                final long end =  System.currentTimeMillis();
                durationInMillis = end -start;
            }            
        }
        
        @Override
        protected void done() {
            IOProvider.getDefault().getIO(getName(), false).closeInputOutput();
            final InputOutput io = IOProvider.getDefault().getIO(getName(), true);            
            try {
                final String duration = NumberFormat.getNumberInstance().format(durationInMillis / 1000.0);
                final DBCursor cursor = get();
                if(null == cursor) {
                    final String template = bundle.getString("QueryTopComponent.queryFailure");
                    io.getOut().println(MessageFormat.format(template, duration));
                    if(null != errorMessage)
                        io.getErr().println("\n" +errorMessage.replaceAll("Source: java.io.StringReader@(.+?); ", ""));
                    dataCache = DataCache.EMPTY;
                } else {                    
                    final String template = bundle.getString("QueryTopComponent.querySuccess");
                    io.getOut().println(MessageFormat.format(template, duration));
                    dataCache = new DataCache(cursor, Integer.parseInt(txtPageSize.getText()));                    
                }
                updateTable();
            } catch(Exception ignored) {                
            } finally {
                io.select();
                io.getErr().close();
                io.getOut().close();
            }
            
        }
        
    }
    
}
