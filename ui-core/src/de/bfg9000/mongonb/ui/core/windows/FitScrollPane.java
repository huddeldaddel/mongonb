package de.bfg9000.mongonb.ui.core.windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelListener;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

/**
 * Taken from Jidesoft examples.
 * 
 * @author wernert
 */
class FitScrollPane extends JScrollPane implements ComponentListener {
        
    public FitScrollPane() {
        initScrollPane();
    }

    public FitScrollPane(Component view) {
        super(view);
        initScrollPane();
    }

    public FitScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        initScrollPane();
    }

    public FitScrollPane(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        initScrollPane();
    }

    private void initScrollPane() {
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        getViewport().getView().addComponentListener(this);
        removeMouseWheelListeners();
    }

    // remove MouseWheelListener as there is no need for it in FitScrollPane.
    private void removeMouseWheelListeners() {        
        for(MouseWheelListener listener : getMouseWheelListeners())
            removeMouseWheelListener(listener);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        removeMouseWheelListeners();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        setSize(getSize().width, getPreferredSize().height);
    }

    @Override
    public void componentMoved(ComponentEvent e) { }

    @Override
    public void componentShown(ComponentEvent e) { }

    @Override
    public void componentHidden(ComponentEvent e) { }

    @Override
    public Dimension getPreferredSize() {
        getViewport().setPreferredSize(getViewport().getView().getPreferredSize());
        return super.getPreferredSize();
    }
    
}
