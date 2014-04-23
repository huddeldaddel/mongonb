package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.core.Connection;
import de.bfg9000.mongonb.core.Database;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

/**
 * Creates a new {@code Database}.
 *
 * @author thomaswerner35
 */
public class CreateDatabaseAction extends AbstractAction {

    private static final ResourceBundle bundle = NbBundle.getBundle(CreateDatabaseAction.class);

    private final Connection connection;

    public CreateDatabaseAction(Connection connection) {
        this.connection = connection;

        super.putValue(NAME, bundle.getString("CreateDatabaseAction.Name"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String databaseName = queryDatabaseName("");
        while(null != databaseName) {
            String errMsg = isValidName(databaseName);
            if((null == errMsg) && databaseExists(databaseName))
                errMsg = bundle.getString("CreateDatabaseAction.ErrorExists");
            if(null != errMsg) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(errMsg));
                databaseName = queryDatabaseName(databaseName);
            } else {
                final String name = databaseName;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        connection.createDatabase(name);
                    }
                }).start();
                return;
            }
        }
    }

    /**
     * Displays a input dialog for the user to enter the name of the new database.
     *
     * @return the name of the database or {@code null}
     */
    private String queryDatabaseName(String defaultValue) {
        final String text = bundle.getString("CreateDatabaseAction.InputText");
        final String title = bundle.getString("CreateDatabaseAction.InputTitle");
        final NotifyDescriptor.InputLine il = new NotifyDescriptor.InputLine(text, title);
        il.setInputText(defaultValue);
        return NotifyDescriptor.OK_OPTION == DialogDisplayer.getDefault().notify(il) ? il.getInputText() : null;
    }

    /**
     * @param databaseName the name of the database
     * @return {@code true} if a database having the given name exists
     */
    private boolean databaseExists(String databaseName) {
        for(Database db: connection.getDatabases())
            if(db.getName().equalsIgnoreCase(databaseName))
                return true;
        return false;
    }

    /**
     * Checks the given database name. Returns {@code null} if the given databaseName is valid or an error message
     * if it's not.
     *
     * @param databaseName the name to be checked
     * @return {@code null} if the given databaseName is valid. Returns an error message if it's not.
     * @see http://docs.mongodb.org/manual/reference/limits/
     */
    private String isValidName(String databaseName) {
        if(databaseName.isEmpty())
            return bundle.getString("CreateDatabaseAction.ErrorEmpty");
        if(databaseName.contains(Character.toString((char)0)))
            return bundle.getString("CreateDatabaseAction.ErrorNullCharacter");
        if(databaseName.length() >= 64)
            return bundle.getString("CreateDatabaseAction.TooManyCharacters");

        final char[] forbiddenCharacters = {'/', '\\', '.', ' ', '"', '*', '<', '>', ':', '|', '?'};
        for(char forbiddenCharacter: forbiddenCharacters) {
            if(databaseName.contains(Character.toString(forbiddenCharacter))) {
                final String template = bundle.getString("CreateDatabaseAction.ForbiddenCharacter");
                return MessageFormat.format(template, forbiddenCharacter);
            }
        }

        return null;
    }

}
