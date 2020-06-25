package CGlab;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    String version = "0.02";

    public static void main(String[] args) {
        if(args.length < 3)
        {
            System.out.println("Not all arguments were given");
            return;
        }

        //directory
        String directory=args[0];
        //width
        int w=Integer.parseInt(args[1]);
        //height
        int h=Integer.parseInt(args[2]);

        Renderer mainRenderer = new Renderer(directory,w,h);
        mainRenderer.clear();
        mainRenderer.drawPoint(100, 100);
        try {
            mainRenderer.save();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getVersion() {
	return this.version;
    }
}
