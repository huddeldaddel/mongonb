package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.core.Database;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

/**
 * Creates a new {@code Collection}.
 *
 * @author thomaswerner35
 */
public class CreateCollectionAction extends AbstractAction {

    private static final ResourceBundle bundle = NbBundle.getBundle(CreateCollectionAction.class);

    private final Database database;

    public CreateCollectionAction(Database database) {
        this.database = database;

        super.putValue(NAME, bundle.getString("CreateCollectionAction.Name"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String collectionName = queryCollectionName("");
        while(null != collectionName) {
            String errMsg = isValidName(collectionName);
            if((null == errMsg) && collectionExists(collectionName))
                errMsg = bundle.getString("CreateCollectionAction.ErrorExists");
            if(null != errMsg) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(errMsg));
                collectionName = queryCollectionName(collectionName);
            } else {
                final String name = collectionName;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        database.createCollection(name);
                    }
                }).start();
                return;
            }
        }
    }

    /**
     * Displays a input dialog for the user to enter the name of the new collection.
     *
     * @return the name of the collection or {@code null}
     */
    private String queryCollectionName(String defaultValue) {
        final String text = bundle.getString("CreateCollectionAction.InputText");
        final String title = bundle.getString("CreateCollectionAction.InputTitle");
        final NotifyDescriptor.InputLine il = new NotifyDescriptor.InputLine(text, title);
        il.setInputText(defaultValue);
        return NotifyDescriptor.OK_OPTION == DialogDisplayer.getDefault().notify(il) ? il.getInputText() : null;
    }

    /**
     * @param collectionName the name of the collection
     * @return {@code true} if a collection having the given name exists
     */
    private boolean collectionExists(String collectionName) {
        for(Collection c: database.getCollections())
            if(c.getName().equals(collectionName))
                return true;
        return false;
    }

    /**
     * Checks the given collection name. Returns {@code null} if the given collectionName is valid or an error message
     * if it's not.
     *
     * @param collectionName the name to be checked
     * @return {@code null} if the given collectionName is valid. Returns an error message if it's not.
     * @see http://docs.mongodb.org/manual/reference/limits/
     */
    private String isValidName(String collectionName) {
        if(collectionName.isEmpty())
            return bundle.getString("CreateCollectionAction.ErrorEmpty");
        if(collectionName.contains(Character.toString((char)0)))
            return bundle.getString("CreateCollectionAction.ErrorNullCharacter");
        if(collectionName.contains("$"))
            return bundle.getString("CreateCollectionAction.ErrorDollar");
        if(collectionName.startsWith("system."))
            return bundle.getString("CreateCollectionAction.ErrorSystem");
        if(!(collectionName.substring(0, 1).equals("_") || Character.isAlphabetic(collectionName.charAt(0))))
            return bundle.getString("CreateCollectionAction.ErrorStart");
        return null;
    }

}
