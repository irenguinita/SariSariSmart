package UI.Components;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SimpleDocumentListener implements DocumentListener {
    private final DocumentUpdateListener listener;
    public SimpleDocumentListener(DocumentUpdateListener listener) { this.listener = listener; }
    public void insertUpdate(DocumentEvent e) { listener.update(e); }
    public void removeUpdate(DocumentEvent e) { listener.update(e); }
    public void changedUpdate(DocumentEvent e) { listener.update(e); }
}
