package Common.Image;


import org.bytedeco.javacpp.BytePointer;

import static org.bytedeco.javacpp.lept.*;
import static org.bytedeco.javacpp.tesseract.*;

public class ImageRecognition {


    public String recognition(String file, String type) {

        BytePointer result;
        TessBaseAPI api = new TessBaseAPI();
        String dataPath = "./tessdata";
        if (api.Init(dataPath, type) != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        PIX image = pixRead(file);
        api.SetImage(image);
        result = api.GetUTF8Text();
        System.out.println(result.getString());
        api.End();
        result.deallocate();
        pixDestroy(image);
        return result.getString();
    }
}
