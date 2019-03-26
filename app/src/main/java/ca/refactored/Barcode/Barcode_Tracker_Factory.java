package ca.refactored.Barcode;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import ca.refactored.Barcode.camera.GraphicOverlay;

/**
 * Factory for creating a tracker and associated graphic to be associated with a new barcode.  The
 * multi-processor uses this factory to create barcode trackers as needed -- one for each barcode.
 */
class Barcode_Tracker_Factory implements MultiProcessor.Factory<Barcode> {
    private GraphicOverlay<Barcode_Graphic> mGraphicOverlay;
    private Barcode_Graphic_Tracker.BarcodeGraphicTrackerListener listener;

    Barcode_Tracker_Factory(GraphicOverlay<Barcode_Graphic> barcodeGraphicOverlay, Barcode_Graphic_Tracker.BarcodeGraphicTrackerListener listener) {
        mGraphicOverlay = barcodeGraphicOverlay;
        this.listener = listener;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        Barcode_Graphic graphic = new Barcode_Graphic(mGraphicOverlay);
        return new Barcode_Graphic_Tracker(mGraphicOverlay, graphic, listener);
    }

}

