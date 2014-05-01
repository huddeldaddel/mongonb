package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBObject;
import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.core.MongoExceptionUnwrapper;
import de.bfg9000.mongonb.core.QueryResult;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.ResourceBundle;
import javax.swing.SwingWorker;
import lombok.Setter;
import org.openide.util.NbBundle;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

/**
 * Inserts a new document asynchronously, then updates the UI.
 *
 * @author thomaswerner35
 */
class InsertWorker extends SwingWorker<DBObject, Void> {

    private static final ResourceBundle bundle = NbBundle.getBundle(InsertWorker.class);

    private final Collection collection;
    private final String query;
    private final String name;
    private final int pageSize;
    private long durationInMillis = 0;
    private String errorMessage = null;
    private DataCache dataCache;
    @Setter private ResultTable resultTable;

    public InsertWorker(Collection collection, String query, String name, int pageSize) {
        this.collection = collection;
        this.query = query;
        this.name = name;
        this.pageSize = pageSize;

    }

    @Override
    protected DBObject doInBackground() throws Exception {
        final long start = System.currentTimeMillis();
        try {
            return collection.add(query);
        } catch(Exception ex) {
            errorMessage = new MongoExceptionUnwrapper(ex).toString();
            return null;
        } finally {
            final long end =  System.currentTimeMillis();
            durationInMillis = end -start;
        }
    }

    @Override
    protected void done() {
        IOProvider.getDefault().getIO(name, false).closeInputOutput();
        final InputOutput io = IOProvider.getDefault().getIO(name, true);
        try {
            final String duration = NumberFormat.getNumberInstance().format(durationInMillis / 1000.0);
            final DBObject dbObject = get();
            if(null == dbObject) {
                final String template = bundle.getString("QueryTopComponent.insertFailure");
                io.getOut().println(MessageFormat.format(template, duration));
                if(null != errorMessage)
                    io.getErr().println("\n" +errorMessage.replaceAll("Source: java.io.StringReader@(.+?); ", ""));
                dataCache = DataCache.EMPTY;
            } else {
                final QueryResult qResult = new QueryResult.CollectionResult(Collections.singletonList(dbObject), null);
                dataCache = new DataCache(qResult, pageSize);
                String template = bundle.getString("QueryTopComponent.insertSuccess");
                io.getOut().println(MessageFormat.format(template, duration));
                io.getOut().println(bundle.getString("QueryTopComponent.insertInfoSingle"));
            }
            resultTable.updateTable(dataCache, false);
        } catch(Exception ignored) {
        } finally {
            io.select();
            io.getErr().close();
            io.getOut().close();
        }
    }

}
