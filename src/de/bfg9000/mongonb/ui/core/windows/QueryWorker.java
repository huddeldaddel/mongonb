package de.bfg9000.mongonb.ui.core.windows;

import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.core.MongoExceptionUnwrapper;
import de.bfg9000.mongonb.core.QueryExecutor;
import de.bfg9000.mongonb.core.QueryResult;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javax.swing.SwingWorker;
import lombok.Setter;
import org.openide.util.NbBundle;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

/**
 *
 * Executes a query asynchronously, then updates the UI.
 *
 * @author thomaswerner35
 */
class QueryWorker extends SwingWorker<QueryResult, Void> implements QueryExecutor {

    private static final ResourceBundle bundle = NbBundle.getBundle(QueryWorker.class);

    private final Collection collection;
    private final String query;
    private final String name;
    private final int pageSize;
    private long durationInMillis = 0;
    private String errorMessage = null;
    private DataCache dataCache;
    @Setter private ResultTable resultTable;

    public QueryWorker(Collection collection, String query, String name, int pageSize) {
        this.collection = collection;
        this.query = query;
        this.name = name;
        this.pageSize = pageSize;
    }

    @Override
    protected QueryResult doInBackground() throws Exception {
        final long start = System.currentTimeMillis();
        try {
            return collection.query(query, this);
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
            final QueryResult queryResult = get();
            if(null == queryResult) {
                final String template = bundle.getString("QueryTopComponent.queryFailure");
                io.getOut().println(MessageFormat.format(template, duration));
                if(null != errorMessage)
                    io.getErr().println("\n" +errorMessage.replaceAll("Source: java.io.StringReader@(.+?); ", ""));
                dataCache = DataCache.EMPTY;
            } else {
                dataCache = new DataCache(queryResult, pageSize, query);
                String template = bundle.getString("QueryTopComponent.querySuccess");
                io.getOut().println(MessageFormat.format(template, duration));
                if(1 == dataCache.getCount()) {
                    io.getOut().println(bundle.getString("QueryTopComponent.queryInfoSingle"));
                } else {
                    template = bundle.getString("QueryTopComponent.queryInfo");
                    io.getOut().println(MessageFormat.format(template, dataCache.getCount()));
                }
            }
            resultTable.updateTable(dataCache, true);
        } catch(Exception ignored) {
        } finally {
            io.select();
            io.getErr().close();
            io.getOut().close();
        }
    }

}
