package de.bfg9000.mongonb.ui.core.nodes;

import de.bfg9000.mongonb.ui.core.actions.CreateConnectionAction;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.Action;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle;

/**
 * This Node is the base node of the plugin. It is registered as a root entry of the Services tab.
 *
 * @author thomaswerner35
 */
@ServicesTabNodeRegistration(
        displayName=MongoServiceNode.NAME,
        iconResource=MongoServiceNode.ICON_BASE,
        name=MongoServiceNode.NAME,
        position = 5000
)
public class MongoServiceNode extends AbstractNode {

    public static final String ICON_BASE = "de/bfg9000/mongonb/ui/core/images/MongoDB-logo-16.png";
    public static final String NAME = "MongoDB";

    private static final ResourceBundle bundle = NbBundle.getBundle(MongoServiceNode.class);

    public MongoServiceNode() {
        super(Children.create(new ConnectionNodeFactory(), true));

        setName(bundle.getString("MongoServiceNode.Name"));
        setIconBaseWithExtension(ICON_BASE);
    }

    @Override
    public Action[] getActions(boolean context) {
        final List<Action> actions = new LinkedList<Action>();
        actions.add(new CreateConnectionAction());
        actions.addAll(Arrays.asList(super.getActions(context)));
        return actions.toArray(new Action[actions.size()]);
    }

}
