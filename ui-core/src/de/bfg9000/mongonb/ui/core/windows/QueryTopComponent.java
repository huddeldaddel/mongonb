package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBCursor;
import de.bfg9000.mongonb.core.Collection;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javax.swing.SwingWorker;
import javax.swing.text.Document;
import lombok.Getter;
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
    
    @Getter @Setter private Collection collection;
    
    public QueryTopComponent() {
        initComponents();
        initEditor();        
        
        setName(Bundle.CTL_QueryTopComponent());
        setToolTipText(Bundle.HINT_QueryTopComponent());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spltSplitter = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        epEditor = new javax.swing.JEditorPane();
        tbToolBar = new javax.swing.JToolBar();
        lblConnectionInfo = new javax.swing.JLabel();
        lblConnection = new javax.swing.JLabel();
        btnRunQuery = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        spltSplitter.setDividerLocation(200);
        spltSplitter.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/view-refresh.png"))); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/go-first.png"))); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/go-previous.png"))); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/go-next.png"))); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/go-last.png"))); // NOI18N
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton5);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(QueryTopComponent.class, "QueryTopComponent.jLabel1.text")); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(65, 16));
        jToolBar1.add(jLabel1);

        jTextField1.setText(org.openide.util.NbBundle.getMessage(QueryTopComponent.class, "QueryTopComponent.jTextField1.text")); // NOI18N
        jToolBar1.add(jTextField1);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(QueryTopComponent.class, "QueryTopComponent.jLabel2.text")); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(80, 16));
        jToolBar1.add(jLabel2);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.NORTH);

        spltSplitter.setRightComponent(jPanel1);

        jScrollPane1.setViewportView(epEditor);

        spltSplitter.setTopComponent(jScrollPane1);

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
    private javax.swing.JButton btnRunQuery;
    private javax.swing.JEditorPane epEditor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblConnection;
    private javax.swing.JLabel lblConnectionInfo;
    private javax.swing.JSplitPane spltSplitter;
    private javax.swing.JToolBar tbToolBar;
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
                final DBCursor cursor = collection.executeQuery(epEditor.getText());
                return cursor.skip(0).limit(100);
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
                } else {                    
                    final String template = bundle.getString("QueryTopComponent.querySuccess");
                    io.getOut().println(MessageFormat.format(template, duration));
                    
                    while(cursor.hasNext())
                        System.out.println("> " +cursor.next().toString());
                }
                
            } catch(Exception ignored) {                
            } finally {
                io.select();
                io.getErr().close();
                io.getOut().close();
            }
            
        }
        
    }
    
}
