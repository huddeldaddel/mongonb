package de.bfg9000.mongonb.ui.core.actions;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import de.bfg9000.mongonb.core.QueryObjectConverter;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.AbstractAction;

/**
 * Copies the content of multiple selected table rows (to the clipboard).
 *
 * @author thomaswerner35
 */
public class CopyEntriesAction extends AbstractAction {

    private final Collection<DBObject> selectedRecords;

    public CopyEntriesAction(Collection<DBObject> selectedRecords) {
        super("Copy");
        this.selectedRecords = selectedRecords;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final BasicDBList dbObjectList = new BasicDBList();
        dbObjectList.addAll(selectedRecords);
        final String query = new QueryObjectConverter().convertQueryObject(dbObjectList);
        final StringSelection selection = new StringSelection(query);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
    }

}
