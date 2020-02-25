package converter;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
final class FileDropHandler extends TransferHandler {
	
	private JTextField field;
	
    public FileDropHandler(JTextField field) {
		this.field = field;
	}

	@Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        for (DataFlavor flavor : support.getDataFlavors()) {
            if (flavor.isFlavorJavaFileListType()) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!this.canImport(support))
            return false;

        List<File> files;
        try {
            files = (List<File>) support.getTransferable()
                    .getTransferData(DataFlavor.javaFileListFlavor);
        } catch (UnsupportedFlavorException | IOException ex) {
            return false;
        }

        //System.out.println(getField().getName().toString());
        
        for (File file: files) {
        	getField().setText(file.getAbsolutePath());
        }
        return true;
    }
    
    public JTextField getField() {
    	return field;
    }

}