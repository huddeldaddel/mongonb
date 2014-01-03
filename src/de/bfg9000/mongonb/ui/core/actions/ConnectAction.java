package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.core.Connection;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

/**
 * Connects to a database.
 * 
 * @author thomaswerner35
 */
public class ConnectAction extends AbstractAction {
    
    private static final ResourceBundle bundle = NbBundle.getBundle(ConnectAction.class);
    
    private final Connection connection;
    
    public ConnectAction(Connection connection) {
        this.connection = connection;        
            
        super.putValue(NAME, bundle.getString("ConnectAction.Name"));
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(new ConnectionRunner()).start();        
    }
    
    private final class ConnectionRunner implements Runnable {
        
        @Override
        public void run() {
            if(!connection.connect()) 
                SwingUtilities.invokeLater(new DialogRunner());
        }
        
    }
    
    private final class DialogRunner implements Runnable {

        @Override
        public void run() {
            final String template = bundle.getString("ConnectAction.Failure");
            final String text = MessageFormat.format(template, connection.getHost(), 
                                Integer.toString(connection.getPort()));            
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(text, NotifyDescriptor.WARNING_MESSAGE));
        }
        
    }
    
}
