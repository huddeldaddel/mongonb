package de.bfg9000.mongonb.ui.core.actions;

import com.mongodb.DBObject;
import de.bfg9000.mongonb.core.QueryObjectConverter;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * Copies the content of a selected table row (to the clipboard).
 *
 * @author thomaswerner35
 */
public class CopyEntryAction extends AbstractAction {

    private final DBObject selectedRecord;

    public CopyEntryAction(DBObject selectedRecord) {
        super("Copy");
        this.selectedRecord = selectedRecord;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        final String query = new QueryObjectConverter().convertQueryObject(selectedRecord);
        final StringSelection selection = new StringSelection(query);
        clipboard.setContents(selection, selection);
    }

}
