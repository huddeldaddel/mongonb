package de.bfg9000.mongonb.ui.core.nodes;

import de.bfg9000.mongonb.core.Connection;
import de.bfg9000.mongonb.ui.core.actions.ConnectAction;
import de.bfg9000.mongonb.ui.core.actions.CreateDatabaseAction;
import de.bfg9000.mongonb.ui.core.actions.DeleteConnectionAction;
import de.bfg9000.mongonb.ui.core.actions.DisconnectAction;
import de.bfg9000.mongonb.ui.core.actions.EditConnectionAction;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle;

/**
 * Displays a {@code Connection} in the Services window.
 *
 * @author thomaswerner35
 */
class ConnectionNode extends AbstractNode implements PropertyChangeListener {

    public static final String ICON_CONNECTED = "de/bfg9000/mongonb/ui/core/images/connected.png";
    public static final String ICON_DISCONNECTED = "de/bfg9000/mongonb/ui/core/images/disconnected.png";

    private static final ResourceBundle bundle = NbBundle.getBundle(ConnectionNode.class);

    private final Connection connection;
    private final ConnectAction connectAction;
    private final DisconnectAction disconnectAction;

    public ConnectionNode(Connection connection) {
        super(Children.create(new DatabaseNodeFactory(connection), true));
        this.connection = connection;
        this.connection.addPropertyChangeListener(this);

        connectAction = new ConnectAction(connection);
        connectAction.setEnabled(true);
        disconnectAction = new DisconnectAction(connection);
        disconnectAction.setEnabled(false);

        setName(connection.getName());
        setIconBaseWithExtension(connection.isConnected() ? ICON_CONNECTED : ICON_DISCONNECTED);
    }

    @Override
    public Action getPreferredAction() {
        return connectAction.isEnabled() ? connectAction : null;
    }

    @Override
    public Action[] getActions(boolean context) {
        final List<Action> actions = new LinkedList<Action>();
        actions.add(connectAction);
        actions.add(disconnectAction);
        actions.add(new DeleteConnectionAction(connection));
        actions.add(new EditConnectionAction(connection));
        if(connection.isConnected())
            actions.add(new CreateDatabaseAction(connection));
        actions.addAll(Arrays.asList(super.getActions(context)));
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(Connection.PROPERTY_CONNECTED)) {
            final boolean connected = Boolean.TRUE.equals(evt.getNewValue());
            setIconBaseWithExtension(connected ? ICON_CONNECTED : ICON_DISCONNECTED);
            fireIconChange();

            connectAction.setEnabled(!connected);
            disconnectAction.setEnabled(connected);
        }
    }

    @Override
    protected Sheet createSheet() {
        final Sheet result = new Sheet();
        final Sheet.Set defaultProperties = Sheet.createPropertiesSet();
        defaultProperties.put(new LocalizedProperty("name", connection.getName()));
        defaultProperties.put(new LocalizedProperty("host", connection.getHost()));
        defaultProperties.put(new LocalizedProperty("port", connection.getPort()));
        defaultProperties.put(new LocalizedProperty("authRequired", connection.isAuthRequired() ?
                              bundle.getString("ConnectionNode.property.value.yes") :
                              bundle.getString("ConnectionNode.property.value.no")));
        defaultProperties.put(new LocalizedProperty("authUsername", connection.getAuthUsername()));
        defaultProperties.put(new LocalizedProperty("authDatabase", connection.getAuthDatabase()));
        result.put(defaultProperties);
        return result;
    }

    /**
     * Read-Only property that uses localized strings from the bundle file.
     */
    private final static class LocalizedProperty extends PropertySupport.ReadOnly<String> {

        private final Object data;

        public LocalizedProperty(String propertyName, Object data) {
            super(bundle.getString("ConnectionNode.property." +propertyName +".name"), String.class,
                  bundle.getString("ConnectionNode.property." +propertyName +".displayname"),
                  bundle.getString("ConnectionNode.property." +propertyName +".shortdesc"));
            this.data = data;
        }

        @Override
        public String getValue() throws IllegalAccessException, InvocationTargetException {
            return null == data ? "" : data.toString();
        }
    }

}
