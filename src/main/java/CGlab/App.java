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
        Vec2f A = new Vec2f(10,10);
        Vec2f B = new Vec2f(200,200);
        Vec2f C = new Vec2f(450, 100);
        mainRenderer.drawTriangle(A,B,C);

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
