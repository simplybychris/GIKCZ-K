package CGlab;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    String version = "0.02";

    public static void main(String[] args) {
        if(args.length < 4)
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
        //line algorithm
        String alg = args[3];

        Renderer mainRenderer = new Renderer(directory,w,h,alg);
        mainRenderer.clear();
        mainRenderer.drawLine(320,240,360,320);
        mainRenderer.drawLine(320,240,360,300);
        mainRenderer.drawLine(320,240,360,220);
        mainRenderer.drawLine(320,240,360,180);
        mainRenderer.drawLine(320,240,100,200);
        mainRenderer.drawLine(320,240,200,200);
        mainRenderer.drawLine(320,240,160,300);
        mainRenderer.drawLine(320,240,200,140);
        try {;
            mainRenderer.save();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getVersion() {
	return this.version;
    }
}
