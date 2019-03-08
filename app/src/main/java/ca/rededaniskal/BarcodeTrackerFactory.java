package ca.rededaniskal;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import ca.rededaniskal.camera.GraphicOverlay;

/**
 * Factory for creating a tracker and associated graphic to be associated with a new barcode.  The
 * multi-processor uses this factory to create barcode trackers as needed -- one for each barcode.
 */
class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private GraphicOverlay<ca.rededaniskal.BarcodeGraphic> mGraphicOverlay;
    private ca.rededaniskal.BarcodeGraphicTracker.BarcodeGraphicTrackerListener listener;

    BarcodeTrackerFactory(GraphicOverlay<ca.rededaniskal.BarcodeGraphic> barcodeGraphicOverlay, ca.rededaniskal.BarcodeGraphicTracker.BarcodeGraphicTrackerListener listener) {
        mGraphicOverlay = barcodeGraphicOverlay;
        this.listener = listener;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        ca.rededaniskal.BarcodeGraphic graphic = new ca.rededaniskal.BarcodeGraphic(mGraphicOverlay);
        return new ca.rededaniskal.BarcodeGraphicTracker(mGraphicOverlay, graphic, listener);
    }

}

